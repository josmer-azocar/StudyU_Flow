# Etapa 1: Construcción de la aplicación con Maven y JDK 21
# CORRECCIÓN: Se usa una etiqueta de imagen válida que incluye Maven y OpenJDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Creación de la imagen final con JDK 21
# Se usa una imagen ligera de OpenJDK 21 para ejecutar la aplicación
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]