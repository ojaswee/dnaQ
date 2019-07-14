INSERT INTO user (firstname,lastname,email,password)values("John","Smith", "johnsmith@dnaq.com", "john");
INSERT INTO user (firstname,lastname,email,password)values("admin","admin", "admin@dnaq.com", "admin");
INSERT INTO user (firstname,lastname,email,password)values("Mary","Anderson", "maryanderson@dnaq.com", "mary");
INSERT INTO user (firstname,lastname,email,password)values("Ojaswee","Dhungana", "ojaswee@dnaq.com", "ojaswee");



INSERT INTO test (name,type,cost)values("RNA","Blood",100);
INSERT INTO test (name,type,cost)values("RNA","Saliva",105);
INSERT INTO test (name,type,cost)values("RNA","Stool",170);
INSERT INTO test (name,type,cost)values("RNA","Urine",125);
INSERT INTO test (name,type,cost)values("DNA","Blood",100);
INSERT INTO test (name,type,cost)values("DNA","Saliva",105);
INSERT INTO test (name,type,cost)values("DNA","Stool",180);
INSERT INTO test (name,type,cost)values("DNA","Urine",125);

INSERT INTO usertest (userid,testid,run)values(2,4,1);de
INSERT INTO usertest (userid,testid,run)values(2,4,2);

INSERT INTO queue (userid,testid,run,status,createdon)values(2,2,1,0, NOW());
INSERT INTO queue (userid,testid,run,status,createdon)values (2,2,1,0,'2019-01-10 09:25:30');


-- LOAD data local infile '/home/ojaswee/dnaq/analysis/20190203170748_2_4_RUN1/20190203170748_2_4_RUN1_COMBINED.csv' INTO table mutation FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n'(chr,pos,ref,alt,cosmicid,cds,aa,count,clinvarid,clndn,clnsig,mc,origin,g1000id,altCount,totalCount,altGlobalFreq,americanFreq,asianFreq,afrFreq,eurFreq,disease,drugs,clinicalSignificance,evidenceStatement,variantSummary,gene,proteinChange,oncogenecity,mutationEffect)SET usertestid=2;

LOAD DATA local infile '/home/odhungana/dnaq/01_data/lengthOfChr.csv' INTO table lengthOfChr FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n';

INSERT INTO report(name) values ("Global Mutation Pattern");
INSERT INTO report (name) values ("Gene Report");

-- SELECT * FROM user;
-- SELECT * FROM test;
-- SELECT * FROM usertest;
-- SELECT * FROM queue;
-- SELECT * FROM mutation;
-- SELECT * FROM report;
--
