CREATE OR REPLACE USER 'aneo'@'localhost' IDENTIFIED BY 'devoxx2017';

CREATE DATABASE herodb;

GRANT CREATE, INSERT, SELECT, DELETE, UPDATE ON herodb.* to 'aneo'@'localhost' IDENTIFIED BY 'devoxx2017';

CREATE DATABASE arenadb;

GRANT CREATE, INSERT, SELECT, DELETE, UPDATE ON arenadb.* to 'aneo'@'localhost' IDENTIFIED BY 'devoxx2017';
