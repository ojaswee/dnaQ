#!/usr/bin/env bash
# this bash program
# 1) gets all the queue items where status is zero from database
# 2) stores it in txt file
#	3) makes these queue items status=1
# 4) reading each line from text file send it to

OUTPUT_DIR="/home/ojaswee/dnaq/analysis/"
JOB_DIR="/home/ojaswee/dnaq/queuedjob/"

#################################################
# Parsing arguments
################################################

jobs=()
queueid=0
selectstatement="select queueid, userid, testid, run, status from queue where status= 0;"

currentdate=`date '+%Y%m%d%H%M%S'`
# JOB_FILE="${JOB_DIR}${currentdate}.txt"
JOB_FILE="${JOB_DIR}"20190203170938.txt

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

 		#look for folder under OUTPUT_DIR and find folder with name = anydate_$userid_$testid_$run and combinedfile
		DIRS=`ls -l $OUTPUT_DIR | grep '^d' | awk '{print $9}'`
		#
		for DIR in $DIRS
		do
			if [[ $DIR == *"_${userid}_${testid}_RUN${run}"* ]];then
				echo ${DIR}
				combinedfile="${OUTPUT_DIR}${DIR}/${DIR}_COMBINED.csv"

				#if file is found then trigger usertest table and insert a new row
				if [ -f "$combinedfile" ];then
					echo "combinedfile found."
					loadfilestatement="INSERT INTO usertest (userid,testid,run) VALUES ('$userid','$testid','$run')";
					mysql --user="root" --password="main" --database="dnaq" --execute="$loadfilestatement"

					getusertestid="SELECT usertestid FROM usertest WHERE userid ='$userid' AND testid='$testid' AND run = '$run'"

					usertestid=$(mysql --column-names="0" --user="root" --password="main" --database="dnaq" --execute="$getusertestid")

					echo $usertestid

					loadfilestatement="LOAD data local infile '$combinedfile' INTO table mutation FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n'(chr,pos,ref,alt,cosmicid,cds,aa,count,clinvarid,clndn,clnsig,mc,origin,g1000id,altCount,totalCount,altGlobalFreq,americanFreq,asianFreq,afrFreq,eurFreq,disease,drugs,clinicalSignificance,evidenceStatement,variantSummary,gene,proteinChange,oncogenecity,mutationEffect)SET usertestid='$usertestid';"

					mysql --user="root" --password="main" --database="dnaq" --execute="$loadfilestatement"

				else
					echo "combinedfile not found."
				fi

			fi
		done

done <$JOB_FILE
