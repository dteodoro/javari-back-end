mvn clean package -DskipTests
docker build --build-arg SERVICE=javari-auth -t doteodoro/javari-auth:latest . 
docker build --build-arg SERVICE=javari-connector -t doteodoro/javari-connector:latest . 
docker build --build-arg SERVICE=javari-discovery -t doteodoro/javari-discovery:latest . 
docker build --build-arg SERVICE=javari-gateway -t doteodoro/javari-gateway:latest . 
docker build --build-arg SERVICE=javari-game -t doteodoro/javari-game:latest . 