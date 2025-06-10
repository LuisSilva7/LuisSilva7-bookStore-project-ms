#!/bin/bash

echo "🧹 A limpar builds antigos..."
./build-all.sh || { echo "Erro ao compilar os microserviços"; exit 1; }

echo "A parar containers antigos..."
docker-compose -f docker/backend/docker-compose.yml down -v

echo "A construir e subir todos os serviços com Docker Compose..."
docker-compose -f docker/backend/docker-compose.yml up --build