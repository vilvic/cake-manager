FROM openjdk:17

COPY ./target/cake-manager-server-*.jar /usr/src/cake-manager-server.jar

WORKDIR /usr/src

EXPOSE 8080

CMD ["java", "-jar", "cake-manager-server.jar", "-Djava.security.egd:/dev/./urandom"]
