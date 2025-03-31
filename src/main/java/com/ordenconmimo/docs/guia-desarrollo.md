# Guía de desarrollo

## Entorno de desarrollo
- Java 11 o superior
- Maven 3.6 o superior
- IDE recomendado: IntelliJ IDEA o STS
- Git para control de versiones

## Configuración inicial
1. Clonar el repositorio
2. Importar como proyecto Maven
3. Verificar configuración de application.properties
4. Ejecutar OrdenConMimoApplication

## Estructura de paquetes
```java
src/main/java/com/tudominio/ordenconmimo/
├── OrdenConMimoApplication.java
├── common/
│   ├── exception/
│   └── util/
├── user/
│   ├── controller/
│   ├── model/
│   └── service/
├── security/
│   ├── config/
│   └── service/
├── tarea/
│   ├── controller/
│   ├── model/
│   └── service/
└── espacio/
    ├── controller/
    ├── model/
    └── service/

Convenciones de código
-Seguir principios SOLID
-Nombres de clases en PascalCase
-Nombres de métodos y variables en camelCase
-Documentar métodos públicos con JavaDoc

Flujo de trabajo Git
1.Crear una rama para cada nueva funcionalidad
2.Hacer commits pequeños y frecuentes
3..rear Pull Request para integrar cambios

Tests
-Utilizar JUnit 5 para tests unitarios
-Mockito para mocks
-Alcanzar al menos 70% de cobertura


5. **En "Edit message"**, escribe "Crear página Guía de desarrollo"
6. **Haz clic en "Save page"**

## 7. Crear página "Gestión del proyecto"

1. **Vuelve a Home**
2. **Haz clic en el enlace "[Gestión del proyecto](./Gestion-del-proyecto)"**
3. **Haz clic en "Create page"**
4. **En el contenido**, copia este texto:

```markdown
# Gestión del Proyecto Orden con MIMO

## Herramientas de gestión utilizadas

Para la gestión de este proyecto he utilizado herramientas profesionales que permiten una organización eficiente del trabajo y la documentación.

### Jira Software

He utilizado Jira para la planificación y seguimiento de tareas, organizando el trabajo en épicas, historias de usuario y sprints.

#### Estructura del proyecto en Jira

El trabajo se ha organizado en épicas que representan los grandes bloques funcionales:
- Configuración del proyecto
- Desarrollo de entidades
- Implementación de servicios
- Desarrollo de API
- Interfaz de usuario
- Seguridad

Cada épica contiene historias de usuario que representan funcionalidades específicas que aportan valor.

#### Sprints

El trabajo se ha dividido en tres sprints:
1. Sprint 1: Configuración y entidades
2. Sprint 2: Servicios y API
3. Sprint 3: Interfaz y Seguridad

[Aquí puedes incluir capturas de pantalla de Jira]

## Planificación de desarrollo

El desarrollo se ha planificado con entregas incrementales, siguiendo estos hitos:

1. **Configuración inicial y modelos de datos**
   - Configuración del proyecto Spring Boot
   - Implementación de entidades y enum
   - Configuración de seguridad básica

2. **Lógica de negocio y API**
   - Implementación de servicios
   - Desarrollo de endpoints REST
   - Pruebas de integración

3. **Interfaz de usuario y finalización**
   - Implementación de controladores de vista
   - Desarrollo de templates Thymeleaf
   - Integración completa y pruebas de sistema

## Ventajas de este enfoque

Aunque este es un proyecto individual, el uso de herramientas profesionales de gestión permite:
1. Mejor organización del trabajo
2. Claridad en la planificación
3. Documentación estructurada y accesible
4. Preparación para posibles ampliaciones del equipo en el futuro