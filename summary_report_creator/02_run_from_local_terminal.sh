#login
sshpass -p 'pvamudna1!Q' ssh odhungana@129.207.46.222


# summary report creator to run for local terminal

#transfer data from local to server
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/masters_project/01_data/cosmic.filter.vcf odhungana@129.207.46.222:/home/odhungana/dnaq/01_data
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/masters_project/01_data/g1000.filter.txt odhungana@129.207.46.222:/home/odhungana/dnaq/01_data
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/masters_project/01_data/clinvar.filter.vcf odhungana@129.207.46.222:/home/odhungana/dnaq/01_data

sshpass -p 'pvamudna1!Q' scp /home/ojaswee/masters_project/01_data/chrtopublication.txt odhungana@129.207.46.222:/home/odhungana/dnaq/01_data
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/masters_project/01_data/chrtogene.txt odhungana@129.207.46.222:/home/odhungana/dnaq/01_data
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/masters_project/01_data/chrtodisease.txt odhungana@129.207.46.222:/home/odhungana/dnaq/01_data
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/masters_project/01_data/chrtodisease.txt odhungana@129.207.46.222:/home/odhungana/dnaq/01_data
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/masters_project/01_data/lengthOfChr.csv odhungana@129.207.46.222:/home/odhungana/dnaq/01_data

# create/ rewrite 02_summary_report in server
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/github/dnaQ/summary_report_creator/03_summary_creator.py odhungana@129.207.46.222:/home/odhungana/dnaq/


# import files in collections in mongodb


mongo
use dnaq

mongoimport --db dnaq --collection cosmic --type csv --headerline --file /home/odhungana/dnaq/01_data/cosmic.filter.vcf
mongoimport --db dnaq --collection clinvar --type csv --headerline --file /home/odhungana/dnaq/01_data/clinvar.filter.vcf
mongoimport --db dnaq --collection g1000 --type csv --headerline --file /home/odhungana/dnaq/01_data/g1000.filter.vcf
mongoimport --db dnaq --collection chrtogene --type tsv --headerline --file /home/odhungana/dnaq/01_data/chrtogene.txt
mongoimport --db dnaq --collection chrtodisease --type tsv --headerline --file /home/odhungana/dnaq/01_data/chrtodisease.txt
mongoimport --db dnaq --collection chrtopublication --type tsv --headerline --file /home/odhungana/dnaq/01_data/chrtopublication.txt

# for folder creation file parsing and merger
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/github/dnaQ/pipeline_scripts/01_user_dir_creator.sh odhungana@129.207.46.222:/home/odhungana/dnaq/04_pipeline_scripts/
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/github/dnaQ/pipeline_scripts/02_check_queue.sh odhungana@129.207.46.222:/home/odhungana/dnaq/04_pipeline_scripts/


# for summary report
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/github/dnaQ/summary_report_creator/03_2_naive_method.py odhungana@129.207.46.222:/home/odhungana/dnaq/02_summary_report/
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/github/dnaQ/summary_report_creator/04_threading.py odhungana@129.207.46.222:/home/odhungana/dnaq/02_summary_report/
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/github/dnaQ/summary_report_creator/05_multiprocessing.py odhungana@129.207.46.222:/home/odhungana/dnaq/02_summary_report/
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/github/dnaQ/summary_report_creator/07_multiporcessing_newfunc.py odhungana@129.207.46.222:/home/odhungana/dnaq/02_summary_report
sshpass -p 'pvamudna1!Q' scp /home/ojaswee/github/dnaQ/summary_report_creator/input_files.txt odhungana@129.207.46.222:/home/odhungana/dnaq/02_summary_report
