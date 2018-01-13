DROP TABLE Team CASCADE CONSTRAINTS;
DROP TABLE Schedule CASCADE CONSTRAINTS;
DROP TABLE Employee CASCADE CONSTRAINTS;

DROP SEQUENCE hibernate_sequence;

CREATE SEQUENCE hibernate_sequence START WITH 1001 INCREMENT BY 1;


CREATE TABLE Team(
    TeamID varchar2(15)NOT NULL PRIMARY KEY,
    TeamName varchar2(100) NOT NULL
);

insert into Team values('Team1','Dev');
insert into Team values('Team2','Support');
insert into Team values('Team3','Testing');

CREATE TABLE Employee(
    employeeID varchar2(15) NOT NULL PRIMARY KEY,
    employeeName varchar2(100) NOT NULL,
    employeeTeam varchar2(15),
    FOREIGN KEY (employeeTeam) REFERENCES Team(TeamID)
);


insert into Employee values('Emp1','John','Team1');
insert into Employee values('Emp2','Jack','Team2');
insert into Employee values('Emp3','Jenny','Team3');
insert into Employee values('Emp4','Johny','Team1');
insert into Employee values('Emp5','Jacky','Team2');
insert into Employee values('Emp6','Jen','Team3');
insert into Employee values('Emp7','Jacob','Team1');
insert into Employee values('Emp8','Jill','Team2');
insert into Employee values('Emp9','Jolly','Team3');

CREATE TABLE Schedule(
    scheduleId varchar2(15)NOT NULL PRIMARY KEY,
    employeeId varchar2(100) NOT NULL,
    scheduledDate varchar2(10) NOT NULL,
    employeeTeam varchar2(50) NOT NULL,
    FOREIGN KEY (employeeId) REFERENCES Employee(employeeID),
    FOREIGN KEY (employeeTeam) REFERENCES Team(TeamID)
);

COMMIT;