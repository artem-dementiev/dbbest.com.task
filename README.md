# dbbest.com.task
Dijkstra algorithm and DB H2.

### Java application must answers the following questions: 
1) Does the route between two points (id-A and id-B) exist?
2) If the answer is yes, then calculate the minimal route length between id-A and id-B. 

#### Main files: 
- pipeline_system.csv - describes the water pipeline system and has such format "IDX;IDY;LENGTH";
- set_of_points.csv -  a set of points, between which you need to find the route and has such format "IDX;IDY";
- result.csv - output file and has such format "ROUTE EXISTS;MIN LENGTH".

#### Additional files:
- log4j.properties - is a log4j configuration file;
- pipeline_system_to_delete.csv - file which consists nodes which must be deleted; (fill in manually)
- set_of_points_to_delete.csv - file which consists routes which must be deleted. (fill in manually)

### H2 database configuration

- Setting Name: Generic H2 (Embedded);
- Driver Class: org.h2.Driver;
- JDBC URL: jdbc:h2:~/test;
- User Name: scott;
- Password: scott.

#### SQL Commands Used

```SQL 
create user if not exists scott password 'scott' admin;
create table ROUTE(
    id INT IDENTITY(1,1) PRIMARY KEY,
    idX INT,
    idY INT
);
create table NODE(
    id INT IDENTITY(1,1) PRIMARY KEY,
    idX INT,
    idY INT,
    length INT
);
```

## Description
The application uses the command line to interact with the user. Application displays a menu of available actions on the screen. The user selects an item from the menu and receives the corresponding result. The menu consists of items that are described in the table below.

Item         | Description
-------------|------------
1  | Getting data from DB and creating result (finding the shortest distance for each route and writing this data to a file 'result.csv')
2  | Getting data from local files and creating result (finding the shortest distance for each route and writing this data to a file 'result.csv')
3  | Getting 'pipeline_system.csv' from DB
3.1| Displaying data on the console screen
3.2| Writting data to file (The program will ask you to specify the name of the file that will be located in the directory "src/main/resources/userFiles/filename")
3.3| Return to main menu
4  | Getting 'set_of_points.csv' from DB
4.1| Displaying data on the console screen
4.2| Writting data to file (The program will ask you to specify the name of the file that will be located in the directory "src/main/resources/userFiles/filename")
4.3| Return to main menu
5  | Uploading 'pipeline_system.csv' to DB
6  | Uploading 'set_of_points.csv' to DB
7  | Delete nodes in DB which are specified in the file 'pipeline_system_to_delete.csv'
8  | Delete routes in DB which are specified in the file 'set_of_points_to_delete.csv'
9  | Getting result file and display or re-write data to another file
9.1| Displaying data on the console screen
9.2| Writting data to another file (The program will ask you to specify the name of the file that will be located in the directory "src/main/resources/userFiles/filename")
9.3| Return to main menu
10 | Exit from application



