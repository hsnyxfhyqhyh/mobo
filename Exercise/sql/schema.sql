DROP TABLE tblUser ;
DROP TABLE tblLocation;
DROP TABLE tblMachine ;
DROP TABLE tblPerformance ;
DROP TABLE tblDay ;



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
CREATE TABLE tblPerformance (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
    "machineFK" INTEGER,
    "userFK" INTEGER,
    "description" TEXT,
    "createDate" TEXT
);
CREATE TABLE tblDay (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
    "day" TEXT,
    "userFK" INTEGER);


INSERT INTO tblUser (name) Values ('影辉');
INSERT INTO tblUser (name) Values ('家宁');

INSERT INTO tblLocation (name) Values ('LA Fitness');
INSERT INTO tblLocation (name) Values ('Downs Farm');

INSERT INTO tblMachine (name, description, locationFK) VALUES ('B37916', '手臂锻炼', 1);

INSERT INTO tblMachine (name, description, locationFK) VALUES ('小区跑步', '小区跑步', 2);


INSERT INTO tblMachine (name, description, locationFK) VALUES ('b37969', '腰部和手部锻炼', 1);

INSERT INTO tblMachine (name, description, locationFK) VALUES ('c38405', '两腿分臀部锻炼', 1);

INSERT INTO tblMachine (name, description, locationFK) VALUES ('c38404', '两腿合臀部锻炼', 1);

INSERT INTO tblMachine (name, description, locationFK) VALUES ('b37959', '手臂锻炼', 1);

INSERT INTO tblMachine (name, description, locationFK) VALUES ('下撑仪器', '手臂锻炼', 1);


INSERT INTO tblMachine (name, description, locationFK) VALUES ('c38402', 'Lower Back锻炼', 1);
INSERT INTO tblMachine (name, description, locationFK) VALUES ('c38419', '腰部转动', 1);