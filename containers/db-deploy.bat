prompt ############################################\n
prompt CREATING DOCKER NETWORK\n
prompt ############################################\n
docker network rm navita
docker network create --driver nat navita

prompt ############################################\n
prompt CREATING POSTGRESQL CONTAINER\n
prompt ############################################\n
docker container rm -f db-navita-auth
docker container rm -f db-navita-parking

docker run --name db-navita-auth --network navita -p 5432:5432 --hostname postgres.database -e POSTGRES_USER=navita -e POSTGRES_PASSWORD=navita -e POSTGRES_DB=db-navita-auth -e POSTGRES_HOST_AUTH_METHOD=trust -d postgres
docker run --name db-navita-parking --network navita -p 5433:5432 --hostname postgres.database -e POSTGRES_USER=navita -e POSTGRES_PASSWORD=navita -e POSTGRES_DB=db-navita-parking -e POSTGRES_HOST_AUTH_METHOD=trust -d postgres

pause