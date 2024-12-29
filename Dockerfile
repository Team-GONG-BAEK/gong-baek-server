FROM eclipse-temurin:21
WORKDIR /app
COPY ./build/libs/*.jar 0ggang.jar
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "0ggang.jar"]