# Projeto +praTi

API desenvolvida em **Java + Spring Boot**, com integração a banco de dados **PostgreSQL (Neon)**.  
Este guia explica como configurar o ambiente e executar o projeto localmente.

---

## 🚀 Tecnologias utilizadas

- **Java 21**
- **Spring Boot 3.5.5**
- **Maven**
- **PostgreSQL (Neon)**
- **JPA / Hibernate**

---

## Pré-requisitos

Antes de rodar o projeto, certifique-se de ter instalado:

- [Java 21+](https://adoptium.net/)
- [Maven 3.9+](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/)
- Um banco **PostgreSQL** (pode ser local ou o Neon Cloud)

---

## Configuração das variáveis de ambiente

O projeto usa variáveis de ambiente para evitar expor credenciais diretamente no código.

Crie as variáveis de ambiente abaixo de acordo com o seu sistema operacional.

### No **PowerShell** (Windows)

powershell
$env:DB_URL = "jdbc:postgresql://<host>/<database>?sslmode=require"
$env:DB_USER = "<usuario>"
$env:DB_PASS = "<senha>"

### No CMD (Windows)

set DB_URL=jdbc:postgresql://<host>/<database>?sslmode=require
set DB_USER=<usuario>
set DB_PASS=<senha>

### No Linux / macOS

export DB_URL=jdbc:postgresql://<host>/<database>?sslmode=require
export DB_USER=<usuario>
export DB_PASS=<senha>

### Exemplo real com Neon:

export DB_URL=jdbc:postgresql://ep-rapid-pond-123456-pooler.us-east-2.aws.neon.tech/neondb?sslmode=require
export DB_USER=neondb_owner
export DB_PASS=abc123

### Configuração do Spring Boot

O projeto usa o arquivo application-prod.properties com as variáveis:

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

## Executando o projeto
### Clone o repositório:

git clone https://github.com/seu-usuario/seu-projeto.git
cd seu-projeto

### Instale as dependências:

mvn clean install

Rode a aplicação com o perfil prod (para usar o banco Neon):

mvn spring-boot:run -Dspring-boot.run.profiles=prod

### A aplicação iniciará em:

http://localhost:8080

# Projeto MaisPraTi API

## 🚀 Como Rodar o Projeto Localmente

1.  Clone este repositório.
2.  Certifique-se de que você tem o PostgreSQL instalado e rodando.
3.  Crie um banco de dados para o projeto (ex: `CREATE DATABASE maispra_ti_db;`).
4.  Configure as seguintes variáveis de ambiente na sua máquina ou na sua IDE:

    ```bash
    export DB_NAME=maispra_ti_db
    export DB_USER=seu_usuario_postgres
    export DB_PASSWORD=sua_senha_secreta
    ```

5.  Rode a aplicação pela sua IDE.