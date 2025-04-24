CREATE TABLE IF NOT EXISTS tarea (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(1000),
    categoria VARCHAR(50),
    fecha_creacion TIMESTAMP,
    fecha_limite DATE,
    completada BOOLEAN DEFAULT FALSE,
    usuario_id BIGINT,
    espacio_id BIGINT
);