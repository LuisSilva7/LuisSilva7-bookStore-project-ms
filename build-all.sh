#!/bin/bash

# Caminho base dos serviços
SERVICES_PATH="./bookStore-backend/services"
COMMON_PATH="./bookStore-backend/common"

# Lista de serviços por ordem correta (common compila primeiro)
SERVICES=("common" "config-server" "discovery" "user" "book" "cart" "order" "shipping" "composition" "order-query" "gateway")

echo "A compilar todos os serviços..."

for SERVICE in "${SERVICES[@]}"; do
  if [ "$SERVICE" == "common" ]; then
    SERVICE_PATH="$COMMON_PATH"
    echo "A instalar $SERVICE no repositório local..."
    (cd "$SERVICE_PATH" && mvn clean install -DskipTests)
  else
    SERVICE_PATH="$SERVICES_PATH/$SERVICE"
    if [ -f "$SERVICE_PATH/pom.xml" ]; then
      echo "A compilar $SERVICE..."
      (cd "$SERVICE_PATH" && mvn clean package -DskipTests)
    else
      echo "pom.xml não encontrado em $SERVICE_PATH"
    fi
  fi
done

echo "Compilação concluída com sucesso."