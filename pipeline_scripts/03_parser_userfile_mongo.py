#this python file get name of input filel from 02_check_queue.sh
# 1) parse useruploaded data to get only 13 aatributes
# 2) create a userupload_PARSED.csv file and place in same folder as useruploads

# to run this file from terminal
# /opt/python3/bin/python3.4 home/ojaswee/github/dnaQ/pipeline_scripts/03_parser_userfile_mongo.py -i /home/ojaswee/masters_project/01_data/sample1_2data.txt


import pandas as pd
import cyvcf2
import numpy as np
import optparse


def fileParser(inputfile):
    outputfile = inputfile + "_PARSED"
    df = pd.read_csv(inputfile, sep='\t', skiprows = 2)
    filter=[]
    for indx,row in df.iterrows():
        info_line = row['INFO'].split('|')
        filter.append([row['CHROM'][3:],row['POS'],row['REF'],row['ALT'],info_line[17]])
    df_filter=pd.DataFrame( filter)
    df_filter.columns=['chr','pos','ref','alt','existing_variation']
    df_filter.to_csv(outputfile,index=False)

try:
    parser = optparse.OptionParser()
    parser.add_option('-i', '--inputfile', help = 'input file expected')
    options,args = parser.parse_args()
    inputfile = options.inputfile
    fileParser(inputfile)

except TypeError:
	print ("python 03_parser_userfile_mongo.py -help for help")
