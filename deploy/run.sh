#!/bin/bash
export DB_URL="jdbc:mysql://<url>:<puerto>/no_se_cae_mas?useSSL=false&allowPublicKeyRetrieval=true"
export DB_USER="<usuario>"
export DB_PASS="<contraseña>"
export GITHUB_TACS_TELEGRAM_URL="https://$(curl --silent --show-error http://127.0.0.1:4040/api/tunnels | sed -nE 's/.*public_url":"https:..([^"]*).*/\1/p')"
export GITHUB_TACS_TELEGRAM="<token telegram>"
export GITHUB_TACS="<token gh>"
export GITHUB_TACS_REDIS_URL="<url redis>"
export GITHUB_TACS_REDIS_PORT="<puerto redis>"

java -cp no-se-cae-grupo-cuatro-1.0-SNAPSHOT-jar-with-dependencies.jar org.tacs.grupocuatro.Server
