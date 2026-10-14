#!/bin/bash
set -e

cd "$(dirname "$0")/../.."

echo "=== Сборка образа Liquibase ==="
docker build \
  -t bh-database/liquibase:latest \
  -f bh-database/Dockerfile \
  .

echo "=== Готово: образ bh-database/liquibase:latest собран ==="