CREATE TABLE topicos (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    nombre_curso VARCHAR(100) NOT NULL,
    id_usuario BIGINT NOT NULL,

    CONSTRAINT fk_topicos_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);