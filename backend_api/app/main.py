from fastapi import FastAPI, Depends
from fastapi.middleware.cors import CORSMiddleware

from app.settings import APP_NAME, IS_MYSQL
from app.db import get_conn

app = FastAPI(title=APP_NAME)

# Dash corre en 8050 y consume la API en 8000
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:8050", "http://127.0.0.1:8050"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

def _cursor(conn):
    # MySQL necesita diccionarios; Postgres ya entrega dict_row
    return conn.cursor(dictionary=True) if IS_MYSQL else conn.cursor()

# --- GESTIÓN DE USUARIOS ---

@app.get("/usuarios")
def listar_usuarios(conn=Depends(get_conn)):
    # Configurar cursor según el motor de base de datos
    cur = conn.cursor(dictionary=True) if IS_MYSQL else conn.cursor()
    
    try:
        cur.execute("""
            SELECT id_usuario, nombre_completo, correo_electronico, estado
            FROM usuarios
            ORDER BY id_usuario
            LIMIT 20;
        """)
        # Si es Postgres sin RealDictCursor, podrías necesitar convertir la tupla a dict manualmente
        rows = cur.fetchall()
        return rows
    finally:
        cur.close()

# --- GESTIÓN DE LIBROS ---

@app.get("/libros")
def buscar_libros(titulo: str = None, conn=Depends(get_conn)):
    cur = conn.cursor(dictionary=True) if IS_MYSQL else conn.cursor()
    try:
        query = "SELECT id_libro, titulo, autor, copias_disponibles FROM libros"
        if titulo:
            query += f" WHERE titulo LIKE '%{titulo}%'"
        
        cur.execute(query)
        return cur.fetchall()
    finally:
        cur.close()


# --- USUARIOS MOROSOS (no devolvieron libros) ---
@app.get("/reportes/usuarios-morosos")
def usuarios_morosos(conn=Depends(get_conn)):
    cur = conn.cursor()
    try:
        cur.execute("""
            SELECT
                u.id_usuario,
                u.nombre_completo,
                l.titulo,
                p.fecha_devolucion_esperada,
                p.monto_multa_generado
            FROM prestamos p
            JOIN usuarios u ON p.id_usuario = u.id_usuario
            JOIN libros l ON p.id_libro = l.id_libro
            WHERE p.estado_prestamo = 'En Moroso'
            ORDER BY p.fecha_devolucion_esperada;
        """)

        columns = [col[0] for col in cur.description]
        rows = [dict(zip(columns,row)) for row in cur.fetchall()]
        return rows

    finally:
        cur.close()
# --- USUARIOS SIN PRÉSTAMOS ACTIVOS NI MOROSOS ---
@app.get("/reportes/usuarios-libres")
def usuarios_libres(conn=Depends(get_conn)):
    cur = conn.cursor()
    try:
        cur.execute("""
            SELECT
                u.id_usuario,
                u.nombre_completo
            FROM usuarios u
            WHERE u.id_usuario NOT IN (
                SELECT id_usuario
                FROM prestamos
                WHERE estado_prestamo = 'Activo'
                OR estado_prestamo = 'En Moroso'
                OR monto_multa_generado > 0
            );
        """)

        columns = [col[0] for col in cur.description]
        rows = [dict(zip(columns,row)) for row in cur.fetchall()]
        return rows
    finally:
        cur.close()
# --- USUARIOS CON LIBROS PRESTADOS ACTUALMENTE ---
@app.get("/reportes/prestamos-actuales")
def prestamos_actuales(conn=Depends(get_conn)):
    cur = conn.cursor()
    try:
        cur.execute("""
            SELECT
                u.id_usuario,
                u.nombre_completo,
                l.titulo,
                p.fecha_salida,
                p.fecha_devolucion_esperada
            FROM prestamos p
            JOIN usuarios u ON p.id_usuario = u.id_usuario
            JOIN libros l ON p.id_libro = l.id_libro
            WHERE p.estado_prestamo = 'Activo';
        """)

        columns = [col[0] for col in cur.description]
        rows = [dict(zip(columns,row)) for row in cur.fetchall()]
        return rows
    finally:
        cur.close()
# --- HISTORIAL DE LIBROS PRESTADOS POR USUARIO ---
@app.get("/reportes/historial-prestamos")
def historial_prestamos(conn=Depends(get_conn)):
    cur = conn.cursor()
    try:
        cur.execute("""
            SELECT
                u.nombre_completo AS usuario,
                l.id_libro,
                l.titulo AS libro,
                p.fecha_salida,
                p.fecha_devolucion_esperada,
                p.fecha_devolucion_real,
                p.estado_prestamo
            FROM prestamos p
            JOIN usuarios u ON p.id_usuario = u.id_usuario
            JOIN libros l ON p.id_libro = l.id_libro
            ORDER BY usuario, p.fecha_salida DESC;
        """)

        columns = [col[0] for col in cur.description]
        rows = [dict(zip(columns,row)) for row in cur.fetchall()]
        return rows
    finally:
        cur.close()
