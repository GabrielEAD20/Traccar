# Usa una imagen base de OpenJDK 17
FROM openjdk:17-jdk-slim AS builder

# Establecer el directorio de trabajo
WORKDIR /app

# Instalar dependencias necesarias (curl y unzip)
RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# Copiar todos los archivos del proyecto al contenedor
COPY . .

# Asegurarse de que gradlew tiene permisos de ejecución
RUN chmod +x ./gradlew

# Ejecutar la construcción de Gradle (construir el servidor Traccar)
RUN ./gradlew assemble

# Segunda etapa para crear una imagen ligera solo con el JRE y el JAR
FROM openjdk:17-jre-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR generado desde la etapa de construcción anterior
COPY --from=builder /app/target/tracker-server.jar /app/tracker-server.jar

# Exponer el puerto que utiliza Traccar (8082 por defecto)
EXPOSE 8082

# Comando para ejecutar el servidor Traccar
ENTRYPOINT ["java", "-jar", "/app/tracker-server.jar"]
