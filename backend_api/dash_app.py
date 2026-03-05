import dash
from dash import dash_table, html
import pandas as pd
import requests

app = dash.Dash(__name__)

API = "http://localhost:8000/reportes"

def obtener_datos(endpoint):
    try:
        r = requests.get(f"{API}/{endpoint}")
        if r.status_code == 200:
            return pd.DataFrame(r.json())
        else:
            return pd.DataFrame()
    except:
        return pd.DataFrame()

# Obtener datos de la API
morosos = obtener_datos("usuarios-morosos")
usuarios_libres = obtener_datos("usuarios-libres")
prestamos_actuales = obtener_datos("prestamos-actuales")
historial = obtener_datos("historial-prestamos")
libros_mas = obtener_datos("libros-mas-prestados")
usuarios_mas = obtener_datos("usuarios-mas-prestamos")
usuarios_deuda = obtener_datos("usuarios-con-deuda")
libros_disp = obtener_datos("libros-disponibles")
libros_nunca = obtener_datos("libros-nunca-prestados")
estado = obtener_datos("estado-prestamos")


def crear_tabla(df):
    return dash_table.DataTable(
        data=df.to_dict("records"),
        columns=[{"name": i, "id": i} for i in df.columns],
        page_size=10,
        style_table={"overflowX": "auto"},
        style_header={"backgroundColor": "black", "color": "white"},
        style_cell={"textAlign": "left"}
    )


app.layout = html.Div([
    
    html.H1("Sistema de Biblioteca - Reportes", style={"textAlign":"center"}),

    html.H2("Usuarios Morosos"),
    crear_tabla(morosos),

    html.H2("Usuarios sin préstamos ni multas"),
    crear_tabla(usuarios_libres),

    html.H2("Préstamos Activos"),
    crear_tabla(prestamos_actuales),

    html.H2("Historial de Préstamos"),
    crear_tabla(historial),

    html.H2("Libros más prestados"),
    crear_tabla(libros_mas),

    html.H2("Usuarios con más préstamos"),
    crear_tabla(usuarios_mas),

    html.H2("Usuarios con deuda"),
    crear_tabla(usuarios_deuda),

    html.H2("Libros disponibles"),
    crear_tabla(libros_disp),

    html.H2("Libros que nunca se han prestado"),
    crear_tabla(libros_nunca),

    html.H2("Estado de préstamos"),
    crear_tabla(estado)

])


if __name__ == "__main__":
    app.run(debug=True, port=8050)