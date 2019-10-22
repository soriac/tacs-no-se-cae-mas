# No se cae mas!
## Docs
https://documenter.getpostman.com/view/5724831/SVfUq5rY

## Variables de entorno
Ver en el archivo `run.sh` de la carpeta `deploy/`

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
Gracias a JaCoCo, después de correr las tests podemos encontrar coverage en `target/site/jacoco`.
```shell script
mvn clean compile test
```

### Token GitHub API

La API v3 de GitHub requiere una token de acceso a una cuenta para aumentar la cantidad de request posibles.
Para crear la token seguir estos links:
- https://developer.github.com/v4/guides/forming-calls/#authenticating-with-graphql
- https://help.github.com/en/articles/creating-a-personal-access-token-for-the-command-line 

Test para ver si funciona todo correcto
```
curl -H "Authorization: bearer TOKEN" -X GET https://api.github.com/graphql
```

> La token generada la deben guardar como 'environment variable' bajo el nombre 'GITHUB_TACS' para que la app pueda usarla


### Documentacion GitHub API

- https://developer.github.com/v3/search/
- https://help.github.com/en/articles/searching-on-github/
- https://help.github.com/en/articles/understanding-the-search-syntax/


### TelegramBot GitHub API

Para poder iniciar una conversacion el TelegramBot primero:

- Instalar y levantar ngrok en puerto 8080
- Copiar y pegar la web HTTPS en la variable telegram_webhook de la clase Server
- Levantar el server
- (Opcion 1) Abrir el siguiente link: https://t.me/GitHubTacsBot
- (Opcion 2) Escribir @GitHubTacsBot en algun chat y hacer click
- Mandar el mensaje /start al bot por Telegram




 

