import sys
import pandas as pd
import requests as req
from PyPDF2 import PdfFileMerger

# CREATE A pdf file if file not found
from fpdf import FPDF

# /opt/python3/bin/python3.4 /home/ojaswee/github/dnaQ/report_generator/01_create_report.py "Global Similarity" "home/ojaswee/dnaq/analysis/2_1_RUN1/Report/condition_gene.csv" "/home/ojaswee/dnaq/analysis/2_1_RUN1/Report/"

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
    # try:
        r=req.get(WEBSOURCE+row['type']+"/"+row['value']+".pdf", allow_redirects=True)
        pdf_file=OUT_DIR+SAMPLENAME+"_"+row['type']+"_"+row['value']+".pdf"
        open(pdf_file, 'wb').write(r.content)
    #
    # except IOError:
    #     print("")
    #
    # else:
    #     r="Cannot find " + row ['type'] + ': ' + row ['value']
    #     pdf_file = OUT_DIR+SAMPLENAME+"_"+row['type']+"_"+row['value']+".pdf"
    #
    #     pdf = FPDF()
    #     pdf.add_page()
    #     pdf.set_xy(10, 10)
    #     pdf.set_font('arial', 'B', 13.0)
    #     pdf.cell(ln=0, h=5.0, align='L', w=0, txt=r, border=0)
    #     pdf.output(pdf_file)
    #     pdf.close()

        pdfs.append(pdf_file)

merger = PdfFileMerger()

for pdf in pdfs:
    # print (pdf)
    merger.append(open(pdf, 'rb'))

with open(OUT_DIR+SAMPLENAME+"_Result.pdf", 'wb') as fout:
    merger.write(fout)
