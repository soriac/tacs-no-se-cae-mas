# Pasos para deploy


## Instancia
- Levantar una instancia común de EC2

- Instalar dependencias
```
sudo amazon-linux-extras install java-openjdk11
instalar ngrok desde su pagina
```

- Copiar archivos de servicio a `/etc/systemd/system/tacs.service` y `/etc/systemd/system/ngrok.service`

- Copiar archivos run.sh y run_ngrok.sh a /app


## Server
- Buildear .jar
```
mvn clean compile package
```

- Deployar a instancia de EC2 en la carpeta /app

- Settear variables de entorno en /run.sh

- Correr servicio de server

## Client
- Settear la URL del server en `/src/api/index.ts BASE_URL`

- Armar los archivos estáticos
```
npm install
npm run build
```

- Deployar al balde de Amazon.
