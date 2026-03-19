# chillUML
This is a University project about a UML generator using Use Cases and CRC

## How-to-run
### [Docker]

To run the app you need to have the docker installed. If you already have the docker installed you can continue to the next step.

Open your command line in the project folder. When you open it to start the database you need to type the following command:
```
docker compose up -d
or
docker-compose up -d
```

To start/stop the docker:
```
docker start/stop chillDB
```

### [Spring-boot]

To run the spring boot app after you succesfully started the database you need to go into the program folder and run the maven command to start it.
```
cd chillUML
./mvnw spring-boot:run
```

