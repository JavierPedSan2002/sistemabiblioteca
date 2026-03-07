import dash
from dash import dcc, html, dash_table
import pandas as pd
import requests
import plotly.express as px

API = "http://127.0.0.1:8000"


# FUNCION PARA OBTENER DATOS
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


# CARGAR DATOS
estado = obtener("/reportes/estado-prestamos")
libros = obtener("/reportes/libros-mas-prestados").head(10)
usuarios = obtener("/reportes/usuarios-mas-prestamos").head(10)
multas = obtener("/reportes/usuarios-con-deuda").head(10)
bibliotecarios = obtener("/reportes/bibliotecarios-mas-prestamos").head(10)


morosos = obtener("/reportes/usuarios-morosos")
usuarios_libres = obtener("/reportes/usuarios-libres")
prestamos_actuales = obtener("/reportes/prestamos-actuales")
historial = obtener("/reportes/historial-prestamos")
libros_disponibles = obtener("/reportes/libros-disponibles")
libros_nunca = obtener("/reportes/libros-nunca-prestados")


# GRAFICAS

# Estado prestamos
if not estado.empty:
    fig_estado = px.pie(
        estado,
        names="estado_prestamo",
        values="total",
        title="Estado de préstamos"
    )
else:
    fig_estado = px.pie(title="Sin datos")
    
# Bibliotecarios 
if not bibliotecarios.empty:
    fig_bibliotecarios = px.pie(
    bibliotecarios,
    names="bibliotecario",
    values="total_prestamos_registrados",
    hole=0.4,
    title="Top 10 Bibliotecarios que registran más préstamos"
)
else:
    fig_bibliotecarios = px.pie(title="Sin datos")


# Top 10 libros
if not libros.empty:
    fig_libros = px.bar(libros, x="titulo", y="total_prestamos",
                        title="Top 10  Libros más prestados")
else:
    fig_estado = px.pie(title="Sin datos")


# Top 10 usuarios
if not usuarios.empty:
    fig_usuarios = px.bar(
    usuarios,
    x="nombre_completo",
    y="total_prestamos",
    title="Top 10 Usuarios con más préstamos"
)
else:
    fig_usuarios = px.bar(title="Sin datos")


# Top 10 multas
if not multas.empty:
    fig_multas = px.bar(
    multas,
    x="deuda_total",
    y="nombre_completo",
    orientation="h",
    title="Top 10 Usuarios con más deuda"
)
else:
    fig_multas = px.bar(title="Sin datos")



# FUNCION PARA TABLAS

def crear_tabla(df):
    return dash_table.DataTable(
        data=df.to_dict("records"),
        columns=[{"name": i, "id": i} for i in df.columns],
        page_size=10,
        style_table={"overflowX": "auto"},
        style_header={"backgroundColor": "black", "color": "white"},
        style_cell={"textAlign": "left"}
    )


# APP

app = dash.Dash(__name__)

app.layout = html.Div([

    html.H1("📊 Dashboard Biblioteca", style={"textAlign": "center"}),

    dcc.Tabs([

        # TAB GRAFICAS
        dcc.Tab(label="📊 Gráficas", children=[

        # PASTELES EN LA MISMA LINEA
        html.Div([
            dcc.Graph(figure=fig_estado, style={"width": "50%"}),
            dcc.Graph(figure=fig_bibliotecarios, style={"width": "50%"})
        ], style={"display": "flex"}),

        # BARRAS
        dcc.Graph(figure=fig_libros),
        dcc.Graph(figure=fig_usuarios),
        dcc.Graph(figure=fig_multas),


        html.P(
            "Las gráficas muestran únicamente los primeros 10 registros.",
            style={"textAlign": "center", "fontStyle": "italic"}
        )

    ]),

        # TAB TABLAS
        dcc.Tab(label="📋 Reportes", children=[

            html.H2("Historial de Préstamos"),
            crear_tabla(historial),

            html.H2("Usuarios Morosos"),
            crear_tabla(morosos),

            html.H2("Usuarios sin préstamos ni multas"),
            crear_tabla(usuarios_libres),

            html.H2("Préstamos Activos"),
            crear_tabla(prestamos_actuales),

            html.H2("Libros disponibles"),
            crear_tabla(libros_disponibles),

            html.H2("Libros que nunca se han prestado"),
            crear_tabla(libros_nunca),

        ])

    ])

])


if __name__ == "__main__":
    app.run(debug=True, port=8050)