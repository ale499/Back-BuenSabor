# Usar una imagen base de OpenJDK con soporte para Java 21
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo de configuración de Gradle y el archivo build.gradle
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Otorgar permisos de ejecución al archivo gradlew
RUN chmod +x gradlew

# Instalar las dependencias del proyecto
RUN ./gradlew build --no-daemon || true

# Copiar el resto de los archivos del proyecto
COPY src ./src
COPY src/main/resources/application.properties ./src/main/resources/

# Construir el proyecto
RUN ./gradlew bootJar --no-daemon

# Exponer el puerto definido en application.properties
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "build/libs/MiPriApi-0.0.1-SNAPSHOT.jar"]