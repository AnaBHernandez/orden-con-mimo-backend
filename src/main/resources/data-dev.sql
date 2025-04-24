-- Datos para entorno de desarrollo (H2)
INSERT INTO usuarios (nombre, apellido, email, password) 
VALUES ('Usuario', 'Ejemplo', 'usuario@ejemplo.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a');

-- Insertar tareas para cada categoría MIMO
INSERT INTO tarea (nombre, descripcion, categoria, fecha_creacion, fecha_limite, completada, usuario_id, espacio_id) 
VALUES ('Meditar 10 minutos', 'Practicar mindfulness por la mañana', 'MIRATE', CURRENT_TIMESTAMP(), DATEADD('DAY', 7, CURRENT_DATE()), FALSE, 1, NULL);

INSERT INTO tarea (nombre, descripcion, categoria, fecha_creacion, fecha_limite, completada, usuario_id, espacio_id) 
VALUES ('Planificar vacaciones', 'Investigar destinos para el verano', 'IMAGINA', CURRENT_TIMESTAMP(), DATEADD('DAY', 30, CURRENT_DATE()), FALSE, 1, NULL);

INSERT INTO tarea (nombre, descripcion, categoria, fecha_creacion, fecha_limite, completada, usuario_id, espacio_id) 
VALUES ('Correr 5km', 'Completar recorrido en el parque', 'MUEVETE', CURRENT_TIMESTAMP(), DATEADD('DAY', 3, CURRENT_DATE()), FALSE, 1, NULL);

INSERT INTO tarea (nombre, descripcion, categoria, fecha_creacion, fecha_limite, completada, usuario_id, espacio_id) 
VALUES ('Organizar escritorio', 'Limpiar y ordenar área de trabajo', 'ORDENA', CURRENT_TIMESTAMP(), DATEADD('DAY', 1, CURRENT_DATE()), FALSE, 1, NULL);