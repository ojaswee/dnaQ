# this file creates the summary report
# input: ref and alt from user mutation list
# ouput: excel sheet with mutation and database list
#
# time py3 /home/ojaswee/github/dnaQ/summary_report_creator/03_naive_method.py /home/ojaswee/masters_project/01_data/cosmic.filter.vcf /home/ojaswee/masters_project/01_data/g1000.filter.txt /home/ojaswee/masters_project/01_data/05_file_for_summary_report/bigfile.txt

import sys
import csv

# #readfile_v1 used csv library
def readFile(name,data_struct):
    with open (name, 'r') as file:
        db = csv.reader(file, delimiter=',',quotechar='-')
        for row in db:
            if row[0][0] !='#':
                updateSummary(row,data_struct)

def updateSummary(row,data_struct):
    ref = row[3]
    alt = row[4]
    refalt= ref+alt
    if (len(refalt)==2):
        if refalt in data_struct.keys():
            data_struct[refalt] += 1
    # if value greater then 2 find i,s,d
    else:
        if (len(ref)>len(alt)):
            data_struct['D'] +=1
        elif(len(ref)<len(alt)):
            data_struct['I'] +=1
        elif (len(ref)==len(alt)):
            data_struct['S'] += 1


def readFile_v2(name,data_struct):
    with open (name, 'r') as file:
        for row in file:
            if row[0][0] !='#':
                updateSummary_v2(row,data_struct)
    file.close()


def updateSummary_v2(row,data_struct):
    value_in_row = row.split(',')
    ref = value_in_row[3]
    alt = value_in_row[4]
    if (len(ref)==1 and len(alt)==1):
        refalt = ref+alt
        if refalt in data_struct.keys():
            data_struct[refalt] += 1

    # if value greater then 2 find i,s,d
    else:
        if (len(ref)>len(alt)):
            data_struct['D'] +=1
        elif(len(ref)<len(alt)):
            data_struct['I'] +=1
        elif (len(ref)==len(alt)):
            data_struct['S'] += 1

def printSummary(ds):
    for k, v in ds.items():
        print (k, v)


if __name__ == '__main__':

    data_s={'AC':0,'AG':0,'AT':0,
        'CA':0,'CG':0,'CT':0,
        'GA':0,'GC':0,'GT':0,
        'TA':0,'TC':0,'TG':0,
        'I':0,'D':0,'S':0}

    cosmic_server=sys.argv[1]

    #readFile(cosmic_server,data_s)

    readFile_v2(cosmic_server,data_s)

    printSummary(data_s)

    ### (cosmic+g1000+cosmic*15) expected result
# CA 6353499
# I 932193
# TA 1883260
# GT 7091756
# GC 3047323
# AT 1846109
# AC 1755087
# AG 4472965
# D 2181925
# S 359313
# CG 3098150
# TG 1761695
# GA 18843444
# CT 18763633
# TC 4607604
