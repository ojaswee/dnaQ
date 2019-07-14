# this file creates the summary report
# input: ref and alt from user mutation list
# ouput: excel sheet with mutation and database list
#
# time py3 /home/ojaswee/github/dnaQ/summary_report_creator/04_threading.py /home/ojaswee/masters_project/01_data/cosmic.filter.vcf /home/ojaswee/masters_project/01_data/g1000.filter.txt /home/ojaswee/masters_project/01_data/05_file_for_summary_report/bigfile.txt

import sys
from multiprocessing import Process, Manager, Lock

def readFile_v2(name,data_struct,lock):
    with open (name, 'r') as file:
        for row in file:
            if row[0][0] !='#':
                # lock.acquire()

                updateSummary_v2(row,data_struct)

                # lock.release()
    print(data_struct)
    file.close()


def updateSummary_v2(row,data_struct):
    value_in_row = row.split(',')
    ref = value_in_row[3]
    alt = value_in_row[4]
    if (len(ref)==1 and len(alt)==1):
        refalt = ref+alt
        if refalt in data_struct.keys():
            data_struct[refalt] += 1
    else:
        if (len(ref)>len(alt)):
            data_struct['D'] +=1
        elif(len(ref)<len(alt)):
            data_struct['I'] +=1
        elif (len(ref)==len(alt)):
            data_struct['S'] += 1
    # print(data_struct)

def printSummary(ds):
    for k, v in ds.items():
        print (k, v)

if __name__ == '__main__':

    manager = Manager()
    data_s = manager.dict()


    values = ['AC','AG','AT','CA','CG','CT','GA','GC','GT','TA','TC','TG','I','D','S']

    for i in values:
        data_s.update([(i,0)])

    cosmic_server=sys.argv[1]
    g1000_server=sys.argv[2]
    clinvar_server=sys.argv[3]

    databases = [cosmic_server,g1000_server,clinvar_server]


    print('-----program running ---')

    processes=[]

    lock=Lock()
    for file in databases:
        ps = Process(target=readFile_v2, args=(file,data_s,lock))
        print (file)
        processes.append(ps)
        ps.start()

    for p in processes:
        print(p.name)
        p.join()


    # pool.map(readFile_v3_pool, (databases,data_s))

    printSummary(data_s)
