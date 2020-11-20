echo "Remove old DB"
docker container rm -f db 2>&1
echo "Pull postgres image"
docker pull postgres:12.3-alpine
echo "Run postgres... "
docker container run -d --name db --publish 5433:5432 -e POSTGRES_PASSWORD=admin postgres:12.3-alpine

echo "create db"
sleep 5
docker exec db bash -c "
psql -U postgres -c '
CREATE DATABASE tododb
    WITH
    OWNER = postgres
    ENCODING = "UTF8"
    CONNECTION LIMIT = -1;
    '"
