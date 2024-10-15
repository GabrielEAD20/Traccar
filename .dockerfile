# Usa una imagen de base con Java 17 (necesario para Traccar)
FROM openjdk:17-jdk-slim AS builder

# Establecer el directorio de trabajo
WORKDIR /app

# Instalar Gradle
RUN apt-get update && apt-get install -y curl unzip
RUN curl -s https://get.sdkman.io | bash && \
    bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install gradle 7.4.2"

# Copia el código fuente de tu proyecto al contenedor
COPY . .

# Ejecutar el build de Gradle (construcción del servidor Traccar)
RUN ./gradlew assemble

# Segunda etapa para ejecutar el servidor
FROM openjdk:17-jre-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo generado en la etapa anterior
COPY --from=builder /app/target/tracker-server.jar /app/tracker-server.jar

# Exponer el puerto que utiliza Traccar (por defecto es el 8082)
EXPOSE 8082

# Comando para ejecutar el servidor Traccar
ENTRYPOINT ["java", "-jar", "/app/tracker-server.jar"]
