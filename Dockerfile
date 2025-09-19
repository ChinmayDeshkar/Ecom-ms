FROM openjdk
WORKDIR /app
COPY . /app
CMD [ "java", "-jar", "OrderService-0.0.1-SNAPSHOT.jar" ]
EXPOSE 8081