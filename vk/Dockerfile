FROM amazoncorretto:17
ARG JAR_FILE=build/libs/vk.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
