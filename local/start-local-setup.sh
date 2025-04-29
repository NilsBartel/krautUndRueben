#!/usr/bin/env bash
cd "$(dirname "$0")" || exit

echo
echo "                                    Port number:"
echo " * MySQL                            3306"
echo

docker compose -f ../docker-compose.yaml -p krautundeueben up -d
