import pandas as pd
import cyvcf2
import numpy as np
import optparse

def fileParser(inputfile, outputfile, source):

    if source == 'cosmic':
        filter=[]
        df = pd.read_csv(inputfile)

        for indx,row in df.iterrows():
            info_line=row['INFO'].split(';')
            gene = info_line[0].split('=')[1]
            strand = info_line[1].split('=')[1]
            if info_line[2] == 'SNP':
                cds = info_line[3].split('=')[1]
                aa = info_line[4].split('=')[1]
                count = info_line[5].split('=')[1]
            else:
                cds = info_line[2].split('=')[1]
                aa = info_line[3].split('=')[1]
                count = info_line[4].split('=')[1]
            filter.append(row['ID'], row['CHROM'],row['POS'],row['REF'],row['ALT'],gene,strand,cds,aa,count)
        df_filter=pd.DataFrame(filter)
        df_filter.columns= ['cosmic-id','chr','pos','ref','alt','gene','strand','cds','aa','count']
        df_filter.to_csv(outputfile,index='false')

    elif source == 'clinvar':
        filter=[]
        origin_code = {"0":"unknown","1":"germline", "2":"somatic", "4":"inherited", "8":"paternal",
        "16":"maternal","32":"de-novo", "64" :"biparental", "128" :"uniparental", "256":"not-tested",
        "512" : "tested-inconclusive", "1073741824" :"other"}
        df = pd.read_csv(inputfile,sep='\t')

        for indx,row in df.iterrows():
            info_line= row['INFO'].split(';')
            CLNDN=''
            CLNSIG=''
            MC=''
            ORIGIN=''
            for pairs in info_line:
                code = pairs.split('=')[0]
                if code == 'CLNDN':
                    CLNDN=pairs.split('=')[1]
                elif code == 'CLNSIG':
                    CLNSIG=pairs.split('=')[1]
                elif code == 'MC':
                    MC=pairs.split('=')[1].split('|')[1]
                elif code == 'ORIGIN':
                    try:
                        ORIGIN=origin_code[pairs.split('=')[1]]
                    except:
                        ORIGIN='other'
            # break
            filter.append([row['ID'],row['CHROM'],row['POS'],row['REF'], row['ALT'],CLNDN,CLNSIG,MC,ORIGIN])
        df_filter=pd.DataFrame(filter)
        df_filter.columns=['clinvar-id','chr','pos','ref','alt','CLNDN','CLNSIG','MC','ORIGIN']
        df_filter.to_csv(outputfile,index=False)


    elif source == 'sample':
        df = pd.read_csv(inputfile, sep='\t', skiprows = 2)
        filter=[]
        for indx,row in df.iterrows():
            info_line = row['INFO'].split('|')
            filter.append([row['CHROM'][3:],row['POS'],row['REF'],row['ALT'],row['QUAL'],row['FILTER'],info_line[1],
            info_line[2],info_line[3],info_line[4],info_line[5],info_line[6],info_line[7]])
        df_filter=pd.DataFrame( filter)
        df_filter.columns=['chr','pos','ref','alt','qual', 'filter', 'consequence','impact','symbol','ensgid','featuretype','enst','biotype']
        df_filter.to_csv(outputfile,index=False)

    elif source == 'genome1000':
        df = pd.read_csv(inputfile,sep='\t')
        df.columns=["chr","pos","g1000-id","ref","alt","altCount","totalCount","altGlobalFreq","americanFreq","asianFreq","afrFreq", "eurFreq"]
        df = df[["g1000-id","chr","pos","ref","alt","altCount","totalCount","altGlobalFreq","americanFreq","asianFreq","afrFreq", "eurFreq"]]
        df.to_csv(outputfile,index=False)

    elif source == 'oncokb':
        df = pd.read_csv(inputfile,sep='\t',encoding='latin-1')
        df = df.iloc[:,[0,1,3,5,6,7]]
        df.to_csv(outputfile,index=False)

try:
    parser = optparse.OptionParser()
    parser.add_option('-i', '--inputfile', help = 'input file expected')
    parser.add_option('-o', '--outputfile', help = 'output file expected')
    parser.add_option('-s', '--source', help = 'source expected')
    options,args = parser.parse_args()
    inputfile = options.inputfile
    outputfile = options.outputfile
    source = options.source
    fileParser(inputfile, outputfile, source)

except TypeError:
	print ("python 06_parser_mongo.py -help for help")
