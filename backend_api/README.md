

# 📚 Sistema de Biblioteca Universitaria

### API REST + Dashboard de Visualización


Sistema de reportes para una **Biblioteca Universitaria**  que incluye:

* API REST construida con **FastAPI**
* Dashboard interactivo con **Dash + Plotly**
* Conexión a base de datos **MySQL**
* Reportes de préstamos, usuarios y libros

El objetivo del proyecto es visualizar información de la biblioteca mediante **endpoints y dashboards interactivos**.

---

# 🚀 Características

✔ API REST con FastAPI
✔ Dashboard interactivo con Dash
✔ Gráficas avanzadas con Plotly
✔ Conexión a MySQL
✔ Documentación automática con Swagger
✔ Arquitectura separada API / Dashboard

---

# 📂 Estructura del Proyecto

```
sistemabiblioteca
│
├── backend_api
│   │
│   ├── app
│   │     └── main.py          # API FastAPI
│   │
│   ├── dash_app.py            # Dashboard versión 1
│   ├── requirements.txt
│   └── .env
│
└── dashboard
      └── dashboard.py         # Dashboard versión 2 con Plotly
```


---

# 🧠 Configuración del Backend (API)

## 2️⃣ Entrar al backend

```bash
cd backend_api
```

## 3️⃣ Crear entorno virtual

```bash
python3 -m venv venv
```

Activar entorno:

Mac / Linux

```bash
source venv/bin/activate
```

Windows

```bash
venv\\Scripts\\activate
```

---

## 4️⃣ Instalar dependencias

```bash
pip install -r requirements.txt
```

---

## 5️⃣ Configurar variables de entorno

Editar el archivo `.env`

```
DB_TYPE=mysql
DB_HOST=localhost
DB_PORT=3306
DB_NAME=biblioteca_universidad
DB_USER=root
DB_PASSWORD=helado22

APP_NAME=Dashboard API
```

---

# ▶️ Ejecutar la API

```bash
uvicorn app.main:app --reload --host 0.0.0.0 --port 8000
```

La API se ejecutará en:

```
http://localhost:8000
```

---

# 📄 Documentación de la API

FastAPI genera documentación automática:

```
http://localhost:8000/docs
```

Aquí puedes probar todos los endpoints directamente desde el navegador.

---

# 🔗 Endpoints disponibles

| Endpoint                         | Descripción                |
| -------------------------------- | -------------------------- |
| /reportes/usuarios-morosos       | Usuarios con adeudos       |
| /reportes/usuarios-libres        | Usuarios sin adeudos       |
| /reportes/prestamos-actuales     | Préstamos activos          |
| /reportes/historial-prestamos    | Historial completo         |
| /reportes/libros-mas-prestados   | Libros más solicitados     |
| /reportes/usuarios-mas-prestamos | Usuarios con más préstamos |
| /reportes/usuarios-con-deuda     | Usuarios con multas        |
| /reportes/libros-disponibles     | Libros disponibles         |
| /reportes/libros-nunca-prestados | Libros nunca prestados     |
| /reportes/estado-prestamos       | Estado de préstamos        |

Ejemplo:

```
http://localhost:8000/reportes/libros-disponibles
```

---

# 📊 Dashboard 1.0

Dashboard básico para visualizar datos consumiendo la API.

## 1️⃣ Entrar a la carpeta del backend

```bash
cd backend_api
```

## 2️⃣ Instalar dependencias (si no las tienes)

```bash
pip install dash pandas requests
```

## 3️⃣ Ejecutar la API con FastAPI

```bash
uvicorn app.main:app --reload
```

La API se ejecutará en:

```
http://localhost:8000
```

## 4️⃣ Ejecutar el Dashboard

Abrir **otra terminal** y entrar nuevamente al backend:

```bash
cd backend_api
```

Ejecutar Dash:

```bash
python dash_app.py
```

## 5️⃣ Abrir el Dashboard

Entrar en el navegador a:

```
http://localhost:8050
```


# 📈 Dashboard 2.0 (Visualización avanzada)

Dashboard con **gráficas avanzadas utilizando Plotly** para visualizar los reportes de la biblioteca.

## 1️⃣ Entrar a la carpeta del backend

```bash
cd backend_api
```

## 2️⃣ Instalar librerías necesarias

```bash
pip install dash pandas requests plotly
```

## 3️⃣ Ejecutar la API

```bash
uvicorn main:app --reload
```

La API correrá en:

```
http://localhost:8000
```

## 4️⃣ Ejecutar el Dashboard

Abrir **otra terminal**.

Entrar a la carpeta del dashboard:

```bash
cd dashboard
```

Ejecutar el dashboard:

```bash
python dashboard.py
```

## 5️⃣ Abrir el Dashboard

Entra en el navegador a:

```
http://localhost:8050
```

---

# 👨‍💻 Autor

**Ricardo Garcia**


---



