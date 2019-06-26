import sys

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
    print('line',ref,alt)
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
            print(ref,alt)
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

    databases = [cosmic_server,g1000_server,clinvar_server]

    for files in databases:
        readFile_v2(files,data_s)

    printSummary(data_s)
