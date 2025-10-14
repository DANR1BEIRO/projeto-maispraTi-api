# Etapa 1: Build da aplicação
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copia os arquivos do projeto
COPY . .

# Faz o build do projeto usando o Maven Wrapper (ou Maven instalado)
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagem final (leve)
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o JAR gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Define a variável de ambiente da porta (Render usa PORT automaticamente)
ENV PORT=8080
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]