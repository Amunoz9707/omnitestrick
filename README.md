# Rick & Morty - Clean Architecture, GraphQL & Compose Application

Aplicación desarrollada con Clean Architecture, para garantizar un código limpio, escalable y fácil de mantener, se realizo la implementación del patrón **Clean Architecture** y también **Jetpack Compose** para el desarrollo de la UI.

### ¿Por qué Clean Architecture?

La razón principal para elegir **Clean Architecture** es mantener una separación clara de responsabilidades y mejorar la escalabilidad a medida que el proyecto crece. Las ventajas clave son:

1. **Separación de capas**: Clean Architecture divide la aplicación en diferentes capas, como la capa de dominio, la capa de datos y la capa de presentación. Esto permite que cada parte del código tenga una responsabilidad bien definida y facilita los cambios en cualquier parte de la aplicación sin afectar a otras capas.

2. **Escalabilidad**: La separación de responsabilidades hace que el código sea fácil de extender sin impactar el comportamiento existente. Esto es crucial para proyectos que pueden evolucionar con el tiempo y agregar nuevas funcionalidades de manera ágil.

3. **Testabilidad**: Con esta arquitectura, es fácil aislar cada capa y probarlas individualmente mediante pruebas unitarias, lo que aumenta la confiabilidad del software.

4. **Mantenibilidad**: Clean Architecture facilita el mantenimiento del código. Como las dependencias están bien organizadas, cualquier actualización o corrección de errores es más fácil de implementar.

### Estructura del proyecto

- **domain**: Contiene los casos de uso y las reglas de negocio puras.
- **data**: Aquí se gestionan los repositorios y fuentes de datos, asegurando una separación de la lógica de la aplicación hacia el acceso a datos.
- **di**: Facilita la gestión de las dependencias (objetos que una clase necesita para funcionar). En lugar de que una clase cree directamente sus propias dependencias, estas se inyectan desde el exterior.
- **presentation**: Maneja toda la parte de la interfaz de usuario, donde **Jetpack Compose** juega un papel central, junto con los **ViewModels** para manejar el estado de la UI.
  
### Integración de GraphQL

La razón para utilizar **GraphQL** como el protocolo principal para la comunicación con el servidor, es debido a los siguientes beneficios:

1. **Optimización de las peticiones**: Con **GraphQL**, podemos solicitar exactamente los datos que necesitamos, reduciendo la sobrecarga de datos innecesarios y optimizando la eficiencia de las peticiones. Esto es especialmente útil en un entorno móvil, donde los recursos de red son limitados.

2. **Flexibilidad**: GraphQL nos permite diseñar las consultas de forma flexible, lo que facilita la evolución de la API sin afectar a los clientes. Podemos añadir o modificar campos en la API sin tener que modificar múltiples endpoints, como suele suceder con REST.

3. **Estructura del esquema**: El uso de un esquema tipado garantiza que tanto el cliente como el servidor tengan un entendimiento común de los datos que están siendo solicitados y proporcionados. Esto reduce errores de interpretación de la API.

4. **Facilidad para manejar relaciones**: GraphQL permite consultar relaciones complejas entre entidades en una sola petición, lo que simplifica la gestión de datos en aplicaciones, donde múltiples entidades están interrelacionadas.

## Tecnologías Usadas

- **Kotlin**
- **Jetpack Compose**
- **Hilt (DI)**
- **StateFlow**
- **Coroutines**
- **Clean Architecture**

## Ejecución del Proyecto

### Requisitos previos

Asegúrate de tener instaladas las siguientes herramientas antes de ejecutar el proyecto:

- **Android Studio** (versión recomendada: Koala)
- **Java Development Kit (JDK)** 11 o superior
- **Kotlin** (el proyecto está desarrollado en Kotlin)
- **Gradle** (Android Studio lo gestiona automáticamente)
- **Apollo Client** (para GraphQL)
- **Conexión a internet** (para interactuar con la API GraphQL)

### Clonación del repositorio

Primero, clona el repositorio del proyecto a tu máquina local usando Git:

```bash
git clone https://github.com/Amunoz9707/omnitestrick
