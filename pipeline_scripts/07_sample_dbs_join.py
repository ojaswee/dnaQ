import pymongo
import optparse
import pandas as pd

#Output a table with matching chr, pos, ref,alt with sample
# phase one check in cosmic -> clinvar,

client= pymongo.MongoClient();
db=client.cancer
cosmic=db.cosmic
clinvar = db.clinvar
oncokb=db.oncokb
civic =db.civic
g1000=db.g1000


def appendResult(cursor,cols):
    res=[]
    if len(cursor) >0 :
        for col in cols:
            res.append([x[col] for x in cursor])
    else:
            res.append([['none'] for x in range(len(cols))])
            res=res[0]
    return res



def combineDB(infile, outfile):
    sample = pd.read_csv(infile,sep=',')

    with open(outfile,"w") as w:

        # header=['chr','pos','ref','alt',\
        # 'cosmic_id','cds','aa','count',\
        # 'clinvar_id','CLNDN','CLNSIG','MC','ORIGIN',\
        # 'g1000_id','altCount','totalCount','altGlobalFreq','americanFreq','asianFreq','afrFreq', 'eurFreq',\
        # 'disease','drugs','clinicalSignificance','evidenceStatement','variantSummary',\
        # 'gene','proteinChange','oncogenecity','mutationEffect']


        #df.to_csv(outfile,index='false')
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
            enst=row[11]

            cosmic_cursor = list(cosmic.find({'chr':int(chr),'pos':int(pos),'ref':ref,'alt':alt},projection={'_id': 0,'cosmic-id':1,'cds':1,'aa':1,'count':1}))
            cosmic_ids= appendResult(cosmic_cursor,['cosmic-id','cds','aa','count'])

            clinvar_cursor = list(clinvar.find({'chr':int(chr),'pos':int(pos),'ref':ref,'alt':alt},projection={'_id': 0,'clinvar-id':1,'CLNDN':1,'CLNSIG':1,'MC':1,'ORIGIN':1}))
            clinvar_ids=appendResult(clinvar_cursor,['clinvar-id','CLNDN','CLNSIG','MC','ORIGIN'])

            g1000_cursor = list(g1000.find({'chr':'chr'+str(chr),'pos':int(pos),'ref':ref,'alt':alt},projection={'_id': 0,'g1000-id':1,'altCount':1,'totalCount':1,'altGlobalFreq':1,'americanFreq':1,'asianFreq':1,'afrFreq':1,'eurFreq':1}))
            g1000_ids=appendResult(g1000_cursor,['g1000-id','altCount','totalCount','altGlobalFreq','americanFreq','asianFreq','afrFreq', 'eurFreq'])

            civic_cursor = list(g1000.find({'chr':int(chr),'pos':int(pos),'ref':ref,'alt':alt},projection={'_id': 0,'disease':1,'drugs':1,'clinical_significance':1,'evidence_statement':1,'variant_summary':1}))
            civic_ids=appendResult(civic_cursor,['disease','drugs','clinical_significance','evidence_statement','variant_summary'])

            oncokb_cursor = list(g1000.find({'Isoform':enst},projection={'_id': 0,'Gene':1,'Protein Change':1,'Oncogenicity':1,'Mutation Effect':1}))
            oncokb_ids=appendResult(oncokb_cursor,['Gene','Protein Change','Oncogenecity','Mutation Effect'])

            result.append([chr,pos,ref,alt,\
                    cosmic_ids[0],cosmic_ids[1],cosmic_ids[2],cosmic_ids[3],\
            clinvar_ids[0],clinvar_ids[1],clinvar_ids[2],clinvar_ids[3],clinvar_ids[4],\
            g1000_ids[0],g1000_ids[1],g1000_ids[2],g1000_ids[3],g1000_ids[4],g1000_ids[5],g1000_ids[6],g1000_ids[7],\
            civic_ids[0],civic_ids[1],civic_ids[2],civic_ids[3],civic_ids[4],\
            oncokb_ids[1],oncokb_ids[1],oncokb_ids[2],oncokb_ids[3]])

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
    parser.add_option('-o', '--outfile', help = 'provide output file')
    options,args = parser.parse_args()
    infile = options.infile
    outfile = options.outfile
    # infile no need we can pull from mongodb
    infile = "/home/ojaswee/masters_project/01_data/sample1.filter.vcf"
    outfile = "/home/ojaswee/masters_project/01_data/combined_data.csv"
    combineDB(infile, outfile)

except TypeError:
	print ("python combineVariantDB.py -help for help")
