-- mysql code for creating database of dnaQ


CREATE DATABASE IF NOT EXISTS dnaq;

USE dnaq;
CREATE TABLE IF NOT EXISTS user (
  userid INT NOT NULL AUTO_INCREMENT,
  firstname VARCHAR(150),
  lastname VARCHAR(150),
  email VARCHAR(150),
  password VARCHAR(150),
  Primary Key (userid),
  UNIQUE Key unique_email (email)
);


CREATE TABLE IF NOT EXISTS test (
  testid int NOT NULL AUTO_INCREMENT,
  name VARCHAR (150),
  type VARCHAR (150),
  cost int,
  PRIMARY KEY (testid)
);

CREATE TABLE IF NOT EXISTS usertest(
  usertestid int NOT NULL AUTO_INCREMENT,
  userid int,
  testid int,
  run int,
  PRIMARY KEY (usertestid),
  FOREIGN KEY (userid) REFERENCES user (userid) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (testid) REFERENCES test (testid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS queue (
  queueid int NOT NULL AUTO_INCREMENT,
  userid int,
  testid int,
  run int,
  status int,
  createdon DATETIME,
  PRIMARY KEY (queueid),
  FOREIGN KEY (userid) REFERENCES user (userid) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (testid) REFERENCES test (testid) ON DELETE CASCADE ON UPDATE CASCADE
);
-------------update name to mutation
CREATE TABLE IF NOT EXISTS mutation (
  usertestid int,
  chr VARCHAR(150) NOT NULL,
  pos INT NOT NULL,
  ref VARCHAR(150) NULL,
  alt VARCHAR(150) NULL,
  freqid VARCHAR(150) NULL,
  globalFreq VARCHAR(150) NULL,
  americanFreq VARCHAR(150) NULL,
  asianFreq VARCHAR(150) NULL,
  afrFreq VARCHAR(150) NULL,
  eurFreq VARCHAR(150) NULL,
  cancerid VARCHAR(150) NULL,
  cancerCount VARCHAR(150) NULL,
  clinicalid VARCHAR(150) NULL,
  clinicalDisease VARCHAR(150) NULL,
  signficance VARCHAR(150) NULL,
  gene VARCHAR(150) NULL,
  biologyDisease VARCHAR(150) NULL,
  publicationCount VARCHAR (150) NULL,
  comment VARCHAR (5000) DEFAULT '',
  PRIMARY KEY (usertestid,chr,pos,ref,alt),
  FOREIGN KEY (usertestid) REFERENCES usertest (usertestid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS lengthOfChr(
  chr VARCHAR(15) NOT NULL PRIMARY KEY,
  length INT NOT NULL
);

CREATE TABLE IF NOT EXISTS report (
  reportid INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(150),
  PRIMARY KEY (reportid)
);

CREATE TABLE IF NOT EXISTS reportlog(
  reportlogid INT NOT NULL AUTO_INCREMENT,
  reportid INT NOT NULL,
  runtime DATETIME,
  PRIMARY KEY (reportlogid),
  FOREIGN KEY (reportid) REFERENCES report (reportid) ON DELETE CASCADE ON UPDATE CASCADE
);


-- DROP DATABASE IF EXISTS dnaq;
-- DROP TABLE mutation ;
-- DROP TABLE queue;
-- DROP TABLE usertest;
-- DROP TABLE test;
-- DROP TABLE user;
-- DROP TABLE report;
-- DROP TABLE reportlog;
