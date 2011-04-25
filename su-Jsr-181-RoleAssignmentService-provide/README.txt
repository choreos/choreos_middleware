set up database access
======================

Create user and database in MySQL
---------------------------------
CREATE USER 'choreos'@'localhost' IDENTIFIED BY  'password';

GRANT USAGE ON * . * TO  'choreos'@'localhost' IDENTIFIED BY  'password' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;

CREATE DATABASE IF NOT EXISTS  `choreos` ;

GRANT ALL PRIVILEGES ON  `choreos` . * TO  'choreos'@'localhost';

Configure hibernate
-------------------
$ cp src/main/resources/
$ cp hibernate.cfg.template.xml hibernate.cfg.xml
Edit hibernate.cfg.xml and change database (choreos), user (root) and password (empty).
