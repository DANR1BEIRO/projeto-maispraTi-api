CREATE DATABASE projeto_maisprati;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    nome_completo VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    foto_perfil TEXT,
    streak_atual INT DEFAULT 0,
    grupo_atual VARCHAR(50)
);

CREATE TABLE journey_progress (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    grupo_atual VARCHAR(50),
    grupos_concluidos JSONB
);

CREATE TABLE exercises (
    id SERIAL PRIMARY KEY,
    grupo VARCHAR(50),
    pergunta TEXT NOT NULL,
    alternativas JSONB NOT NULL,
    resposta_correta VARCHAR(255) NOT NULL
);

CREATE TABLE user_exercise_results (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    exercise_id INT REFERENCES exercises(id),
    resposta VARCHAR(255),
    correta BOOLEAN,
    data_resposta TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE daily_challenges (
    id SERIAL PRIMARY KEY,
    pergunta TEXT NOT NULL,
    alternativas JSONB NOT NULL,
    resposta_correta VARCHAR(255) NOT NULL,
    data_disponivel DATE NOT NULL
);

CREATE TABLE daily_results (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    challenge_id INT REFERENCES daily_challenges(id),
    resposta VARCHAR(255),
    correta BOOLEAN,
    data_resposta TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
    user_id INT REFERENCES users(id),
    role VARCHAR(20) DEFAULT 'USER'
);









