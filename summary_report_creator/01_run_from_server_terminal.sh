
# first loginto the server
sshpass -p 'pvamudna1!Q' ssh odhungana@129.207.46.222

# summary report creator to run for server terminal
# make dir in server
mkdir dnaq
cd dnaq
mkdir 01_data

mkdir 03_summary_result

# create bigfile
for i in {1..15}
	do
		cat home/odhungana/dnaq/01_data/cosmic.filter.vcf >> home/odhungana/dnaq/01_data/02_files_for_summary_report/bigfile.txt
		echo $i
	done

# all g1000 file
time python3 /home/odhungana/dnaq/02_summary_report/03_2_naive_method.py /home/odhungana/dnaq/01_data/g1000.filter.vcf /home/odhungana/dnaq/01_data/g1000.filter.vcf /home/odhungana/dnaq/01_data/g1000.filter.vcf
time python3 /home/odhungana/dnaq/02_summary_report/04_threading.py /home/odhungana/dnaq/01_data/g1000.filter.vcf /home/odhungana/dnaq/01_data/g1000.filter.vcf /home/odhungana/dnaq/01_data/g1000.filter.vcf

# multiprocessing from input file
time python3 /home/odhungana/dnaq/02_summary_report/07_multiporcessing_newfunc.py /home/odhungana/dnaq/01_data/cosmic.filter.vcf /home/odhungana/dnaq/summary_report_creator/input_files.txt


# to check if we can create a folder in server
bash /home/odhungana/dnaq/04_pipeline_scripts/01_user_dir_creator.sh -d20190703105347 -u15 -t1 -r2

#to check for parser and merger ---------------------------------------error mariadb cannot connect remotly 
bash /home/odhungana/dnaq/04_pipeline_scripts/02_check_queue.sh
