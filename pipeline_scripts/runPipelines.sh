#!/usr/bin/env bash

if [ $# -eq 0 ]
then 
	echo "Usage: reportThread.sh"
	echo "-d database"
        echo "-u user"
	echo "-p password"
fi


if test $# -gt 0
	then
	while getopts :d:u:p: opt
	do
	case $opt in
  	d)
		database=$OPTARG
		;;
  	u)
      	user=$OPTARG
      	;;
    p)
   		password=$OPTARG     		
		;;
	:)
		echo "Option -$OPTARG requires an argument." ; exit
		;;
	\?)
		echo "Invalid option: -$OPTARG"; exit
	esac
	done
	shift $((OPTIND-1))
fi

echo " $user, $database , $password "

rm -f outfiles/*.txt

date_=`date '+%Y-%m-%d %H:%M:%S'`
currentdate=$(echo $date_ | sed -e 's/ /_/g' -e 's/:/_/g' -e 's/-/_/g' )
touch  outfiles/"$currentdate".txt
chmod 777   outfiles/"$currentdate".txt

echo "Running pipelines cron job at - $currentdate " > outfiles/"$currentdate".txt

jobs=()
statement="select requestID from queue where status='queued';"
echo "---Queued jobs ---"
while read -r requestID;
do 
   echo "requestID is $requestID"
   jobs+=($requestID)
done < <( mysql --user="$user" --password="$password" --database="$database" --execute="$statement" -N)

sudo parallel --jobs /home/reporting/run_files/jobfile \
			  --load /home/reporting/run_files/loadfile \
	          --noswap \
	          --eta \
	          --memfree /home/reporting/run_files/memfile \
              --args jobs  "/home/reporting/runPipelineThread.sh -d $database -u $user -p $password -r "




