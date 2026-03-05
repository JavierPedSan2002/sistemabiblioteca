import dash
from dash import dcc, html, dash_table
import pandas as pd
import requests
import plotly.express as px

API = "http://127.0.0.1:8000"

def obtener(endpoint):
    try:
        r = requests.get(f"{API}{endpoint}")
        data = r.json()

        if not data:
            return pd.DataFrame()

        return pd.DataFrame(data)

    except Exception as e:
        print("Error:", e)
        return pd.DataFrame()

# =========================
# CARGAR DATOS
# =========================

libros = obtener("/reportes/libros-mas-prestados")
usuarios = obtener("/reportes/usuarios-mas-prestamos")
multas = obtener("/reportes/usuarios-con-deuda")
estado = obtener("/reportes/estado-prestamos")
historial = obtener("/reportes/historial-prestamos")

# =========================
# GRÁFICAS
# =========================

if not libros.empty:
    fig_libros = px.bar(
        libros,
        x="titulo",
        y="total_prestamos",
        title="📚 Libros más prestados"
    )
else:
    fig_libros = px.bar(title=" Libros más prestados (sin datos)")


if not usuarios.empty:
    fig_usuarios = px.bar(
        usuarios,
        x="nombre_completo",
        y="total_prestamos",
        title="👤 Usuarios con más préstamos"
    )
else:
    fig_usuarios = px.bar(title=" Usuarios con más préstamos (sin datos)")


if not multas.empty:
    fig_multas = px.bar(
        multas,
        x="nombre_completo",
        y="deuda_total",
        title="💰 Multas acumuladas"
    )
else:
    fig_multas = px.bar(title=" Multas acumuladas (sin datos)")


if not estado.empty:
    fig_estado = px.pie(
        estado,
        names="estado_prestamo",
        values="total",
        title="📊 Estado de préstamos"
    )
else:
    fig_estado = px.pie(title="Estado de préstamos (sin datos)")


# =========================
# DASH APP
# =========================

app = dash.Dash(__name__)

app.layout = html.Div([

    html.H1(" Dashboard Biblioteca", style={"textAlign": "center"}),

    html.Br(),

    html.Div([
        dcc.Graph(figure=fig_libros)
    ]),

    html.Div([
        dcc.Graph(figure=fig_usuarios)
    ]),

    html.Div([
        dcc.Graph(figure=fig_multas)
    ]),

    html.Div([
        dcc.Graph(figure=fig_estado)
    ]),

    html.H2("Historial de préstamos"),

    dash_table.DataTable(
        data=historial.to_dict("records"),
        columns=[{"name": i, "id": i} for i in historial.columns],
        page_size=10,
        style_table={"overflowX": "auto"},
    )

])

if __name__ == "__main__":
    app.run(debug=True, port=8050)