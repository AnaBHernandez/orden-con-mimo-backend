-- Datos iniciales para el proyecto Orden con MIMO (H2)

-- Insertar usuario de ejemplo
INSERT INTO usuarios (nombre, apellido, email, password) 
VALUES ('Usuario', 'Ejemplo', 'usuario@ejemplo.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a');

-- Insertar tareas de ejemplo para cada categoría MIMO
-- MÍRATE
INSERT INTO tareas (titulo, descripcion, categoria_mimo, fecha_limite, usuario_id) 
VALUES ('Meditar 10 minutos', 'Práctica diaria de mindfulness para reducir estrés', 'MIRATE', DATEADD('DAY', 1, CURRENT_DATE), 1);

INSERT INTO tareas (titulo, descripcion, categoria_mimo, fecha_limite, usuario_id) 
VALUES ('Revisar objetivos personales', 'Actualizar mi lista de objetivos y reflexionar sobre mi progreso', 'MIRATE', DATEADD('DAY', 7, CURRENT_DATE), 1);

-- IMAGINA
INSERT INTO tareas (titulo, descripcion, categoria_mimo, fecha_limite, usuario_id) 
VALUES ('Visualizar proyecto creativo', 'Crear un moodboard para mi próximo proyecto', 'IMAGINA', DATEADD('DAY', 3, CURRENT_DATE), 1);

INSERT INTO tareas (titulo, descripcion, categoria_mimo, fecha_limite, usuario_id) 
VALUES ('Planificar viaje de vacaciones', 'Investigar destinos y crear itinerario preliminar', 'IMAGINA', DATEADD('DAY', 14, CURRENT_DATE), 1);

-- MUÉVETE
INSERT INTO tareas (titulo, descripcion, categoria_mimo, fecha_limite, usuario_id) 
VALUES ('Ejercicio cardiovascular', '30 minutos de entrenamiento para mejorar resistencia', 'MUEVETE', DATEADD('DAY', 2, CURRENT_DATE), 1);

INSERT INTO tareas (titulo, descripcion, categoria_mimo, fecha_limite, usuario_id) 
VALUES ('Reorganizar espacio de trabajo', 'Mejorar la ergonomía y funcionalidad del escritorio', 'MUEVETE', DATEADD('DAY', 5, CURRENT_DATE), 1);

-- ORDENA
INSERT INTO tareas (titulo, descripcion, categoria_mimo, fecha_limite, usuario_id) 
VALUES ('Organizar calendario semanal', 'Planificar actividades y compromisos de la próxima semana', 'ORDENA', DATEADD('DAY', 1, CURRENT_DATE), 1);

INSERT INTO tareas (titulo, descripcion, categoria_mimo, fecha_limite, usuario_id) 
VALUES ('Revisar presupuesto mensual', 'Actualizar hoja de cálculo con gastos e ingresos', 'ORDENA', DATEADD('DAY', 4, CURRENT_DATE), 1);