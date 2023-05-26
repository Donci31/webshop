FROM eclipse-temurin
WORKDIR /app
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
 
COPY src ./src

RUN ./mvnw package -DskipTests
 
CMD ["java", "-jar", "target/alf2023-javan-t-0.0.1-SNAPSHOT.jar"]

