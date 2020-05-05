cd ./ProSource
docker build -t java-image .
docker run -i --rm --name java-container java-image