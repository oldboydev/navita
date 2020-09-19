prompt ############################################\n
prompt CREATING DOCKER NETWORK\n
prompt ############################################\n
docker network rm navita
docker network create --driver nat navita

prompt ############################################\n
prompt CREATING POSTGRESQL CONTAINER\n
prompt ############################################\n
docker container rm -f db-navita-auth

docker run --name db-navita-auth --network navita -p 5432:5432 --hostname postgres.database -e POSTGRES_USER=navita -e POSTGRES_PASSWORD=navita -e POSTGRES_DB=db-navita-auth -e POSTGRES_HOST_AUTH_METHOD=trust -d postgres

pause