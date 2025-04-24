-- Datos para entorno de producción (MySQL)
INSERT INTO usuarios (nombre, apellido, email, password) 
SELECT 'Usuario', 'Ejemplo', 'usuario@ejemplo.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a'
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'usuario@ejemplo.com');

-- Insertar tareas para cada categoría MIMO (solo si no existen)
INSERT INTO tarea (nombre, descripcion, categoria, fecha_creacion, fecha_limite, completada, usuario_id, espacio_id)
SELECT 'Meditar 10 minutos', 'Practicar mindfulness por la mañana', 'MIRATE', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY), FALSE, 1, NULL
WHERE NOT EXISTS (SELECT 1 FROM tarea WHERE nombre = 'Meditar 10 minutos' AND categoria = 'MIRATE');

INSERT INTO tarea (nombre, descripcion, categoria, fecha_creacion, fecha_limite, completada, usuario_id, espacio_id)
SELECT 'Planificar vacaciones', 'Investigar destinos para el verano', 'IMAGINA', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_DATE, INTERVAL 30 DAY), FALSE, 1, NULL
WHERE NOT EXISTS (SELECT 1 FROM tarea WHERE nombre = 'Planificar vacaciones' AND categoria = 'IMAGINA');

INSERT INTO tarea (nombre, descripcion, categoria, fecha_creacion, fecha_limite, completada, usuario_id, espacio_id)
SELECT 'Correr 5km', 'Completar recorrido en el parque', 'MUEVETE', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_DATE, INTERVAL 3 DAY), FALSE, 1, NULL
WHERE NOT EXISTS (SELECT 1 FROM tarea WHERE nombre = 'Correr 5km' AND categoria = 'MUEVETE');

INSERT INTO tarea (nombre, descripcion, categoria, fecha_creacion, fecha_limite, completada, usuario_id, espacio_id)
SELECT 'Organizar escritorio', 'Limpiar y ordenar área de trabajo', 'ORDENA', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY), FALSE, 1, NULL
WHERE NOT EXISTS (SELECT 1 FROM tarea WHERE nombre = 'Organizar escritorio' AND categoria = 'ORDENA');