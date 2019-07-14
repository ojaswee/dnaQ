-- login to mysql server
mysql -u root

--
-- GRANT ALL ON dnaq.* TO root@'129.207.46.222' IDENTIFIED BY '';

FLUSH PRIVILEGES;

-- GRANT ALL PRIVILEGES ON dnaq.* TO 'root'@'%' IDENTIFIED BY '' WITH GRANT OPTION;

-- ALTER USER 'root'@'192.168.1.7'
-- mysql -uroot -h 129.207.46.222


SET PASSWORD FOR 'root'@'192.168.1.7' = PASSWORD('main')

SELECT user,host,password FROM mysql.user;
+-----------+---------------+-------------------------------------------+
| user      | host          | password                                  |
+-----------+---------------+-------------------------------------------+
| root      | localhost     | *E9EE5658D54FD1F691EEEF19D836CFFD18C64B28 |
| root      | pvamucloud222 |                                           |
| root      | 127.0.0.1     |                                           |
| root      | ::1           |                                           |
|           | localhost     |                                           |
|           | pvamucloud222 |                                           |
| root      | 192.168.1.7   | *0D3DC0AC8220B4FF12465D834FBE53D5E231B604 |
| odhungana | localhost     |                                           |
+-----------+---------------+-------------------------------------------+

DROP USER 'root'@'%';
