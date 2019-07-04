# this file creates the summary report
# input: ref and alt from user mutation list
# ouput: excel sheet with mutation and database list
#
# time py3 /home/ojaswee/github/dnaQ/summary_report_creator/04_threading.py /home/ojaswee/masters_project/01_data/cosmic.filter.vcf /home/ojaswee/masters_project/01_data/g1000.filter.txt /home/ojaswee/masters_project/01_data/05_file_for_summary_report/bigfile.txt

import sys
# import csv
import threading

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

    databases = []

    with open (sys.argv[1], 'r') as input:
        for file in input:
            databases.append(file.strip('\n'))

    threads=[]

    print('-----program running ---')


    for files in databases:
        th = threading.Thread(target=readFile_v2, args=(files,data_s,))
        print (files)
        threads.append(th)
        th.start()


    for t in threads:
        t.join()
    printSummary(data_s)
