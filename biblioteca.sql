
CREATE DATABASE biblioteca_universidad;
USE biblioteca_universidad;

-- 1. Tabla de Roles para manejar los tipos de usuario y permisos
CREATE TABLE roles (
    id_rol SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(50) UNIQUE NOT NULL -- 'Estudiante', 'Profesor', 'Bibliotecario', 'SuperAdmin'
);

-- 2. Tabla de Usuarios (RF-01 a RF-04)
CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre_completo VARCHAR(150) NOT NULL,
    correo_electronico VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    id_rol INT REFERENCES roles(id_rol),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado BOOLEAN DEFAULT TRUE, -- TRUE = Activo, FALSE = Inactivo 
    score_deuda DECIMAL(10,2) DEFAULT 0.00,
    fotografia_url VARCHAR(255) -- Requerimiento de identificación vigente
);

-- 3. Tabla de Libros (RF-05 a RF-08)
CREATE TABLE libros (
    id_libro VARCHAR(20) PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    editorial VARCHAR(100),
    anio_publicacion INT,
    categoria VARCHAR(100),
    copias_disponibles INT NOT NULL CHECK (copias_disponibles >= 0),
    ubicacion_estanteria VARCHAR(50)
);

-- 4. Tabla de Préstamos 
CREATE TABLE prestamos (
    id_prestamo SERIAL PRIMARY KEY,
    isbn VARCHAR(20) REFERENCES libros(id_libro),
    id_usuario INT REFERENCES usuarios(id_usuario),
    id_bibliotecario_entrega INT REFERENCES usuarios(id_usuario), -- Solo Bibliotecario/Admin
    fecha_salida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_devolucion_esperada DATE NOT NULL,
    fecha_devolucion_real TIMESTAMP,
    monto_multa_generado DECIMAL(10,2) DEFAULT 0.00,
    estado_prestamo VARCHAR(20) DEFAULT 'Activo' -- 'Activo', 'Devuelto', 'En Moroso'
);

-- 5. Variables Globales y Configuración 
CREATE TABLE configuracion_sistema (
    clave VARCHAR(50) PRIMARY KEY,
    valor_texto TEXT,
    valor_numerico DECIMAL(10,2),
    valor_fecha DATE
);

-- 6. Tabla de Días Feriados (Para la regla de no contabilizar fines de semana/feriados)
CREATE TABLE dias_no_laborales (
    fecha DATE PRIMARY KEY,
    descripcion VARCHAR(100)
);