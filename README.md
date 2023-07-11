1. run docker-compose up --this will create postgres database image
2. in project root directory run ./gradelw build --this will run tests and create .jar
3. to start application in project root directory run java -jar build/libs/blog-0.0.1-SNAPSHOT.jar
4. make a request for example curl --location 'localhost:8080/posts' \
--header 'Content-Type: application/json' \
--data '{
    "userId": "2a3f7b76-1fc2-11ee-be56-0242ac120002",
    "content": "somecontent"
}'
