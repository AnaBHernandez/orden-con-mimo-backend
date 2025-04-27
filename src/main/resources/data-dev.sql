-- Insertar usuario por defecto
INSERT INTO usuario (id, nombre, email, password, fecha_creacion) 
VALUES (1, 'Mi Nombre', 'hola@ordenconmimo.com', 'password', CURRENT_TIMESTAMP);

-- Actualizar los espacios para seguir exactamente el método MIMO
INSERT INTO espacio (id, nombre, descripcion, icono, color, usuario_id, fecha_creacion) 
VALUES 
(1, 'Mírate', 'Espacio para tareas de autocuidado y reflexión', 'user', '#614385', 1, CURRENT_TIMESTAMP),
(2, 'Imagina', 'Espacio para tareas creativas y de planificación', 'lightbulb', '#F1C40F', 1, CURRENT_TIMESTAMP),
(3, 'Muévete', 'Espacio para tareas de actividad y acción', 'running', '#E67E22', 1, CURRENT_TIMESTAMP),
(4, 'Ordena', 'Espacio para tareas de organización y estructura', 'list-check', '#27AE60', 1, CURRENT_TIMESTAMP);

-- Mantener las tareas existentes pero reasignarlas a los espacios adecuados
-- Tareas para Mírate (espacio_id = 1)
INSERT INTO tarea (id, nombre, descripcion, completada, categoria, fecha_creacion, fecha_limite, espacio_id, usuario_id) 
VALUES 
(1, 'Reflexionar sobre mi desarrollo profesional', 'Dedicar 20 minutos a evaluar mi situación actual y definir áreas de mejora', false, 'MIRATE', CURRENT_TIMESTAMP, DATEADD('DAY', 2, CURRENT_TIMESTAMP), 1, 1),
(5, 'Preparar espacio tranquilo para leer', 'Acondicionar rincón de lectura con iluminación adecuada y cojines cómodos', false, 'MIRATE', CURRENT_TIMESTAMP, DATEADD('DAY', 5, CURRENT_TIMESTAMP), 1, 1),
(9, 'Sesión de meditación matutina', 'Dedicar 15 minutos a meditar enfocándome en la respiración', false, 'MIRATE', CURRENT_TIMESTAMP, DATEADD('DAY', 1, CURRENT_TIMESTAMP), 1, 1),
(13, 'Disfrutar de baño relajante', 'Prepararme un baño con sales y música tranquila', false, 'MIRATE', CURRENT_TIMESTAMP, DATEADD('DAY', 1, CURRENT_TIMESTAMP), 1, 1);

-- Tareas para Imagina (espacio_id = 2)
INSERT INTO tarea (id, nombre, descripcion, completada, categoria, fecha_creacion, fecha_limite, espacio_id, usuario_id) 
VALUES 
(2, 'Planificar presentación del proyecto', 'Diseñar estructura y puntos clave para la presentación del viernes', false, 'IMAGINA', CURRENT_TIMESTAMP, DATEADD('DAY', 3, CURRENT_TIMESTAMP), 2, 1),
(6, 'Diseñar menú semanal saludable', 'Planificar comidas equilibradas para toda la semana', false, 'IMAGINA', CURRENT_TIMESTAMP, DATEADD('DAY', 1, CURRENT_TIMESTAMP), 2, 1),
(10, 'Visualizar metas a 5 años', 'Crear mapa mental con objetivos personales y profesionales', false, 'IMAGINA', CURRENT_TIMESTAMP, DATEADD('DAY', 7, CURRENT_TIMESTAMP), 2, 1),
(14, 'Planificar escapada de fin de semana', 'Buscar opciones para escapada rural a la sierra', false, 'IMAGINA', CURRENT_TIMESTAMP, DATEADD('DAY', 10, CURRENT_TIMESTAMP), 2, 1);

-- Tareas para Muévete (espacio_id = 3)
INSERT INTO tarea (id, nombre, descripcion, completada, categoria, fecha_creacion, fecha_limite, espacio_id, usuario_id) 
VALUES 
(3, 'Contactar a cliente potencial', 'Llamar a Juan García para agendar reunión introductoria', false, 'MUEVETE', CURRENT_TIMESTAMP, DATEADD('DAY', 1, CURRENT_TIMESTAMP), 3, 1),
(7, 'Reparar estantería del salón', 'Fijar el estante que está suelto y reorganizar libros', false, 'MUEVETE', CURRENT_TIMESTAMP, DATEADD('DAY', 3, CURRENT_TIMESTAMP), 3, 1),
(11, 'Inscribirme en curso de fotografía', 'Formalizar inscripción en el taller de fotografía digital', false, 'MUEVETE', CURRENT_TIMESTAMP, DATEADD('DAY', 4, CURRENT_TIMESTAMP), 3, 1),
(15, 'Salir a correr al parque', 'Sesión de running de 30 minutos por el parque del oeste', false, 'MUEVETE', CURRENT_TIMESTAMP, DATEADD('DAY', 1, CURRENT_TIMESTAMP), 3, 1);

-- Tareas para Ordena (espacio_id = 4)
INSERT INTO tarea (id, nombre, descripcion, completada, categoria, fecha_creacion, fecha_limite, espacio_id, usuario_id) 
VALUES 
(4, 'Organizar documentos de proyecto', 'Clasificar y archivar documentación pendiente del proyecto Reino', false, 'ORDENA', CURRENT_TIMESTAMP, DATEADD('DAY', 4, CURRENT_TIMESTAMP), 4, 1),
(8, 'Organizar despensa y nevera', 'Revisar alimentos, desechar caducados y reorganizar por categorías', false, 'ORDENA', CURRENT_TIMESTAMP, DATEADD('DAY', 2, CURRENT_TIMESTAMP), 4, 1),
(12, 'Actualizar lista de libros pendientes', 'Revisar y priorizar los próximos 5 libros a leer', true, 'ORDENA', CURRENT_TIMESTAMP, DATEADD('DAY', 2, CURRENT_TIMESTAMP), 4, 1),
(16, 'Organizar colección de música', 'Crear playlists temáticas y organizar biblioteca digital', false, 'ORDENA', CURRENT_TIMESTAMP, DATEADD('DAY', 5, CURRENT_TIMESTAMP), 4, 1);