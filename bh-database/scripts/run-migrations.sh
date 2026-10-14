#!/bin/bash
docker run --rm bh-database/liquibase:latest \
  --url="jdbc:postgresql://host.docker.internal:5432/bh_user_service" \
  --username="postgres" \
  --password="233122" \
  --log-level=INFO \
  update