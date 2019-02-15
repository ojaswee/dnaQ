#!/usr/bin/env bash
# this bash program looks for users folder if not present it will create one

OUTPUT_DIR="/home/ojaswee/dnaq/analysis/"

#################################################
# Parsing arguments
################################################

if [ "$#" -eq 0 ]; then
echo "-d currentdate"
echo "-u USERID"
echo "-t TESTID"
echo "-r RUN"
exit
fi

while getopts :d:u:t:r: option; do
	case "$option" in
		d) currentdate="$OPTARG" ;;
    u) USERID="$OPTARG" ;;
    t) TESTID="$OPTARG" ;;
    r) RUN="$OPTARG" ;;
    :) echo "Option -$OPTARG requires an argument." ;;
	  	\?) echo "Invalid option: -$OPTARG" ;;
		esac
done

########################################
#create folders
#######################################

OUTPUT_DIR_CURRENT_UPLOAD="${OUTPUT_DIR}${USERID}_${TESTID}_RUN${RUN}/"

if [ ! -d $OUTPUT_DIR_CURRENT_UPLOAD ]
then
  mkdir $OUTPUT_DIR_CURRENT_UPLOAD
fi
chmod 777 $OUTPUT_DIR_CURRENT_UPLOAD

LOG_FILE="${OUTPUT_DIR_CURRENT_UPLOAD}${USERID}.log"

# chmod 777 $LOG_FILE

function log {
 MESSAGE=$1
 TIMESTAMP=`date "+%Y-%m-%d %H:%M:%S"`

 echo "[ $TIMESTAMP ]  $MESSAGE" >>${LOG_FILE}
}

##################################################################################################
# Write on log file
##################################################################################################

log "------------------
Starting - log_file_creator.sh $USERID
OUTPUT_DIR_CURRENT_UPLOAD - $OUTPUT_DIR_CURRENT_UPLOAD
"
