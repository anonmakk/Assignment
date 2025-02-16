FROM amazoncorretto:21


WORKDIR /app

COPY target/*.jar app.jar

ENV TZ=Asia/Bangkok

ENTRYPOINT ["java","-Duser.timezone=Asia/Bangkok", "-jar", "app.jar"]