# --- LIBROS MÁS PRESTADOS ---
@app.get("/reportes/libros-mas-prestados")
def libros_mas_prestados(conn=Depends(get_conn)):
    cur = conn.cursor()
    try:
        cur.execute("""
            SELECT
                l.id_libro,
                l.titulo,
                COUNT(p.id_prestamo) AS total_prestamos,
                SUM(CASE WHEN p.estado_prestamo = 'Activo' THEN 1 ELSE 0 END) AS activos,
                SUM(CASE WHEN p.estado_prestamo = 'En Moroso' THEN 1 ELSE 0 END) AS morosos
            FROM libros l
            LEFT JOIN prestamos p ON l.id_libro = p.id_libro
            GROUP BY l.id_libro, l.titulo
            ORDER BY total_prestamos DESC;
        """)

        columns = [col[0] for col in cur.description]
        rows = [dict(zip(columns,row)) for row in cur.fetchall()]
        return rows
    finally:
        cur.close()
# --- USUARIOS CON MÁS PRÉSTAMOS ---
@app.get("/reportes/usuarios-mas-prestamos")
def usuarios_mas_prestamos(conn=Depends(get_conn)):
    cur = conn.cursor()
    try:
        cur.execute("""
            SELECT
                u.id_usuario,
                u.nombre_completo,
                COUNT(*) AS total_prestamos
            FROM prestamos p
            JOIN usuarios u ON p.id_usuario = u.id_usuario
            GROUP BY u.id_usuario, u.nombre_completo
            ORDER BY total_prestamos DESC;
        """)

        columns = [col[0] for col in cur.description]
        rows = [dict(zip(columns,row)) for row in cur.fetchall()]
        return rows
    finally:
        cur.close()
# --- USUARIOS CON DEUDA TOTAL ---
@app.get("/reportes/usuarios-con-deuda")
def usuarios_con_deuda(conn=Depends(get_conn)):
    cur = conn.cursor()
    try:
        cur.execute("""
            SELECT
                u.id_usuario,
                u.nombre_completo,
                SUM(p.monto_multa_generado) AS deuda_total
            FROM prestamos p
            JOIN usuarios u ON p.id_usuario = u.id_usuario
            GROUP BY u.id_usuario, u.nombre_completo
            HAVING SUM(p.monto_multa_generado) > 0
            ORDER BY deuda_total DESC;
        """)

        columns = [col[0] for col in cur.description]
        rows = [dict(zip(columns,row)) for row in cur.fetchall()]
        return rows
    finally:
        cur.close()
# --- LIBROS DISPONIBLES (máximo 3 copias) ---
@app.get("/reportes/libros-disponibles")
def libros_disponibles(conn=Depends(get_conn)):
    cur = conn.cursor()
    try:
        cur.execute("""
            SELECT
                l.id_libro,
                l.titulo,
                3 - COUNT(p.id_prestamo) AS copias_disponibles
            FROM libros l
            LEFT JOIN prestamos p
                ON l.id_libro = p.id_libro
                AND p.estado_prestamo IN ('Activo','En Moroso')
            GROUP BY l.id_libro, l.titulo
            HAVING copias_disponibles > 0;
        """)

        columns = [col[0] for col in cur.description]
        rows = [dict(zip(columns,row)) for row in cur.fetchall()]
        return rows
    finally:
        cur.close()
# --- LIBROS QUE NUNCA SE HAN PRESTADO ---
@app.get("/reportes/libros-nunca-prestados")
def libros_nunca_prestados(conn=Depends(get_conn)):
    cur = conn.cursor()
    try:
        cur.execute("""
            SELECT
                l.id_libro,
                l.titulo
            FROM libros l
            LEFT JOIN prestamos p ON l.id_libro = p.id_libro
            WHERE p.id_prestamo IS NULL;
        """)

        columns = [col[0] for col in cur.description]
        rows = [dict(zip(columns,row)) for row in cur.fetchall()]
        return rows
    finally:
        cur.close()
# --- ESTADO GENERAL DE PRÉSTAMOS ---
@app.get("/reportes/estado-prestamos")
def estado_prestamos(conn=Depends(get_conn)):
    cur = conn.cursor()
    try:
        cur.execute("""
            SELECT
                estado_prestamo,
                COUNT(*) AS total
            FROM prestamos
            GROUP BY estado_prestamo;
        """)

        columns = [col[0] for col in cur.description]
        rows = [dict(zip(columns,row)) for row in cur.fetchall()]
        return rows
    finally:
        cur.close()
# --- BIBLIOTECARIOS QUE MÁS PRÉSTAMOS REGISTRAN ---
@app.get("/reportes/bibliotecarios-mas-prestamos")
def bibliotecarios_mas_prestamos(conn=Depends(get_conn)):
    cur = conn.cursor()
    try:
        cur.execute("""
            SELECT
                b.id_usuario,
                b.nombre_completo AS bibliotecario,
                COUNT(*) AS total_prestamos_registrados
            FROM prestamos p
            JOIN usuarios b ON p.id_bibliotecario_entrega = b.id_usuario
            GROUP BY b.id_usuario, b.nombre_completo
            ORDER BY total_prestamos_registrados DESC;
        """)

        columns = [col[0] for col in cur.description]
        rows = [dict(zip(columns, row)) for row in cur.fetchall()]
        return rows

    finally:
        cur.close()
