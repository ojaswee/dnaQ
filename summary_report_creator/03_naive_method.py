# this file creates the summary report
# input: ref and alt from user mutation list
# ouput: excel sheet with mutation and database list
#
# time py3 /home/ojaswee/github/dnaQ/summary_report_creator/03_naive_method.py /home/ojaswee/masters_project/01_data/cosmic.filter.vcf /home/ojaswee/masters_project/01_data/g1000.filter.txt /home/ojaswee/masters_project/01_data/clinvar.filter.vcf

import sys
import csv

# #readfile_v1 used csv library
# def readFile(name,data_struct):
#     with open (name, 'r') as file:
#         db = csv.reader(file, delimiter=',',quotechar='-')
#         for row in db:
#             if row[0][0] !='#':
#                 updateSummary_v2(row,data_struct)

# def updateSummary(row,data_struct):
#     ref = row[2]
#     alt = row[3]
#     refalt= ref+alt
#     if (len(refalt)==2):
#         if refalt in data_struct.keys():
#             data_struct[refalt] += 1
#     # if value greater then 2 find i,s,d
#     else:
#         if (len(ref)>len(alt)):
#             data_struct['D'] +=1
#         elif(len(ref)<len(alt)):
#             data_struct['I'] +=1
#         elif (len(ref)==len(alt)):
#             data_struct['S'] += 1
#


# read_file_memory_error
def readFile_memory_error(name,data_struct):
    f = open(name,'r')
    line = f.read()
    for l in line:
        print(l)


# read_file_v2 does not use csv library
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
    # print('line',ref,alt)
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
    g1000_server=sys.argv[2]
    clinvar_server=sys.argv[3]

    # readFile(cosmic_server,data_s)

    databases = [cosmic_server,g1000_server,clinvar_server]

    for i in range (1):
        for files in databases:
                readFile_v2(files,data_s)
    printSummary(data_s)


### cosmic.filter.vcf *3 expected result
# S 67371
# GT 1320984
# GC 561693
# GA 3481563
# TA 348195
# CA 1182441
# D 406005
# AT 341256
# I 172761
# CT 3466977
# CG 571158
# AC 323247
# TC 839310
# TG 324510
# AG 814239
