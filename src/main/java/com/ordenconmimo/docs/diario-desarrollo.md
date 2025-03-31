# Diario de Desarrollo

Este documento registra el progreso diario, los problemas encontrados y las soluciones aplicadas durante el desarrollo del proyecto.

## 31 de marzo de 2025

### Tareas completadas
1. Creación de la estructura de carpetas del proyecto siguiendo una organización por funcionalidad
2. Adición de archivos `.gitkeep` para mantener la estructura de directorios vacíos en Git
3. Configuración de Jira para la gestión del proyecto con épicas y sprints
4. Creación de la estructura de documentación en la carpeta `docs/`

### Problemas y soluciones

#### Problema 1: Git no reconoce carpetas vacías
**Descripción:** Git no incluye carpetas vacías en el control de versiones, lo que dificultaba mantener la estructura de directorios.

**Solución:** Se añadieron archivos `.gitkeep` (un archivo vacío con este nombre por convención) en cada directorio vacío para que Git los reconociera y mantuviera.

```bash
# Comando utilizado para crear archivos .gitkeep en directorios vacíos
touch src/main/java/com/tudominio/ordenconmimo/common/.gitkeep
touch src/main/java/com/tudominio/ordenconmimo/user/.gitkeep
touch src/main/java/com/tudominio/ordenconmimo/security/.gitkeep
touch src/main/java/com/tudominio/ordenconmimo/tarea/.gitkeep
touch src/main/java/com/tudominio/ordenconmimo/espacio/.gitkeep

#### Problema 2: Organización en Jira
**Descripción:** Estructurar correctamente las épicas y historias de usuario en Jira resultaba confuso inicialmente.

**Solución:** Se optó por una organización basada en la arquitectura del sistema, creando épicas para cada componente principal (Configuración, Entidades, Servicios, API, UI, Seguridad) y distribuyendo las historias en tres sprints.

#### Problema 3: Documentación del proyecto
**Descripción:** No se podía acceder a Confluence para documentación debido a limitaciones de la versión gratuita.

**Solución:** Se implementó una estructura de documentación basada en archivos Markdown dentro de una carpeta `docs/` en el repositorio, siguiendo buenas prácticas de proyectos profesionales.