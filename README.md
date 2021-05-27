
### requirements
postgres 11+
```
url=jdbc:postgresql://localhost:5432/profile
host: localhost
port: 5432
dbname: profile (ayrica unit testler icin profile-test)
spring.datasource.username=postgres
spring.datasource.password=postgres
```

swagger ui:
```
http://localhost:8081/swagger-ui.html
```

### TODO
- ~~profile save/get~~
- ~~image upload~~
- ~~candidates~~
- ~~temp candidate calculation~~
- ~~imagelari paylasma (blob sanirim)~~
- ~~validation (dogum gunu eksik vs.)~~
- ~~error handlers + response messages~~
- ~~postgres + earthdistance~~
- yukselen, ya da astrolojik hesaplar
- dockerize
- cicd (+hosting)
- guvenlik (headerdan, profil sahibini bulup o operasyona izni var mi diye kontrol)
- notification
