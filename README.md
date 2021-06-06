
### requirements
- postgres 11+
- java 11

### run locally on docker
```
mvn clean package -DskipTests
docker-compose up -d
```

### dev requirements
- enable annotation processors on intellij
- install lombok on intellij
- run a postgres database with the configuration below
```
host: localhost
port: 5432
dbname: profile (and one for tests profile-test)
spring.datasource.username=postgres
spring.datasource.password=postgres
```

swagger ui:
```
http://localhost:8081/swagger-ui.html
```

staging
```
http://174.138.32.159:8081
```