FROM eclipse-temurin:21
WORKDIR /app
COPY ./build/libs/*.jar gongbaek.jar
COPY ./src/main/resources/application-prod.yml application-prod.yml
CMD ["java", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "-jar", "gongbaek.jar"]