CREATE OR REPLACE USER 'aneo'@'localhost' IDENTIFIED BY 'devoxx2017';

CREATE DATABASE herodb;

GRANT ALL ON `herodb.*` to 'aneo'@'localhost';

CREATE DATABASE arenadb;

GRANT ALL ON `arenadb.*` to 'aneo'@'localhost';
