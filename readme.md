# Application information

## Deployment

Currently there is a docker-compose file which can be used for a deployment on Docker or Podman. For this you need to compile the modules (e.g. mvn clean install), 
build the image and deploy the result to a container engine.

See the section: Setup Containerfiles 

## Development

This project is split into the following structure. This structure follows the DDD principles (Ports and Adapters, Onion Architecture):

```
+- customs-commons
 - customs-backend-app
 - customs-backend-core // DDD driven structure
   + adapter
     - in
       - web // RestAPI implementation
     - out
       - storage       // storage tasks
       - notifications // notification tasks
   + application
     - domain
       - model
       - service
     + port // Use cases for incoming and outgoing operations
       - in
       - out 	
 - customs-backend-api // API part which provides a RestAPI for the UI
 - customs-backend-jap // JPA implementation (Liquibase, H2, PostgreSQL)
 - customs-ui // uses the customs-commons, customs-backend-api)
 - setup
    + docker // docker compose files 
    + helm   // helm charts
```

The projects is split into two parts (customs-backend-xxx and customs-ui).

> Using an OpenAPI configuration would be a better choice. Currently, the customs-backend-app generates the OpenAPI documents (e.g. http://localhost:8080/v3/api-docs, http://localhost:8080/v3/api-docs.yaml) 
> and a Swagger-Ui (e.g. http://localhost:8080/swagger-ui/index.html). The target must be that the customs-ui uses the OpenAPI api-docs to generate the API for the communication. In this case the customs-ui
> is independent and does not need to import the customs-backend-api directly.


### customs-backend-app

This module has no UI and provides the necessary backend actions.

#### database

Currently, there are two profiles defined:

* h2: Uses an in memory test database
* dev: uses a postgresql test database. (See the postgresql-compose.yml file to setup the database)

> The database is configured by using liquibase. 

The database configuration files are used indirectly. This means that there are Spring compatible configuration files (db.yaml, db-dev.yaml, db-h2.yaml) which are not loaded directly 
by Spring during start up. Instead this configuration files needs to be imported by the application.yaml file in the module customs-backend-app. To support the profiles (h2, dev) the 
db.yaml file in the module customs-backend-jpa itself imports the profiles.

> Why is the database module not part of the customs-backend-core? 
>
> The basic idea behind this decision is that we probably want to use another storage engine (e.g. BaSyx) later on. In this case the customs-backend-app could be copied, the pom.xml 
> must be changed to import the different modules (e.g. BaSyx) and the application.yaml must configure the new modules.

### customs-ui

This module provides a UI. This module communicates with the backend using rest calls.

### common parts

There are two common modules.

* customs-commons: This module contains common data model (like enums)
* customs-backend-api: This module contains the API for the rest calls.

## Setup Containerfiles

> Tip: If you use Podman simply replace the 'docker' command by 'podman' and 'docker-compose' by 'podman-compose'. For 'podman-compose' the compose libraries must be in the path.

1. Call ´´mvn clean install´´ in the root folder.
2. Go the customs-backend-app module. In the target folder a customs-backend-app-<version>.jar was generated. In the root folder of this module you find a Dockerfile. Replace the <version> tag and execute the command.:

```bash
# Build the Docker image
docker build -t customs-backend-app:<version> .
```

3. Go the customs-ui module. In the target folder a customs-ui-<version>.jar was generated. In the root folder of this module you find a Dockerfile. Replace the <version> and execute the command:

```bash
# Build the Docker image
docker build -t customs-backend-app:<version> .
```

4. Open the '/setup/docker/h2' or '/setup/docker/docker/postgresql' folder. 
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

###  Minikube (Kubernetes): Setup Containerfiles

The most easy way to push the container file is to build the container using Minikube. The command is more or less 
similar to the above Docker commands. Replace the <app-name> and <version> and start the command.

```bash
# Build the Docker for customs-backend-app image using Minikube
minikube image build -t localhost/<app-name>:<version> .
```

You can verify if the command was successful by executing the following command which shows the loaded images.

```bash
# Show images loaded by Minikube
minikube image ls
```

There are other possible ways which are possible to get the image loaded into Minikube.
* With Podman you can push the image directly from Podman-Desktop
* There is a ```minikube image load``` command. This needs some additional configuration of the command shell. (If you use Podman you need 
to use the container-runtime  __cri-o__  which on the other hand seems not working with the Ingress addon)
* You can push the image to a registry.


### PostgreSql database only

The folder '/setup/postgresql' contains an additional docker-compose file: 'postgresql-compose.yaml'. This file
file simply contains the setup of a postgresql database. This setup can be used for local development with a 
postgresql database.

```bash
docker-compose -f postgresql-compose.yaml up -d
```

## Kubernetes

In this section we describe how to deploy the application to a Kubernetes cluster. The deployment is done using
the Minikube cluster. The deployment is done in the following steps (for some steps administration rights are needed):

> To install the customs services in a dedicated namespace (e.g. 'customs') you can add the following snippet to 
> the install command: ```--namespace customs --create-namespace```

1. Install Kubectl and Helm
2. Install and start Minikube
2. Build the Docker images (using Minikube; you can also copy the images to the Minikube cluster later on). See above.
3. Navigate to the '/setup/helm/customs' folder.
4. Execute ```helm install customs .``` to deploy the application.
5. Do use the deployment you can forward the 'ui' and 'postgres' port.

additional support using ingress (instead of port forwarding):

5. Install the Ingress addon for Minikube: ```minikube addons enable ingress```
6. Deploy the Ingress configuration: Property 'ingress.enabled' in the values.yaml file must be set to true (=default).
7. Execute ```helm install customs .``` to deploy the application.
8. Edit the '/etc/hosts' (on Windows normally c:\Windows\System32\drivers\etc) file and add the following line: ```127.0.0.1 ui.customs```
9. Open the Minikube tunnel: ```minikube tunnel```
10. Open the browser and navigate to 'http://ui.customs'

> Currently, there is no check that the database is up before the backend-app starts. This can lead to some strange behaviour.

> If you want to access the database using a tool not installed in the Kubernetes cluster you can use the following 
> command to forward the port: ```kubectl port-forward svc/<db-service> 5432:5432```. Be aware the name of the database 
> service depends on the name you have given during the hell install command. If you have used 'customs' the service name
> should be 'customs-postgres'. Setting up a port forward can be done easier by using a tool like 'Lens' which provides
> a GUI for the Kubernetes cluster.

### Using different namespaces

If you use different namespaces the URL accessing the other namespace looks as follows:

```bash
http://<service-name>.<namespace>.svc.cluster.local:<port>
```
Let's say someone wants to acces the customs-backend service in the customs namespace. The URL would look like this:

```bash
http://customs-backend.customs.svc.cluster.local:8080
```
