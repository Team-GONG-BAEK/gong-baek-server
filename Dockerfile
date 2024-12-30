FROM eclipse-temurin:21
WORKDIR /app
COPY ./build/libs/*.jar gongbaek.jar
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "gongbaek.jar"]