#!/usr/bin/env bash
cd "$(dirname "$0")" || exit

# By default, all volumes (all custom data) are removed, when shutting down.
# If you want to keep your data, provide the parameter "keep":
#     ./stopmocks.sh keep

if [ "$1" = "keep" ]; then
  docker compose -f ../docker-compose.yaml -p krautundeueben down
else
  docker compose -f ../docker-compose.yaml -p krautundeueben down --volumes
fi
