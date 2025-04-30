#!/bin/bash

echo "A construir todos os microserviços com Maven (sem testes)"

# Caminho para a pasta dos serviços
SERVICES_DIR="./bookStore-backend/services"

# Percorre cada subdiretório (serviço)
for SERVICE in "$SERVICES_DIR"/*; do
  if [ -d "$SERVICE" ]; then
    echo "A empacotar $(basename "$SERVICE")..."
    (cd "$SERVICE" && ./mvnw clean package -DskipTests)
    echo "$(basename "$SERVICE") concluído."
  fi
done

echo "Todos os microserviços foram construídos!"
