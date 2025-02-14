# Application information

## Development

The projects is split into two parts.

### customs-backend-app

This module has no UI and provides the necessary backend actions.

Currently there are two profiles defined:

* h2: Uses an in memory test database
* dev: uses a postgresql test database. (See the postgresql-compose.yml file to setup the database)

### customs-ui

This module provides a UI. This module communicates with the backend using rest calls.

### common parts

There are two common modules.

* customs-commons: This module contains common data model (like enums)
* customs-backend-api: This module contains the API for the rest calls.




## Setup Containerfile

> Tip: If you use Podman simply replace the 'docker' command by 'podman' and 'docker-compose' by 'podman-compose'. For 'podman-compose' the compose libraries must be in the path.

1. Call ´´mvn clean install´´ in the root folder.
2. Go the customs-backend-app module. In the target folder a customs-backend-app-<version>.jar was generated. In the root folder of this module you find a Dockerfile. Execute the following command:

```bash
# Build the Docker image
docker build -t customs-backend-app:<version> .
```

3. Go the customs-ui module. In the target folder a customs-ui-<version>.jar was generated. In the root folder of this module you find a Dockerfile. Execute the following command:

```bash
# Build the Docker image
docker build -t customs-backend-app:<version> .
```

4. Open the '/setup/h2' or '/setup/postgresql' folder. 
5. Before you start docker-compose check the selected versions (Should be equal to the one which was used during the build command).
6. To start with docker-compose execute the following command:

```bash
# Start
docker-compose up -d
# Stop
docker-compose stop
# remove (volumes are not removed, remove the volumes manually or use parameter -v)
docker-compose down
```

### PostgreSql database only

The folder '/setup/postgresql' contains an additional docker-compose file: 'postgresql-compose.yaml'. This file
file simply contains the setup of a postgresql database. This setup can be used for local development with a 
postgresql database.

```bash
docker-compose -f postgresql-compose.yaml up
```


