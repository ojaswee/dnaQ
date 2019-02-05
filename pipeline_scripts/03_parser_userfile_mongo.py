#this python file
# 1) parse sample data
# 2) inserts each row of useruploaded file into mongodb
# 3) adds 3 new key userid, testid and run into the mongodb

# to run this file from terminal
#  /opt/python3/bin/python3.4 /home/ojaswee/masters_project/05_scripts/03_parser_userfile_mongo.py /home/ojaswee/masters_project/01_data/test_sample.vcf

import pandas as pd
import cyvcf2
import numpy as np
import optparse


def fileParser(inputfile):
    outputfile = inputfile + "_parsed"
    df = pd.read_csv(inputfile, sep='\t', skiprows = 2)
    filter=[]
    for indx,row in df.iterrows():
        info_line = row['INFO'].split('|')
        filter.append([row['CHROM'][3:],row['POS'],row['REF'],row['ALT'],row['QUAL'],row['FILTER'],info_line[1],
        info_line[2],info_line[3],info_line[4],info_line[5],info_line[6],info_line[7]])
    df_filter=pd.DataFrame( filter)
    df_filter.columns=['chr','pos','ref','alt','qual', 'filter', 'consequence','impact','symbol','ensgid','featuretype','enst','biotype']
    df_filter.to_csv(outputfile,index=False)

try:
    parser = optparse.OptionParser()
    parser.add_option('-i', '--inputfile', help = 'input file expected')
    options,args = parser.parse_args()
    inputfile = options.inputfile
    fileParser(inputfile)

except TypeError:
	print ("python 03_parser_userfile_mongo.py -help for help")


# mongoimport --db dnaq --collection mutation --type csv --headerline --file /home/ojaswee/masters_project/01_data/sample1.filter.vcf
