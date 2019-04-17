import sys
import pandas as pd
import requests as req
from PyPDF2 import PdfFileMerger

# /opt/python3/bin/python3.4 /home/ojaswee/github/dnaQ/report_generator/01_create_report.py "Global Similarity" "home/ojaswee/dnaq/analysis/2_1_RUN2/condition_gene.csv" "/home/ojaswee/dnaq/analysis/2_1_RUN2/Report/"

SAMPLENAME = sys.argv[1]
INPUTFILE  = sys.argv[2]
OUT_DIR = sys.argv[3]

WEBSOURCE = "https://ghr.nlm.nih.gov/"

print("sample is ",SAMPLENAME)
print("INPUTFILE is ",INPUTFILE)
print("OUT_DIR is ",OUT_DIR)


df = pd.read_csv(INPUTFILE,header=None)
df.columns=['type','value']

pdfs = []

for indx,row in df.iterrows():
    r=req.get(WEBSOURCE+row['type']+"/"+row['value']+".pdf", allow_redirects=True)
    pdf_file=OUT_DIR+SAMPLENAME+"_"+row['type']+"_"+row['value']+".pdf"
    open(pdf_file, 'wb').write(r.content)
    pdfs.append(pdf_file)


merger = PdfFileMerger()

for pdf in pdfs:
    # print(pdf)
    merger.append(open(pdf, 'rb'))

    with open(OUT_DIR+SAMPLENAME+"_Result.pdf", 'wb') as fout:
         merger.write(fout)
