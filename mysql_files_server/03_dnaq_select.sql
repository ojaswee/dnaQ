-- get all the items in queue where userid
SELECT q.testid,t.name, t.type
FROM test t
INNER JOIN queue q ON q.testid = t.testid
WHERE q.userid = 2
AND q.status = 0;


SELECT testid FROM test WHERE name = 'RNA' AND type = 'Saliva';


SELECT count(testid) FROM usertest WHERE userid = 2 AND testid = 4;


UPDATE queue SET status=0 WHERE queueid IN (6,7);


-- this code is used in "masters_project/05_scripts/02_check_queue.sh" to insert in usertest after combined file is created
DELIMITER $$
CREATE TRIGGER insert_new_usertest AFTER INSERT ON usertest
FOR EACH ROW
BEGIN
INSERT INTO usertest (userid,testid,run) VALUES ($userid,$testid,$run);
END $$
DELIMITER ;


SELECT max(run) FROM queue WHERE userid = 2 AND testid = 1;
