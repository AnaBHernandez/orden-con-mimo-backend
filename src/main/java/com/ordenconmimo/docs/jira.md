# Gestión del proyecto con Jira

## Organización general

Para este proyecto, he utilizado Jira para la gestión y seguimiento del desarrollo. La estructura se ha organizado de la siguiente manera:

- **Épicas**: Grandes bloques funcionales del proyecto
- **Historias de usuario**: Funcionalidades específicas dentro de cada épica
- **Sprints**: Períodos de trabajo de una semana con objetivos definidos

## Épicas creadas

He organizado el trabajo en las siguientes épicas:

1. **Configuración del proyecto**
   - Preparación del entorno y estructura base

2. **Desarrollo de entidades**
   - Modelado de las clases principales según el método MIMO

3. **Implementación de servicios**
   - Lógica de negocio para la gestión de tareas y espacios

4. **Desarrollo de API**
   - Endpoints REST para la comunicación con el frontend

5. **Interfaz de usuario**
   - Implementación de vistas y controladores para el usuario

6. **Seguridad**
   - Autenticación y autorización de usuarios

## Historias de usuario

Cada épica contiene las siguientes historias de usuario:

### Épica: Configuración del proyecto
- Configuración inicial del repositorio (1 punto)
- Configuración de Spring Boot (2 puntos)
- Estructura de paquetes por funcionalidad (2 puntos)

### Épica: Desarrollo de entidades
- Creación del enum CategoriaMIMO (1 punto)
- Implementación de entidad Tarea (3 puntos)
- Implementación de entidad Espacio (3 puntos)

### Épica: Implementación de servicios
- Implementación de TareaService (3 puntos)
- Implementación de EspacioService (3 puntos)

### Épica: Desarrollo de API
- Implementación de TareaRestController (2 puntos)
- Implementación de EspacioRestController (2 puntos)

### Épica: Interfaz de usuario
- Implementación de TareaViewController (3 puntos)
- Implementación de EspacioViewController (3 puntos)
- Creación de templates Thymeleaf (5 puntos)

### Épica: Seguridad
- Configuración de Spring Security (3 puntos)
- Implementación de login/logout (3 puntos)

## Planificación de sprints

### Sprint 1: Configuración y entidades (1-7 abril)

**Día 1 (1 de abril)**
- Configuración inicial del repositorio
- Creación de estructura básica de paquetes

**Día 2 (2 de abril)**
- Configuración de Spring Boot
- Conexión con H2 Database

**Día 3 (3 de abril)**
- Creación del enum CategoriaMIMO
- Boceto inicial de entidades

**Día 4 (4 de abril)**
- Implementación completa de entidad Tarea
- Pruebas unitarias de entidad Tarea

**Día 5 (5 de abril)**
- Implementación completa de entidad Espacio
- Pruebas unitarias de entidad Espacio

**Día 6 (6 de abril)**
- Implementación de repositorios
- Pruebas de integración de repositorios

**Día 7 (7 de abril)**
- Revisión y refactorización
- Documentación de entidades

### Sprint 2: Servicios y API (8-14 abril)

**Día 8 (8 de abril)**
- Diseño de interfaces de servicio
- Implementación básica de TareaService

**Día 9 (9 de abril)**
- Implementación completa de TareaService
- Pruebas unitarias de TareaService

**Día 10 (10 de abril)**
- Implementación de EspacioService
- Pruebas unitarias de EspacioService

**Día 11 (11 de abril)**
- Diseño de API REST
- Implementación básica de controladores REST

**Día 12 (12 de abril)**
- Implementación de TareaRestController
- Pruebas unitarias de TareaRestController

**Día 13 (13 de abril)**
- Implementación de EspacioRestController
- Pruebas unitarias de EspacioRestController

**Día 14 (14 de abril)**
- Revisión y refactorización
- Documentación de API

### Sprint 3: Interfaz y Seguridad (15-27 abril)

**Día 15 (15 de abril)**
- Configuración de Thymeleaf
- Diseño de estructura de páginas

**Día 16 (16 de abril)**
- Implementación de TareaViewController
- Creación de templates para tareas

**Día 17 (17 de abril)**
- Implementación de EspacioViewController
- Creación de templates para espacios

**Día 18 (18 de abril)**
- Implementación de página principal
- Diseño de navegación

**Día 19 (19 de abril)**
- Configuración básica de Spring Security
- Creación de entidades de usuario y rol

**Día 20 (20 de abril)**
- Implementación de login/logout
- Pruebas de seguridad

**Día 21 (21 de abril)**
- Integración de UI con seguridad
- Pruebas de navegación

**Día 22 (22 de abril)**
- Mejoras de estilo y experiencia de usuario
- Validación de formularios

**Día 23 (23 de abril)**
- Implementación de búsqueda y filtrado
- Mejoras de rendimiento

**Día 24 (24 de abril)**
- Pruebas de integración completas
- Corrección de errores

**Día 25 (25 de abril)**
- Revisión final de código
- Mejora de cobertura de tests

**Día 26 (26 de abril)**
- Documentación final
- Preparación de presentación

**Día 27 (27 de abril)**
- Revisión final y últimos ajustes
- Verificación de requisitos

## Capturas de pantalla

### Backlog con épicas y sprints

![Backlog de Jira](./imagenes/jira-backlog.png)
*Figura 1: Backlog completo mostrando las épicas y los sprints planificados.*

### Sprint activo

![Sprint activo](./imagenes/jira-sprint.png)
*Figura 2: Sprint 1 activo con historias de usuario asignadas y en progreso.*

### Tablero Kanban

![Tablero Kanban](./imagenes/jira-tablero.png)
*Figura 3: Tablero Kanban mostrando el estado actual de las tareas.*

## Ventajas observadas

El uso de Jira para este proyecto ha proporcionado varias ventajas:

1. **Visibilidad clara del progreso**: A través del tablero Kanban, puedo ver fácilmente qué tareas están pendientes, en progreso o completadas.

2. **Planificación estructurada**: La división en épicas y sprints me ayuda a mantener el enfoque en objetivos concretos.

3. **Estimación de esfuerzo**: La asignación de puntos de historia me permite distribuir el trabajo de manera equilibrada.

4. **Seguimiento del tiempo**: Puedo ver si estoy avanzando según lo planificado o si necesito ajustar el alcance.

5. **Documentación del proceso**: El historial de Jira proporciona una documentación clara del proceso de desarrollo que será útil para la evaluación.