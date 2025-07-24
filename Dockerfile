# Usar una imagen base de OpenJDK con soporte para Java 21
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar archivos necesarios para Gradle
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Otorgar permisos de ejecución al wrapper de Gradle
RUN chmod +x gradlew

# Descargar dependencias (esto acelera builds posteriores)
RUN ./gradlew dependencies --no-daemon || true

# Copiar el código fuente y resources
COPY src ./src

# Copiar el application.properties si lo necesitás embebido (aunque se puede configurar por variables de entorno en Render)
COPY src/main/resources/application.properties ./src/main/resources/

# Construir el JAR
RUN ./gradlew bootJar --no-daemon

# Exponer el puerto por defecto (Render necesita esto)
EXPOSE 8080

# Ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "build/libs/MiPriApi-0.0.1-SNAPSHOT.jar"]