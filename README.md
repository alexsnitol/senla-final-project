## Description of application Real Estate Market

This project was developed as a final work after the Java courses for web developers from SENLA.

Start of development: 09.08.2022 \
First version published: 19.09.2022

**The Real Estate Market** is **REST API** application which allows us to sell real estate and rent out.

### Features

Main tasks which resolve this project:
* User registration in the system
* Profile editing
* View a list of announcements with searched, multi filters and sorting
* Ability to add / editing / deleting announcements
* Possibility to leave reviews under the owner's profile
* Owner rating system that affects the position of the owner's listings in search results. The lower the rating, the lower the position in the search results.
* Chat for clients with owners of announcements
* Ability to buy a displaying announcement on top of a list
* History of closed owner announcements
* Possibility of buying or renting real estate

This application supports the following types of real estate:
* 🏙️ Apartment
* 🏠 Family house
* 🌳 Land 

### Dependencies

This system was developed using:
* Java 11
* Gradle
* Spring Framework 5.3 (webmvc, data, security, aop, validator and others)
* Hibernate 5.6
* Servlet 4
* JWT
* RSQL 2
* Lombok
* MapStruct 1.5
* SpringFox 3 Swagger
* LogBack 1.2
* SonarLint
* and others, see project dependencies

As storage data using PostgreSQL 10.

The following used to unit testing of the system:
* Junit Jupiter 5.9
* Mockito

### Deploying

All diagrams this project located in the *diagram* folder.

All completed resources for deploy this project located in the *deploy* folder:
* `deploy.bat` and `deploy_with_db.bat` build the project and deployed it into tomcat: coping `war` on webapps and start server
* In *database* folder located sql init scripts for PostgreSQL, there is also a `bat` script for an init database on Windows
* In *docker* folder located `docker-compose.yml` which contains all settings, there is also contains docker folder wherein separate `Dockerfile`

> ️**ATTENTION**\
> Before building the project, configure `persistence.xml` for your database. Particularly for docker-compose you need to replace `localhost` on name of container default is `database`. 

### Usage
For manually testing this system
and getting more information of all endpoints and details of request bodies and responses,
you can use Swagger UI.
You may open it from the link `http://your-host/swagger-ui/index.html`.
For example `http://localhost:8080/real-estate-market/swagger-ui/index.html`.

And also, I uploaded Insomnia config files to _insomnia_ folder.

The vast majority of GET queries have the ability to filter and sort:
* for **filtering**, using RSQL: `?q=rsql-expression` ([template of queries looks here](https://github.com/jirutka/rsql-parser))
* for **sorting** using following template: `?sort=field1,direction;field2,direction` or `?sort=field,direction`.