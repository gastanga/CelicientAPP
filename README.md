La CelicientApp

La CelicientApp es un sistema de e-commerce desarrollado en Java con Spring Boot. Permite gestionar usuarios, perfiles de comprador/vendedor, productos, anuncios, pedidos y lógica básica de pagos (sin pasarela integrada aún).
El backend está diseñado para ser seguro, escalable y fácilmente integrable con un front-end externo.

Características principales
✔ Gestión de usuarios

Registro y login básico.

Perfiles separados: usuario normal y vendedor.

Activación de perfil vendedor mediante endpoint seguro.

Representación segura con DTOs (sin datos sensibles).

✔ Gestión de vendedores

Creación automática del perfil de vendedor asociado al usuario.

Control de datos de tienda, rating, reseñas y alcance geográfico.

Endpoints públicos para consultar la información del vendedor sin exponer datos privados.

✔ Productos

CRUD básico de productos.

Asociación automática al vendedor que los publica.

Conversión a ProductoDTO evitando exponer campos sensibles (receta, costos, etc.).

Inclusión de VendedorPublicoDTO dentro del producto para mostrar datos públicos del vendedor.

✔ Seguridad / Buenas prácticas

Separación clara entre entidades internas y DTOs expuestos.

Validaciones básicas.

Manejo de excepciones estructurado (ControllerAdvice).

Servicios encargados de la lógica de negocio, sin mezclar responsabilidades en controladores.

✔ Arquitectura

Capas: Controller → Service → Repository → Entity

DTOs diseñados para cada caso de uso público.

Uso de Lombok para reducir boilerplate.

Repositorios JPA para consultas limpias.

Tecnologías

Java 21

Spring Boot (Web, Data JPA)

MySQL

Maven

Lombok

Postman para pruebas

Git / GitHub

Estado del proyecto

En desarrollo activo.
Próximas funcionalidades previstas:

Sistema de pedidos y carro de compra.

Calificaciones y reseñas.

Seguridad reforzada (JWT).

Perfil de comprador.

Integración de métodos de pago.

Cómo ejecutar

Clonar el repositorio.

Configurar application.properties con tus datos de MySQL.

Ejecutar la aplicación desde IntelliJ o por consola:

mvn spring-boot:run


Probar endpoints mediante Postman.