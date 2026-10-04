# Etapa 1: compilar y empaquetar
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY src ./src
RUN mkdir out \
 && javac -encoding UTF-8 -d out $(find src -name "*.java") \
 && jar --create --file app.jar --main-class Main -C out .

# Etapa 2: imagen final
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/app.jar .
# /data es la carpeta donde appuser puede escribir los .txt
RUN useradd --system --no-create-home appuser \
 && mkdir /data \
 && chown appuser /data
USER appuser
WORKDIR /data
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/app.jar"]