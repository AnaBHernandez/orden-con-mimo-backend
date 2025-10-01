
# 🏰 Orden con MIMO - Backend

## 📋 Descripción
Backend de la aplicación "Orden con MIMO", una plataforma de organización personal basada en el método MIMO (Mírate, Imagina, Muévete, Ordena) con temática de reino fantástico.

Este proyecto implementa la API REST del backend, utilizando Spring Boot con JPA para la persistencia de datos y Spring Security para la autenticación.

## 🎨 Tema Reino MIMO
La aplicación sigue una temática de "Reino Fantástico" donde cada categoría MIMO tiene su propio color distintivo:

- **Mírate** 🔮: Púrpura (#614385)
- **Imagina** ✨: Dorado (#F1C40F)
- **Muévete** 🔥: Naranja (#E67E22)
- **Ordena** 🌿: Verde (#27AE60)

El esquema de colores principal de la aplicación es:
- Primary: #98D8C8 (Verde menta)
- Accent: #F5E6A8 (Amarillo suave)
- Secondary: #89CFF0 (Azul bebé)

## 🛠️ Tecnologías
- **Spring Boot 3.4.5**: Framework base para la aplicación
- **Java 21**: Lenguaje de programación
- **Spring Data JPA**: Para la persistencia de datos
- **Spring Security**: Para autenticación y autorización
- **H2 Database**: Base de datos en memoria para desarrollo
- **Maven**: Gestión de dependencias y construcción
- **JaCoCo**: Para medición de cobertura de pruebas

## 🚀 Instalación y ejecución

### Requisitos previos
- Java 21 o superior
- Maven 3.9+

### Pasos
1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/AnaBHernandez/orden-con-mimo-backend.git
   cd orden-con-mimo-backend
   ```

2. **Compilar el proyecto:**
   ```bash
   mvn clean compile
   ```

3. **Ejecutar la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

4. **Acceder a la aplicación:**
   - **API REST**: http://localhost:8082/api
   - **H2 Console**: http://localhost:8082/h2-console

### 🔧 Configuración de Puertos
- **Backend API**: Puerto 8082
- **H2 Console**: http://localhost:8082/h2-console
- **Frontend**: Puerto 8083 (proyecto separado)

> **Nota**: Asegúrate de que el frontend esté ejecutándose en http://localhost:8083 para que la comunicación con la API funcione correctamente.

## 📂 Estructura del proyecto
La estructura del proyecto sigue una organización por módulos funcionales:

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── ordenconmimo/
│   │           ├── config/              # Configuraciones (CORS, seguridad)
│   │           ├── espacio/             # Módulo de gestión de espacios
│   │           │   ├── controladores/
│   │           │   ├── modelos/
│   │           │   ├── repositorios/
│   │           │   └── servicios/
│   │           ├── usuario/             # Módulo de gestión de tareas y usuarios
│   │           │   ├── controladores/
│   │           │   ├── modelos/
│   │           │   ├── repositorios/
│   │           │   └── servicios/
│   │           └── seguridad/          # Configuración de seguridad
│   └── resources/
│       ├── application.properties       # Configuración general
│       ├── application-dev.properties  # Configuración de desarrollo
│       ├── application-prod.properties # Configuración de producción
│       ├── schema.sql                   # Esquema de base de datos
│       └── data-dev.sql                 # Datos iniciales
```

## 🌐 API REST

### 🗂️ Tareas
- **GET** `/api/tareas` - Listar todas las tareas
- **GET** `/api/tareas/{id}` - Obtener una tarea por ID
- **GET** `/api/tareas/categoria/{categoria}` - Filtrar tareas por categoría MIMO
- **POST** `/api/tareas` - Crear una nueva tarea
- **PUT** `/api/tareas/{id}` - Actualizar una tarea existente
- **DELETE** `/api/tareas/{id}` - Eliminar una tarea

### 🏠 Espacios
- **GET** `/api/espacios` - Listar todos los espacios
- **GET** `/api/espacios/{id}` - Obtener un espacio por ID
- **POST** `/api/espacios` - Crear un nuevo espacio
- **PUT** `/api/espacios/{id}` - Actualizar un espacio existente
- **DELETE** `/api/espacios/{id}` - Eliminar un espacio
- **GET** `/api/espacios/{id}/tareas` - Listar tareas de un espacio

## 🗄️ Base de Datos H2 (Desarrollo)
Para acceder a la consola de la base de datos:
1. Ve a: `http://localhost:8082/h2-console/`
2. Configuración:
   - **JDBC URL**: `jdbc:h2:mem:testdb`
   - **User Name**: `sa`
   - **Password**: `password`
3. Click en "Connect"

**Tablas disponibles:**
- `ESPACIO` - Gestión de espacios
- `TAREA` - Tareas con categorías MIMO
- `USUARIOS` - Usuarios del sistema

## 🧪 Testing
```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con cobertura
mvn clean test jacoco:report
```

## 📚 Documentación
Para más información sobre el proyecto, consulta la [Wiki oficial del repositorio](https://github.com/AnaBHernandez/orden-con-mimo-backend/wiki).

## 🤝 Contribución
1. Haz fork del proyecto
2. Crea una rama para tu funcionalidad (`git checkout -b feature/amazing-feature`)
3. Haz commit de tus cambios (`git commit -m 'Add some amazing feature'`)
4. Push a la rama (`git push origin feature/amazing-feature`)
5. Abre un Pull Request

## 📜 Licencia
Este proyecto está licenciado bajo MIT License.

## 🎯 Características Principales
- ✅ **API REST completa** con endpoints para tareas y espacios
- ✅ **Método MIMO implementado** con categorías de colores
- ✅ **Base de datos H2** para desarrollo rápido
- ✅ **Spring Security** para autenticación
- ✅ **Testing completo** con cobertura de código
- ✅ **Documentación detallada** en Wiki
- ✅ **Arquitectura modular** por funcionalidades

## 🐛 Solución de Problemas
Si encuentras problemas:

1. **Verifica que Java 21 esté instalado**: `java -version`
2. **Verifica que Maven esté instalado**: `mvn -version`
3. **Revisa los logs** de la aplicación para errores específicos
4. **Asegúrate de que el puerto 8082 esté libre**
5. **Verifica la configuración de H2** en `application-dev.properties`

## 📞 Soporte
Para soporte técnico o preguntas sobre el proyecto, consulta la Wiki del repositorio o abre un issue en GitHub.