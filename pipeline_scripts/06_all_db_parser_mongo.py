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

    elif source == 'genome1000':
        df = pd.read_csv(inputfile,sep='\t')
        df.columns=["chr","pos","g1000-id","ref","alt","altCount","totalCount","altGlobalFreq","americanFreq","asianFreq","afrFreq", "eurFreq"]
        df = df[["g1000-id","chr","pos","ref","alt","altCount","totalCount","altGlobalFreq","americanFreq","asianFreq","afrFreq", "eurFreq"]]
        df.to_csv(outputfile,index=False)

    elif source == 'oncokb':
        df = pd.read_csv(inputfile,sep='\t',encoding='latin-1')
        df = df.iloc[:,[0,1,3,5,6,7]]
        df.to_csv(outputfile,index=False)

    elif source == 'chrtogene':
        df = pd.read_csv(inputfile,sep='\t',encoding='latin-1')
        df.columns= ['chr','pos','gene']
        df.to_csv(outputfile,index=False)

    elif source == 'chrtodisease':
        df = pd.read_csv(inputfile,sep='\t',encoding='latin-1')
        df.columns= ['chr','pos','disease']
        df.to_csv(outputfile,index=False)

    else print("incorrect input or source")
    # publication just has 3 rows just rename header is enough
    # elif source== 'chrtopublication':
    #     df = pd.read_csv(inputfile,sep='\t',encoding='latin-1')
    #     df.columns= ['chr','pos','count']
    #     df.to_csv(outputfile,index=False)
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


    # /opt/python3/bin/python3.4 /home/ojaswee/github/dnaQ/pipeline_scripts/06_all_db_parser_mongo.py -i /home/ojaswee/masters_project/01_data/chrtogene.txt -o /home/ojaswee/masters_project/0101_data/chrtogene.filter.csv -s chrtogene
    # /opt/python3/bin/python3.4 /home/ojaswee/github/dnaQ/pipeline_scripts/06_all_db_parser_mongo.py -i /home/ojaswee/masters_project/01_data/chrtopublication.txt -o /home/ojaswee/masters_project/0101_data/chrtopublication.filter.csv -s chrtopublication
    # /opt/python3/bin/python3.4 /home/ojaswee/github/dnaQ/pipeline_scripts/06_all_db_parser_mongo.py -i /home/ojaswee/masters_project/01_data/chrtodisease.txt -o /home/ojaswee/masters_project/0101_data/chrtodisease.filter.csv -s chrtodisease
