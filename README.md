📚 Sistema de Gestión de Biblioteca (Backend)
  Este proyecto es el motor que controla toda la operación de la biblioteca: desde quiénes son los socios hasta qué libros entran al inventario y quién los tiene prestados. Está diseñado para ser rápido, confiable y fácil de conectar con cualquier aplicación.

⚙️ Antes de empezar
  *Para que el sistema funcione en tu computadora, solo asegúrate de esto:

  *Java: Tener instalada la versión 25.

  *Base de Datos: Usamos MySQL con el nombre biblioteca_universidad.

  *Puerto de conexión: El sistema usa el 8081. Esto es importante para que no choque con otros programas que tengas abiertos.

Tip de configuración: En el archivo application.properties, asegúrate de que tu usuario y contraseña de MySQL sean los correctos para que el sistema pueda "hablar" con la base de datos.

📂 ¿Cómo está organizado el código?
Para que el proyecto sea profesional y fácil de entender, lo dividimos en cuatro capas principales:

  *Modelos (Model): Aquí definimos la "forma" de los datos (Usuarios, Libros, Préstamos) para que Java y la base de datos se entiendan perfectamente.

  *Repositorios (Repository): Son los encargados de realizar las consultas a la base de datos (guardar, buscar, borrar).

  *Servicios (Service): Es donde vive la lógica del negocio. Aquí es donde el sistema toma decisiones, como verificar si un libro tiene stock disponible antes de autorizar un préstamo.

  *Controladores (Controller): Son las "puertas de entrada" del sistema. Reciben tus peticiones desde Postman y devuelven la respuesta correspondiente (como el mensaje 200 OK).

🚀 Guía de uso (¿Cómo probar el sistema?)
Para probar que todo funciona, usaremos Postman. Aquí tienes los pasos básicos:

***1. Registrar un nuevo socio (Usuario)
Lo primero es dar de alta a las personas.

-> Ruta: POST http://localhost:8081/api/usuarios

¿Qué enviar? Copia este ejemplo en el cuerpo (Body) de Postman en formato JSON:

JSON
{
    "nombre": "Javier Pedraza",
    "correoElectronico": "javier@correo.com",
    "passwordHash": "clave123",
    "rol": { "idRol": 1 } 
}
Nota: El sistema te responderá con un ID (por ejemplo, el 52). Guárdalo, lo necesitarás para el préstamo.

***2. Registrar un libro en el inventario
Para poder prestar algo, primero hay que tenerlo registrado.

-> Ruta: POST http://localhost:8081/api/libros

JSON
{
    "idLibro": "L052",
    "titulo": "El Principito",
    "autor": "Antoine de Saint-Exupéry",
    "editorial": "Salamandra",
    "anioPublicacion": 1943,
    "categoria": "Fábula",
    "copiasDisponibles": 10,
    "ubicacionEstanteria": "Pasillo A-1"
}

***3. Crear un préstamo
Aquí es donde unimos todo. Registramos que el usuario que creamos se lleva el libro.

-> Ruta: POST http://localhost:8081/api/prestamos

JSON
{
    "usuario": { "id": 52 },
    "libro": { "idLibro": "L052" },
    "fechaDevolucionEsperada": "2026-04-01"
}

✅ Notas finales
  *Fechas automáticas: No te preocupes por la fecha en que se registra un usuario o un libro, el sistema la pone en automático al momento de guardar.

  *Confirmación: Si recibes un código 200 OK en Postman, ¡felicidades! La operación fue exitosa y los datos ya están seguros en la base de datos.



✅ asi corre en la terminal 
./mvnw spring-boot:run