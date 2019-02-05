#!/usr/bin/env bash
# input: gets usertestid from 02_check_queue.#!/bin/sh
# output: 1) add usertestid in each row
# 2) load file in mutation table

############################################
#Parsing arguments
############################################
if [ "$#" -eq 0 ]; then
echo "-ut USERTESTID"
exit
fi

while getopts :ut option; do
	case "$option" in
		ut) USERTESTID="$OPTARG" ;;
    :) echo "Option -$OPTARG requires an argument." ;;
	  	\?) echo "Invalid option: -$OPTARG" ;;
		esac
done

############################################
#sed userid in the _COMBINED file
############################################

sed -i -e 's/^/$usertestid'\t'/'

loadfilestatement="INSERT INTO mutation (userid,testid,run) VALUES ('$userid','$testid','$run')";
