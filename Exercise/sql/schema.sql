DROP TABLE tblUser ;
DROP TABLE tblLocation;
DROP TABLE tblMachine ;
DROP TABLE tblPerformance ;

CREATE TABLE tblUser (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
    "name" TEXT
, "visible" INTEGER   DEFAULT (1));

CREATE TABLE tblLocation (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
    "name" TEXT);


CREATE TABLE tblMachine (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
    "name" TEXT,
    "description" TEXT
, "locationFK" INTEGER);

CREATE TABLE tblDay (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
    "day" TEXT,
    "userFK" INTEGER);


CREATE TABLE tblPerformance (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
    "machineFK" INTEGER,
    "userFK" INTEGER,
    "description" TEXT,
    "createDate" TEXT
);

INSERT INTO tblUser (name) Values ('Ó°»Ô');
INSERT INTO tblUser (name) Values ('¼ÒÄþ');

INSERT INTO tblLocation (name) Values ('LA Fitness');

INSERT INTO tblMachine (name, description, locationFK) VALUES ('B37916', 'arm exercise machine', 1);