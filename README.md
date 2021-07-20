#Spring boot dockerised app

##This app demonstrates how we can dockerise a spring boot app and pass array type env variables


##Docker build  
`docker build -t helloworld-sb-image -f DockerFile .`

##Docker run
`docker run --name helloworld-sb-container --rm -p 2200:2200 -e FRONTEND_BASEURIS_0=http://localhost:8080 -e FRONTEND_BASEURIS_1=http://localhost:4200 helloworld-sb-image`