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


INSERT INTO tblUser (name) Values ('Ӱ��');
INSERT INTO tblUser (name) Values ('����');

INSERT INTO tblLocation (name) Values ('LA Fitness');
INSERT INTO tblLocation (name) Values ('Downs Farm');

INSERT INTO tblMachine (name, description, locationFK) VALUES ('B37916', '�ֱ۶���', 1);

INSERT INTO tblMachine (name, description, locationFK) VALUES ('С���ܲ�', 'С���ܲ�', 2);


INSERT INTO tblMachine (name, description, locationFK) VALUES ('b37969', '�������ֲ�����', 1);

INSERT INTO tblMachine (name, description, locationFK) VALUES ('c38405', '���ȷ��β�����', 1);

INSERT INTO tblMachine (name, description, locationFK) VALUES ('c38404', '���Ⱥ��β�����', 1);

INSERT INTO tblMachine (name, description, locationFK) VALUES ('b37959', '�ֱ۶���', 1);

INSERT INTO tblMachine (name, description, locationFK) VALUES ('�³�����', '�ֱ۶���', 1);


INSERT INTO tblMachine (name, description, locationFK) VALUES ('c38402', 'Lower Back����', 1);
INSERT INTO tblMachine (name, description, locationFK) VALUES ('c38419', '����ת��', 1);