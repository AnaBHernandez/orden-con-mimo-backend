-- Crear tabla de usuarios
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear tabla de espacios (categorías MIMO)
CREATE TABLE IF NOT EXISTS espacio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(1000),
    icono VARCHAR(100),
    color VARCHAR(20),
    usuario_id BIGINT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Crear tabla de tareas
CREATE TABLE IF NOT EXISTS tarea (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(1000),
    categoria VARCHAR(50),
    fecha_creacion TIMESTAMP,
    fecha_limite DATE,
    completada BOOLEAN DEFAULT FALSE,
    usuario_id BIGINT,
    espacio_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (espacio_id) REFERENCES espacio(id)
);