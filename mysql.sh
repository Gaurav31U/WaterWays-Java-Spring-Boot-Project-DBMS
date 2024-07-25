#!/bin/bash
set -e

host="mysql"
port="3306"
cmd="$@"

echo "Checking if MySQL is up on $host:$port..."
until mysqladmin ping -h "$host" -P "$port" --silent; do
  echo "Waiting for MySQL to be up..."
  sleep 2
done

echo "MySQL is up - running migrations"
# file="Schema.sql"
for file in /app/sql/*.sql; do
  echo "Running migration: $file"
  mysql -h "$host" -P "$port" -u "root" -p"password" "waterways" < "$file"
done

echo "Migrations completed - executing command"
exec $cmd
