#!/usr/bin/env bash

if [ $# -eq 0 ]
then 
	echo "Usage: runPipelineThread.sh"
	echo "-d database"
    echo "-u user"
	echo "-p password"
	echo "-r requestID"
fi

myname='sishir'

if test $# -gt 0
	then
	while getopts :d:u:p:r: opt
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
	r)
      	requestID=$OPTARG
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



echo "running from runPipelinThread"
echo " $user, $database , $password ,$requestID"

### TODO implement tabcmd command to send report request to tableau virtual machine
# log into the tableau server
tabcmd login -s http://IP.address.of.server -u username -p password

#create new project in the default site 
tabcmd createproject -n "Finance"

#publish naked data exact, we want to expenses per process in cost_per_process.tde
tabcmd publish "C:\Users\ojaswee\Critical Tableau Workbooks\ cost_per_process.tde –project" "Most expensive process" -n "Cost per process" --db-username "username" --db-password "password"
 
# update data extract 
tabcmd refreshextracts -–project “Finance” –data source “Cost per process”

# export tableau viz as a pdf
tabcmd export –-project “Finance” “Most expensive process/Sheet1” –-pdf

# remove the project 
tabcmd deleteproject "Finance"


