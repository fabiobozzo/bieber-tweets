FROM openjdk:11

# the executable spring boot app
ARG JAR_FILE=target/bieber-tweets.jar
ADD ${JAR_FILE} app.jar

# the spring profile to run the app with
ARG SPRING_PROFILES=default
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES}

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
