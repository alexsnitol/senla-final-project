# Documentation for application Real estate market

This project was developed as a final work after the Java courses for web developers from SENLA.

All diagrams this project located in the *diagram* folder.

All completed resources for deploy this project located in the *deploy* folder:
* `deploy.bat` and `deploy_with_dab.bat` build project and deployed it into tomcat: coping `war` on webapps and start server
* In *database* folder located sql init scripts for PostgreSQL, there is also a `bat` script for an init database on Windows
* In *docker* folder located `docker-compose.yml` which contains all settings, there is also contains docker folder wherein separate `Dockerfile`

Main tasks which resolve this project:
* User registration in the system
* Profile editing
* View list of announcements with search and filter
* Ability to adding / editing / deleting announcements
* Possibility to leave reviews under the owner's profile
* Owner rating system that affects the position of the owner's listings in search results. The lower the rating, the lower the ad in the search results
* Chat for clients with owners of announcements
* Ability to buy a displaying announcement on top of a list
* History of closed owner announcements
* Possibility of buying or renting real estate

For more information on all endpoints and details of request bodies and responses the project supports Swagger UI,
you can open it from the link `*/swagger-ui/index.html`.