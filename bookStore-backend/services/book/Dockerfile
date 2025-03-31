# 1. Usa imagem base com Java (JDK 21 por exemplo)
FROM eclipse-temurin:21-jdk-alpine

# 2. Define o diretório de trabalho no container
WORKDIR /app

# 3. Copia o .jar para dentro do container
COPY target/books.jar app.jar

# 4. Comando para correr a app
ENTRYPOINT ["java", "-jar", "app.jar"]
