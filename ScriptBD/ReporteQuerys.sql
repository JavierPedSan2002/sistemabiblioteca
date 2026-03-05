-- Usuarios morosos (no devolvieron)
SELECT
    u.id_usuario,
    u.nombre_completo,
    l.titulo,
    p.fecha_devolucion_esperada,
    p.monto_multa_generado
FROM
    prestamos p
    JOIN usuarios u ON p.id_usuario = u.id_usuario
    JOIN libros l ON p.id_libro = l.id_libro
WHERE
    p.estado_prestamo = 'En Moroso'
ORDER BY
    p.fecha_devolucion_esperada;

-- Usuarios sin préstamos activos ni morosos y sin multa.
SELECT
    u.id_usuario,
    u.nombre_completo
FROM
    usuarios u
WHERE
    u.id_usuario NOT IN (
        SELECT
            id_usuario
        FROM
            prestamos
        WHERE
            estado_prestamo = 'Activo'
            OR estado_prestamo = 'En Moroso'
            OR monto_multa_generado > 0
    );

--  usuarios con libros prestados actualmente
SELECT
    u.id_usuario,
    u.nombre_completo,
    l.titulo,
    p.fecha_salida,
    p.fecha_devolucion_esperada
FROM
    prestamos p
    JOIN usuarios u ON p.id_usuario = u.id_usuario
    JOIN libros l ON p.id_libro = l.id_libro
WHERE
    p.estado_prestamo = 'Activo';

-- Historial de libros prestados por usuario
SELECT
    u.nombre_completo AS usuario,
    l.id_libro,
    l.titulo AS libro,
    p.fecha_salida,
    p.fecha_devolucion_esperada,
    p.fecha_devolucion_real,
    p.estado_prestamo
FROM
    prestamos p
    JOIN usuarios u ON p.id_usuario = u.id_usuario
    JOIN libros l ON p.id_libro = l.id_libro
ORDER BY
    usuario,
    p.fecha_salida DESC;

-- Qué libros se prestaron más
SELECT
    l.id_libro,
    l.titulo,
    COUNT(p.id_prestamo) AS total_prestamos,
    SUM(
        CASE
            WHEN p.estado_prestamo = 'Activo' THEN 1
            ELSE 0
        END
    ) AS activos,
    SUM(
        CASE
            WHEN p.estado_prestamo = 'En Moroso' THEN 1
            ELSE 0
        END
    ) AS morosos
FROM
    libros l
    LEFT JOIN prestamos p ON l.id_libro = p.id_libro
GROUP BY
    l.id_libro,
    l.titulo
ORDER BY
    total_prestamos DESC;

-- historial Usuarios con más préstamos
SELECT
    u.id_usuario,
    u.nombre_completo,
    COUNT(*) total_prestamos
FROM
    prestamos p
    JOIN usuarios u ON p.id_usuario = u.id_usuario
GROUP BY
    u.id_usuario
ORDER BY
    total_prestamos DESC;

-- Usuarios con deuda total acumulada
SELECT
    u.id_usuario,
    u.nombre_completo,
    SUM(p.monto_multa_generado) AS deuda_total
FROM
    prestamos p
    JOIN usuarios u ON p.id_usuario = u.id_usuario
GROUP BY
    u.id_usuario
HAVING
    deuda_total > 0
ORDER BY
    deuda_total DESC;

-- Libros que actualmente están prestados
SELECT
    l.id_libro,
    l.titulo,
    COUNT(*) AS copias_prestadas
FROM
    prestamos p
    JOIN libros l ON p.id_libro = l.id_libro
WHERE
    p.estado_prestamo = 'Activo'
GROUP BY
    l.id_libro,
    l.titulo;

-- Libros que nunca se han prestado
SELECT
    l.id_libro,
    l.titulo
FROM
    libros l
    LEFT JOIN prestamos p ON l.id_libro = p.id_libro
WHERE
    p.id_prestamo IS NULL;

-- Bibliotecarios que más préstamos registran
SELECT
    b.nombre_completo AS bibliotecario,
    COUNT(*) total_prestamos_registrados
FROM
    prestamos p
    JOIN usuarios b ON p.id_bibliotecario_entrega = b.id_usuario
GROUP BY
    b.id_usuario
ORDER BY
    total_prestamos_registrados DESC;

-- Cantidad de préstamos por categoría de libro
SELECT
    l.categoria,
    COUNT(*) total_prestamos
FROM
    prestamos p
    JOIN libros l ON p.id_libro = l.id_libro
GROUP BY
    l.categoria
ORDER BY
    total_prestamos DESC;

-- Préstamos que vencen pronto
SELECT
    u.nombre_completo,
    l.titulo,
    p.fecha_devolucion_esperada
FROM
    prestamos p
    JOIN usuarios u ON p.id_usuario = u.id_usuario
    JOIN libros l ON p.id_libro = l.id_libro
WHERE
    p.estado_prestamo = 'Activo'
ORDER BY
    p.fecha_devolucion_esperada;

-- Libros con multas generadas
SELECT
    l.titulo,
    SUM(p.monto_multa_generado) total_multas
FROM
    prestamos p
    JOIN libros l ON p.id_libro = l.id_libro
GROUP BY
    l.id_libro
HAVING
    total_multas > 0
ORDER BY
    total_multas DESC;

-- Estado general de préstamos
SELECT
    estado_prestamo,
    COUNT(*) total
FROM
    prestamos
GROUP BY
    estado_prestamo;

-- libros disponibles
SELECT
    l.id_libro,
    l.titulo,
    3 - COUNT(p.id_prestamo) AS copias_disponibles
FROM
    libros l
    LEFT JOIN prestamos p ON l.id_libro = p.id_libro
    AND p.estado_prestamo IN ('Activo', 'En Moroso')
GROUP BY
    l.id_libro,
    l.titulo
HAVING
    copias_disponibles > 0;