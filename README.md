# No se cae mas!

## Buildear .jar y correr
```shell script
mvn clean compile package
java -cp target/no-se-cae-grupo-cuatro-1.0-SNAPSHOT-jar-with-dependencies.jar org.tacs.grupocuatro.Server
```

## Correr un mysql local con docker
Está bueno para desarrollar si no tenés ganas de instalar mysql o queres un container que podés borrar tranquilo.
```shell script
docker start no-se-cae-mysql || docker run --name no-se-cae-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=dev -d mysql:8
docker exec -it no-se-cae-mysql mysql -uroot -p
CREATE DATABASE no_se_cae_mas
```

## Correr tests
```shell script
mvn clean compile test
```