FROM amazoncorretto:17-alpine-jdk
EXPOSE 8090
ENV APP_HOME /usr/src/app
COPY target/blog-ui-service-0.0.1-SNAPSHOT.jar $APP_HOME/app.jar
WORKDIR $APP_HOME
ENTRYPOINT exec java -jar app.jar