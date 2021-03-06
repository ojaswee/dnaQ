# this progam gets the parsed file
# Outputs a table with matching chr, pos, ref,alt with useruploads_PARSED
# 1) connect to mongdb
# 2) looking at the 4 columns in client data get values from all collections

# /opt/python3/bin/python3.4 /home/ojaswee/github/dnaQ/pipeline_scripts/04_parsed_uploads_join_mongodb.py -i /home/ojaswee/masters_project/01_data/sample1_2data.txt_PARSED

import pymongo
import optparse
import pandas as pd

client= pymongo.MongoClient();
db=client.dnaq
chrtogene=db.chrtogene
chrtodisease=db.chrtodisease
chrtopublication=db.chrtopublication
clinvar=db.clinvar
cosmic=db.cosmic
g1000=db.g1000
# oncokb=db.oncokb
# civic =db.civic

def appendResult(cursor,cols):
    res=[]
    if len(cursor) >0 :
        for col in cols:
            res.append([x[col] for x in cursor])
    else:
            res.append([['none'] for x in range(len(cols))])
            res=res[0]
    return res


def combineDB(infile):
    outfile = infile + "_COMBINED.csv"
    sample = pd.read_csv(infile,sep=',')

    with open(outfile,"w") as w:
        block=10
        count=1

        result=[]

        #w.writelines('\t'.join(str(j) for j in header) + '\n')

        for indx,row in sample.iterrows():
            # print(row)
            chr=row[0]
            pos=row[1]
            ref=row[2]
            alt=row[3]
            # enst=row[11]

            g1000_cursor = list(g1000.find({'chr':'chr'+str(chr),'pos':int(pos),'ref':ref,'alt':alt},projection={'_id': 0,'g1000-id':1,'altGlobalFreq':1,'americanFreq':1,'asianFreq':1,'afrFreq':1,'eurFreq':1}))
            g1000_ids=appendResult(g1000_cursor,['g1000-id','altGlobalFreq','americanFreq','asianFreq','afrFreq', 'eurFreq'])

            cosmic_cursor = list(cosmic.find({'chr':int(chr),'pos':int(pos),'ref':ref,'alt':alt},projection={'_id': 0,'cosmic-id':1,'count':1}))
            cosmic_ids= appendResult(cosmic_cursor,['cosmic-id','count'])

            clinvar_cursor = list(clinvar.find({'chr':int(chr),'pos':int(pos),'ref':ref,'alt':alt},projection={'_id': 0,'clinvar-id':1,'CLNDN':1,'CLNSIG':1}))
            clinvar_ids=appendResult(clinvar_cursor,['clinvar-id','CLNDN','CLNSIG'])

            chrtogene_cursor = list(chrtogene.find({'chr':'chr'+str(chr),'start':{'$lte':int(pos)},'end':{'$gte':int(pos)}},projection={'_id':0,'gene':1}))
            chrtogene_ids=appendResult(chrtogene_cursor,['gene'])

            chrtodisease_cursor = list(chrtodisease.find({'chr':'chr'+str(chr),'start':{'$lte':int(pos)},'end':{'$gte':int(pos)}},projection={'_id':0,'disease':1}))
            chrtodisease_ids=appendResult(chrtodisease_cursor,['disease'])

            chrtopublication_cursor=list(chrtopublication.find({'chr':'chr'+str(chr),'start':{'$lte':int(pos)},'end':{'$gte':int(pos)}},projection={'_id':0,'publication':1}))
            chrtopublication_ids=appendResult(chrtopublication_cursor,['publication'])

            result.append([chr,pos,ref,alt,\
                    g1000_ids[0],g1000_ids[1],g1000_ids[2],g1000_ids[3],g1000_ids[4],g1000_ids[5],\
                    cosmic_ids[0],cosmic_ids[1],\
                    clinvar_ids[0],clinvar_ids[1],clinvar_ids[2],\
                    chrtogene_ids[0],\
                    chrtodisease_ids[0],\
                    chrtopublication_ids[0]
                    # civic_ids[0],civic_ids[1],civic_ids[2],civic_ids[3],civic_ids[4],\
                    # oncokb_ids[1],oncokb_ids[1],oncokb_ids[2],oncokb_ids[3]
            ])

            # result = oncokb_ids

            if len(result)==block:
                w.writelines('\t'.join(str(j) for j in i) + '\n' for i in result)
                print("processed ten rows" , count )
                result=[]
                count += 1

        w.writelines('\t'.join(str(j) for j in i) + '\n' for i in result)
        w.close()

try:
    parser = optparse.OptionParser()
    parser.add_option('-i', '--infile', help = 'provide input file')
    options,args = parser.parse_args()
    infile = options.infile
    # infile = "/home/ojaswee/dnaq/analysis/2_1_RUN1/2_1_RUN1_UPLOAD_PARSED"
    combineDB(infile)

except TypeError:
	print ("Error in python: 04_parsed_uploads_join_mongodb.py -help for help")
