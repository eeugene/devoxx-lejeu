CREATE USER 'aneo'@'localhost' IDENTIFIED BY 'devoxx2017';

CREATE DATABASE herodb;
CREATE DATABASE arenadb;

GRANT ALTER, REFERENCES, CREATE, INSERT, SELECT, DELETE, UPDATE ON herodb.* to 'aneo'@'localhost' IDENTIFIED BY 'devoxx2017';
GRANT ALTER, REFERENCES, CREATE, INSERT, SELECT, DELETE, UPDATE ON arenadb.* to 'aneo'@'localhost' IDENTIFIED BY 'devoxx2017';
