# Arquitectura del sistema

## Estructura del proyecto
El proyecto sigue una organización por funcionalidad:

- **user**: Componentes relacionados con el usuario
- **security**: Configuración y componentes de seguridad
- **tarea**: Entidades y lógica para gestión de tareas
- **espacio**: Entidades y lógica para gestión de espacios
- **common**: Componentes comunes y utilidades

## Capas de la aplicación

1. **Capa de presentación**
   - Controladores REST para API
   - Controladores de vista para el frontend con Thymeleaf
   - Recursos estáticos (CSS, JS)

2. **Capa de servicio**
   - Implementación de la lógica de negocio
   - Servicios para tareas y espacios
   - Servicios de seguridad

3. **Capa de persistencia**
   - Repositorios para acceso a datos
   - Entidades JPA
   - Configuración de base de datos

## Modelos de datos

### Entidades principales:

- **Tarea**: Representa una actividad con categoría MIMO
  - id: Long (identificador único)
  - nombre: String (nombre de la tarea)
  - descripcion: String (descripción detallada)
  - fechaCreacion: LocalDateTime
  - fechaLimite: LocalDateTime (opcional)
  - completada: boolean
  - categoria: CategoriaMIMO (enum)
  - espacio: Espacio (relación)

- **Espacio**: Representa un lugar físico para organizar
  - id: Long
  - nombre: String
  - descripcion: String
  - tareas: List<Tarea> (relación)

- **Usuario**: Información del usuario del sistema
  - id: Long
  - username: String
  - password: String (encriptada)
  - email: String
  - roles: Set<Role>

## Patrones de diseño utilizados

- **MVC**: Para la estructura general de la aplicación
- **Repository**: Para el acceso a datos
- **Service**: Para la lógica de negocio
- **DTO**: Para la transferencia de datos entre capas