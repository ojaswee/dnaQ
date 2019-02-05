DROP TABLE IF EXISTS sample;
CREATE TABLE sample(
  id INT(150) NOT NULL AUTO_INCREMENT,
  chr VARCHAR(150) NOT NULL,
  pos INT NOT NULL,
  ref VARCHAR(150) NULL,
  alt VARCHAR(150) NULL,
  cosmicid VARCHAR(150) NULL,
  cds VARCHAR(150) NULL,
  aa VARCHAR(150) NULL,
  count VARCHAR(150) NULL,
  clinvarid VARCHAR(150) NULL,
  clndn VARCHAR(150) NULL,
  clnsig VARCHAR(150) NULL,
  mc VARCHAR(150) NULL,
  origin VARCHAR(150) NULL,
  g1000id VARCHAR(150) NULL,
  altCount VARCHAR(150) NULL,
  totalCount VARCHAR(150) NULL,
  altGlobalFreq VARCHAR(150) NULL,
  americanFreq VARCHAR(150) NULL,
  asianFreq VARCHAR(150) NULL,
  afrFreq VARCHAR(150) NULL,
  eurFreq VARCHAR(150) NULL,
  disease VARCHAR(150) NULL,
  drugs VARCHAR(150) NULL,
  clinicalSignificance VARCHAR(150) NULL,
  evidenceStatement VARCHAR(150) NULL,
  variantSummary VARCHAR(150) NULL,
  gene VARCHAR(150) NULL,
  proteinChange VARCHAR(150) NULL,
  oncogenecity VARCHAR(150) NULL,
  mutationEffect VARCHAR(150) NULL,
  PRIMARY KEY(id)
  );

  load data local infile '/home/ojaswee/masters_project/01_data/combined_data.csv' into table sample
  FIELDS TERMINATED BY '\t'
  (chr,pos,ref,alt,cosmicid,cds,aa,count,clinvarid,clndn,clnsig,mc,origin,g1000id,
  altCount,totalCount,altGlobalFreq,americanFreq,asianFreq,afrFreq,eurFreq,disease,drugs,
  clinicalSignificance,evidenceStatement,variantSummary,gene,proteinChange,oncogenecity,
  mutationEffect);
