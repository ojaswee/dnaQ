import sys
import pandas as pd
import requests as req
from subprocess import Popen, PIPE
from PyPDF2 import PdfFileMerger
from os import path
# CREATE A pdf file if file not found
from fpdf import FPDF


def cleanup(filepath):
    cmd = "find %s -name '*.pdf' -size -20k -exec rm -rf {} \;" %filepath
    process = Popen(cmd, shell=True,stdout=PIPE, stderr=PIPE)
    stdout, stderr = process.communicate()


# /opt/python3/bin/python3.4 /home/ojaswee/github/dnaQ/report_generator/01_create_report.py "Gene" "/home/ojaswee/dnaq/analysis/2_1_RUN8/Report/condition_gene.csv"
#"/home/ojaswee/dnaq/analysis/2_1_RUN8/Report/"

# https://ghr.nlm.nih.gov/condition/Renal_cell_carcinoma,_papillary.pdf
# https://ghr.nlm.nih.gov/gene/TP53.pdf

SAMPLENAME = sys.argv[1]
INPUTFILE  = sys.argv[2]
OUT_DIR = sys.argv[3]

WEBSOURCE = "https://ghr.nlm.nih.gov/"

print("sample is ",SAMPLENAME)
print("INPUTFILE is ",INPUTFILE)
print("OUT_DIR is ",OUT_DIR)


df = pd.read_csv(INPUTFILE,header=None,error_bad_lines=False)
df.columns=['type','value']

pdfs = []

for indx,row in df.iterrows():
    r=req.get(WEBSOURCE+row['type']+"/"+row['value']+".pdf", allow_redirects=True)
    pdf_file=OUT_DIR+SAMPLENAME+"_"+row['type']+"_"+row['value']+".pdf"
    open(pdf_file, 'wb').write(r.content)
    pdfs.append(pdf_file)


merger = PdfFileMerger()

cleanup(OUT_DIR)

for pdf in pdfs:

    if(path.isfile(pdf)):
        merger.append(open(pdf, 'rb'))

        with open(OUT_DIR+SAMPLENAME+"_Result.pdf", 'wb') as fout:
             merger.write(fout)
