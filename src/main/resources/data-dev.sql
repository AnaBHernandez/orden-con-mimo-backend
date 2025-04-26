-- Insertar usuario por defecto
INSERT INTO usuario (id, nombre, email, password, fecha_creacion) 
VALUES (1, 'Usuario Demo', 'demo@ordenconmimo.com', '$2a$10$ksX1daXNRPfzyZ/BgEW/5ONHRiHZ93cJdV44uTmMCu7q0ETnXL9NG', CURRENT_TIMESTAMP);

-- Crear espacios iniciales
INSERT INTO espacio (id, nombre, descripcion, icono, color, usuario_id, fecha_creacion) 
VALUES 
(1, 'Trabajo', 'Tareas relacionadas con mi vida profesional', 'briefcase', '#4A69BD', 1, CURRENT_TIMESTAMP),
(2, 'Hogar', 'Organización de mi casa y familia', 'home', '#E67E22', 1, CURRENT_TIMESTAMP),
(3, 'Desarrollo Personal', 'Crecimiento y bienestar personal', 'user', '#6A89CC', 1, CURRENT_TIMESTAMP),
(4, 'Ocio', 'Tiempo libre y diversión', 'smile', '#78E08F', 1, CURRENT_TIMESTAMP);

-- Insertar tareas para Espacio: Trabajo
INSERT INTO tarea (id, nombre, descripcion, completada, categoria, fecha_creacion, fecha_limite, espacio_id, usuario_id) 
VALUES 
-- Mírate (Púrpura)
(1, 'Reflexionar sobre mi desarrollo profesional', 'Dedicar 20 minutos a evaluar mi situación laboral actual y definir áreas de mejora', false, 'MIRATE', CURRENT_TIMESTAMP, DATEADD('DAY', 2, CURRENT_TIMESTAMP), 1, 1),
-- Imagina (Dorado)
(2, 'Planificar presentación del proyecto', 'Diseñar estructura y puntos clave para la presentación del viernes', false, 'IMAGINA', CURRENT_TIMESTAMP, DATEADD('DAY', 3, CURRENT_TIMESTAMP), 1, 1),
-- Muévete (Naranja)
(3, 'Contactar a cliente potencial', 'Llamar a Juan García para agendar reunión introductoria', false, 'MUEVETE', CURRENT_TIMESTAMP, DATEADD('DAY', 1, CURRENT_TIMESTAMP), 1, 1),
-- Ordena (Verde)
(4, 'Organizar documentos de proyecto', 'Clasificar y archivar documentación pendiente del proyecto Reino', false, 'ORDENA', CURRENT_TIMESTAMP, DATEADD('DAY', 4, CURRENT_TIMESTAMP), 1, 1);

-- Insertar tareas para Espacio: Hogar
INSERT INTO tarea (id, nombre, descripcion, completada, categoria, fecha_creacion, fecha_limite, espacio_id, usuario_id) 
VALUES 
-- Mírate (Púrpura)
(5, 'Preparar espacio tranquilo para leer', 'Acondicionar rincón de lectura con iluminación adecuada y cojines cómodos', false, 'MIRATE', CURRENT_TIMESTAMP, DATEADD('DAY', 5, CURRENT_TIMESTAMP), 2, 1),
-- Imagina (Dorado)
(6, 'Diseñar menú semanal saludable', 'Planificar comidas equilibradas para toda la semana', false, 'IMAGINA', CURRENT_TIMESTAMP, DATEADD('DAY', 1, CURRENT_TIMESTAMP), 2, 1),
-- Muévete (Naranja)
(7, 'Reparar estantería del salón', 'Fijar el estante que está suelto y reorganizar libros', false, 'MUEVETE', CURRENT_TIMESTAMP, DATEADD('DAY', 3, CURRENT_TIMESTAMP), 2, 1),
-- Ordena (Verde)
(8, 'Organizar despensa y nevera', 'Revisar alimentos, desechar caducados y reorganizar por categorías', false, 'ORDENA', CURRENT_TIMESTAMP, DATEADD('DAY', 2, CURRENT_TIMESTAMP), 2, 1);

-- Insertar tareas para Espacio: Desarrollo Personal
INSERT INTO tarea (id, nombre, descripcion, completada, categoria, fecha_creacion, fecha_limite, espacio_id, usuario_id) 
VALUES 
-- Mírate (Púrpura)
(9, 'Sesión de meditación matutina', 'Dedicar 15 minutos a meditar enfocándome en la respiración', false, 'MIRATE', CURRENT_TIMESTAMP, DATEADD('DAY', 1, CURRENT_TIMESTAMP), 3, 1),
-- Imagina (Dorado)
(10, 'Visualizar metas a 5 años', 'Crear mapa mental con objetivos personales y profesionales', false, 'IMAGINA', CURRENT_TIMESTAMP, DATEADD('DAY', 7, CURRENT_TIMESTAMP), 3, 1),
-- Muévete (Naranja)
(11, 'Inscribirme en curso de fotografía', 'Formalizar inscripción en el taller de fotografía digital', false, 'MUEVETE', CURRENT_TIMESTAMP, DATEADD('DAY', 4, CURRENT_TIMESTAMP), 3, 1),
-- Ordena (Verde)
(12, 'Actualizar lista de libros pendientes', 'Revisar y priorizar los próximos 5 libros a leer', true, 'ORDENA', CURRENT_TIMESTAMP, DATEADD('DAY', 2, CURRENT_TIMESTAMP), 3, 1);

-- Insertar tareas para Espacio: Ocio
INSERT INTO tarea (id, nombre, descripcion, completada, categoria, fecha_creacion, fecha_limite, espacio_id, usuario_id) 
VALUES 
-- Mírate (Púrpura)
(13, 'Disfrutar de baño relajante', 'Prepararme un baño con sales y música tranquila', false, 'MIRATE', CURRENT_TIMESTAMP, DATEADD('DAY', 1, CURRENT_TIMESTAMP), 4, 1),
-- Imagina (Dorado)
(14, 'Planificar escapada de fin de semana', 'Buscar opciones para escapada rural a la sierra', false, 'IMAGINA', CURRENT_TIMESTAMP, DATEADD('DAY', 10, CURRENT_TIMESTAMP), 4, 1),
-- Muévete (Naranja)
(15, 'Salir a correr al parque', 'Sesión de running de 30 minutos por el parque del oeste', false, 'MUEVETE', CURRENT_TIMESTAMP, DATEADD('DAY', 1, CURRENT_TIMESTAMP), 4, 1),
-- Ordena (Verde)
(16, 'Organizar colección de música', 'Crear playlists temáticas y organizar biblioteca digital', false, 'ORDENA', CURRENT_TIMESTAMP, DATEADD('DAY', 5, CURRENT_TIMESTAMP), 4, 1);