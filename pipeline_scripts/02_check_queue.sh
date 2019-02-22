#!/usr/bin/env bash
# this bash program is run from cronjob, it
# 1) gets all the queue items where status is zero from database
# 2) stores it in txt file
#	3) makes these queue items status=1
# 4) reading each line from text file send it to

OUTPUT_DIR="/home/ojaswee/dnaq/analysis/"
JOB_DIR="/home/ojaswee/dnaq/queuedjob/"
PIPELINE_SCRIPTS="/home/ojaswee/github/dnaQ/pipeline_scripts/"

#################################################
# Parsing arguments
################################################

jobs=()
queueid=0
selectstatement="select queueid, userid, testid, run, status from queue where status= 0;"

currentdate=`date "+%Y%m%d%H%M%S"`

JOB_FILE="${JOB_DIR}${currentdate}.txt"

while read -r queueid userid testid run status; do

		echo "$queueid $userid $testid $run $status"

		updatestatement="UPDATE queue SET status = 1 WHERE queueid= '$queueid';"

		mysql --user="root" --password="main" --database="dnaq" --execute="$updatestatement"

		echo "$queueid;$userid;$testid;$run;$status" >> $JOB_FILE

done < <( mysql --user="root" --password="main" --database="dnaq" --execute="$selectstatement" -N)

############################################################################
#get userid,testid, run from queuedjob one by one and insert combined
###########################################################################

while IFS=';' read queueid userid testid run status; do
		echo $userid $testid $run

		CURRENT_DIR="${OUTPUT_DIR}${userid}_${testid}_RUN${run}/"
		uploadedfile="${CURRENT_DIR}${userid}_${testid}_RUN${run}_UPLOAD"

		chmod 777 *
		#if uploadedfile exist send to python to parse file
		if [ -f "${uploadedfile}" ];then
			echo $uploadedfile
			/opt/python3/bin/python3.4 "${PIPELINE_SCRIPTS}03_parser_userfile_mongo.py" -i $uploadedfile

			chmod 777 *

			echo $parserdfile
			if [ -f "${uploadedfile}_PARSED" ];then
				echo "${uploadedfile}_PARSED"

				/opt/python3/bin/python3.4 ${PIPELINE_SCRIPTS}04_parsed_uploads_join_mongodb.py -i "${uploadedfile}_PARSED"
 				combinedfile="${uploadedfile}_PARSED_COMBINED.csv"

				#if file is found then trigger usertest table and insert a new row
				if [ -f "$combinedfile" ];then
					echo "combinedfile found."
					loadfilestatement="INSERT INTO usertest (userid,testid,run) VALUES ('$userid','$testid','$run')";
					mysql --user="root" --password="main" --database="dnaq" --execute="$loadfilestatement"

					getusertestid="SELECT usertestid FROM usertest WHERE userid ='$userid' AND testid='$testid' AND run = '$run'"

					usertestid=$(mysql --column-names="0" --user="root" --password="main" --database="dnaq" --execute="$getusertestid")

					echo $usertestid

					loadfilestatement="LOAD data local infile '$combinedfile' INTO table mutation FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n'(chr,pos,ref,alt,freqid,globalFreq,americanFreq,asianFreq,afrFreq,eurFreq,cancerid,cancerCount,clinicalid,clinicalDisease,signficance,gene,biologyDisease,publicationCount)SET usertestid='$usertestid';"

					mysql --user="root" --password="main" --database="dnaq" --execute="$loadfilestatement"

					updatequeuestatement="UPDATE queue set status =2 where queueid='$queueid'"
					mysql --user="root" --password="main" --database="dnaq" --execute="$updatequeuestatement"

				else
					echo "combinedfile not found."
				fi
			fi
		fi

done <$JOB_FILE
