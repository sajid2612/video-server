##Build artifacts
build artifact

##Package Jar
./mvnw package

##Build image
docker build -t restapi-challenge-network:1 .

## Create image as .tar
docker save restapi-challenge-network > restapi-challenge-network.tar

## Load image form .tar
docker load --input restapi-challenge-network.tar

###Volumes
docker volume create storage
docker volume inspect storage

## Create Container, mount with volume and Start it
docker run -d --name video-server-container -p 8080:8080 --mount source=storage,target=/var/lib/data restapi-challenge-network:1
##dockerWithout Volume
##run -d --name restapi-challenge-network -p 8080:8080 restapi-challenge-network:1


## Stop Container
docker stop restapi-challenge-network

## Start already created container
docker start restapi-challenge-network




filter name=test filter type=mp4
