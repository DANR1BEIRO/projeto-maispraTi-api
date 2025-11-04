CREATE TABLE "UserSystem" (
    id INT AUTO_INCREMENT PRIMARY KEY,
    "nomeCompleto" VARCHAR(255) NOT NULL,
    "email" VARCHAR(255) NOT NULL,
    "senhaHash" VARCHAR(255) NOT NULL,
    "fotoPerfil" VARCHAR(255),
    "role" VARCHAR(50),
    "streakAtual" INT,
    "grupoAtualId" INT,
    "createdAt" TIMESTAMP,
    "updatedAt" TIMESTAMP
);

CREATE TABLE progresso_jornada (
    id INT AUTO_INCREMENT PRIMARY KEY,
    grupo_atual VARCHAR(255),
    user_id INT,
    grupos_concluidos VARCHAR(500),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "UserSystem"(id)
);
