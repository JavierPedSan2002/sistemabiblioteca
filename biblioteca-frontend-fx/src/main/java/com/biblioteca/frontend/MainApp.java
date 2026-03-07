package com.biblioteca.frontend;

import java.awt.Desktop;
import java.net.URI;
import java.util.List;

import com.biblioteca.frontend.models.Libro;
import com.biblioteca.frontend.models.Prestamo;
import com.biblioteca.frontend.models.Usuario;
import com.biblioteca.frontend.services.LibroService;
import com.biblioteca.frontend.services.PrestamoService;
import com.biblioteca.frontend.services.UsuarioService;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private final UsuarioService usuarioService = new UsuarioService();
    private final LibroService libroService = new LibroService();
    private final PrestamoService prestamoService = new PrestamoService();

    private final TableView<Usuario> tableUsuarios = new TableView<>();
    private final ObservableList<Usuario> usuariosData = FXCollections.observableArrayList();

    private final TableView<Libro> tableLibros = new TableView<>();
    private final ObservableList<Libro> librosData = FXCollections.observableArrayList();

    private final TableView<Prestamo> tablePrestamos = new TableView<>();
    private final ObservableList<Prestamo> prestamosData = FXCollections.observableArrayList();

    @Override
public void start(Stage stage) {

    TabPane tabs = new TabPane();

    Tab tabUsuarios = new Tab("Usuarios", crearVistaUsuarios());
    tabUsuarios.setClosable(false);

    Tab tabLibros = new Tab("Libros", crearVistaLibros());
    tabLibros.setClosable(false);

    Tab tabPrestamos = new Tab("Préstamos", crearVistaPrestamos());
    tabPrestamos.setClosable(false);

    tabs.getTabs().addAll(tabUsuarios, tabLibros, tabPrestamos);


    // BOTON REPORTES
    Button btnReportes = new Button("Reportes");

    btnReportes.setOnAction(e -> {
        try {
            Desktop.getDesktop().browse(new URI("http://localhost:8050/"));
        } catch (Exception ex) {
            mostrarAlerta("Error", "No se pudo abrir el dashboard.");
        }
    });


    // BARRA SUPERIOR
    HBox barraSuperior = new HBox(btnReportes);
barraSuperior.setPadding(new Insets(10));
barraSuperior.setStyle("-fx-alignment: center-right;");


    // LAYOUT PRINCIPAL
    VBox root = new VBox(barraSuperior, tabs);

    Scene scene = new Scene(root, 1180, 680);

    stage.setTitle("Sistema Biblioteca");
    stage.setScene(scene);
    stage.show();
}

    /* ===============================
       USUARIOS
    =============================== */

    private VBox crearVistaUsuarios() {

        configurarTablaUsuarios();
        cargarUsuarios();

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtNombre.setPrefWidth(180);

        TextField txtCorreo = new TextField();
        txtCorreo.setPromptText("Correo");
        txtCorreo.setPrefWidth(220);

        TextField txtPassword = new TextField();
        txtPassword.setPromptText("Password");
        txtPassword.setPrefWidth(180);

        TextField txtRol = new TextField();
        txtRol.setPromptText("Rol");
        txtRol.setPrefWidth(90);

        Button btnCrear = new Button("Crear");
        Button btnGuardarCambios = new Button("Guardar cambios");
        Button btnDesactivar = new Button("Desactivar");
        Button btnEliminar = new Button("Eliminar");
        Button btnRecargar = new Button("Recargar");
        Button btnLimpiar = new Button("Limpiar");

        btnCrear.setOnAction(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String correo = txtCorreo.getText().trim();
                String password = txtPassword.getText().trim();
                String rolTexto = txtRol.getText().trim();

                if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || rolTexto.isEmpty()) {
                    mostrarAlerta("Validación", "Completa todos los campos del usuario.");
                    return;
                }

                Integer idRol;
                try {
                    idRol = Integer.parseInt(rolTexto);
                } catch (NumberFormatException ex) {
                    mostrarAlerta("Validación", "El rol debe ser numérico.");
                    return;
                }

                Usuario u = new Usuario();
                u.setNombreCompleto(nombre);
                u.setCorreoElectronico(correo);
                u.setPasswordHash(password);
                u.setIdRol(idRol);
                u.setEstado(true);

                boolean ok = usuarioService.crearUsuario(u);

                if (ok) {
                    mostrarInfo("Éxito", "Usuario creado correctamente.");
                    limpiarFormularioUsuario(txtNombre, txtCorreo, txtPassword, txtRol);
                    cargarUsuarios();
                } else {
                    mostrarAlerta("Error", "No se pudo crear el usuario.");
                }

            } catch (Exception ex) {
                mostrarAlerta("Error", "Ocurrió un problema al crear el usuario.");
            }
        });

        btnGuardarCambios.setOnAction(e -> {
            try {
                Usuario seleccionado = tableUsuarios.getSelectionModel().getSelectedItem();

                if (seleccionado == null) {
                    mostrarAlerta("Error", "Selecciona un usuario para actualizar.");
                    return;
                }

                String nombre = txtNombre.getText().trim();
                String correo = txtCorreo.getText().trim();
                String rolTexto = txtRol.getText().trim();
                String password = txtPassword.getText().trim();

                if (nombre.isEmpty() || correo.isEmpty() || rolTexto.isEmpty()) {
                    mostrarAlerta("Validación", "Nombre, correo y rol son obligatorios.");
                    return;
                }

                Integer idRol;
                try {
                    idRol = Integer.parseInt(rolTexto);
                } catch (NumberFormatException ex) {
                    mostrarAlerta("Validación", "El rol debe ser numérico.");
                    return;
                }

                seleccionado.setNombreCompleto(nombre);
                seleccionado.setCorreoElectronico(correo);
                seleccionado.setIdRol(idRol);

                if (!password.isEmpty()) {
                    seleccionado.setPasswordHash(password);
                }

                boolean ok = usuarioService.actualizarUsuario(seleccionado);

                if (ok) {
                    mostrarInfo("Éxito", "Usuario actualizado correctamente.");
                    cargarUsuarios();
                    limpiarFormularioUsuario(txtNombre, txtCorreo, txtPassword, txtRol);
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar el usuario.");
                }

            } catch (Exception ex) {
                mostrarAlerta("Error", "Ocurrió un problema al actualizar el usuario.");
            }
        });

        btnDesactivar.setOnAction(e -> {
            Usuario seleccionado = tableUsuarios.getSelectionModel().getSelectedItem();

            if (seleccionado == null) {
                mostrarAlerta("Error", "Selecciona un usuario.");
                return;
            }

            boolean ok = usuarioService.desactivarUsuario(seleccionado.getId());

            if (ok) {
                mostrarInfo("Éxito", "Usuario desactivado correctamente.");
                cargarUsuarios();
                limpiarFormularioUsuario(txtNombre, txtCorreo, txtPassword, txtRol);
            } else {
                mostrarAlerta("Error", "No se pudo desactivar el usuario.");
            }
        });

        btnEliminar.setOnAction(e -> {
            Usuario seleccionado = tableUsuarios.getSelectionModel().getSelectedItem();

            if (seleccionado == null) {
                mostrarAlerta("Error", "Selecciona un usuario.");
                return;
            }

            boolean ok = usuarioService.eliminarUsuario(seleccionado.getId());

            if (ok) {
                mostrarInfo("Éxito", "Usuario eliminado correctamente.");
                cargarUsuarios();
                limpiarFormularioUsuario(txtNombre, txtCorreo, txtPassword, txtRol);
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el usuario.");
            }
        });

        btnRecargar.setOnAction(e -> cargarUsuarios());
        btnLimpiar.setOnAction(e -> limpiarFormularioUsuario(txtNombre, txtCorreo, txtPassword, txtRol));

        tableUsuarios.setRowFactory(tv -> {
            TableRow<Usuario> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Usuario u = row.getItem();
                    txtNombre.setText(valor(u.getNombreCompleto()));
                    txtCorreo.setText(valor(u.getCorreoElectronico()));
                    txtPassword.clear();
                    txtRol.setText(u.getIdRol() != null ? String.valueOf(u.getIdRol()) : "");
                }
            });
            return row;
        });

        VBox layout = new VBox(10,
                new HBox(10, txtNombre, txtCorreo, txtPassword, txtRol),
                new HBox(10, btnCrear, btnGuardarCambios, btnDesactivar, btnEliminar, btnRecargar, btnLimpiar),
                tableUsuarios
        );

        layout.setPadding(new Insets(15));
        return layout;
    }

    private void configurarTablaUsuarios() {

        tableUsuarios.getColumns().clear();

        TableColumn<Usuario, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(60);

        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colNombre.setPrefWidth(240);

        TableColumn<Usuario, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
        colCorreo.setPrefWidth(320);

        TableColumn<Usuario, Integer> colRol = new TableColumn<>("Rol");
        colRol.setCellValueFactory(new PropertyValueFactory<>("idRol"));
        colRol.setPrefWidth(80);

        TableColumn<Usuario, Boolean> colEstado = new TableColumn<>("Activo");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEstado.setPrefWidth(80);

        tableUsuarios.getColumns().addAll(colId, colNombre, colCorreo, colRol, colEstado);
        tableUsuarios.setItems(usuariosData);
        tableUsuarios.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableUsuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }

    private void cargarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.obtenerUsuarios();
            usuariosData.setAll(usuarios);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar los usuarios.");
        }
    }

    private void limpiarFormularioUsuario(TextField nombre, TextField correo, TextField password, TextField rol) {
        nombre.clear();
        correo.clear();
        password.clear();
        rol.clear();
        tableUsuarios.getSelectionModel().clearSelection();
    }

    /* ===============================
       LIBROS
    =============================== */

    private VBox crearVistaLibros() {

        configurarTablaLibros();
        cargarLibros();

        TextField txtIsbn = new TextField();
        txtIsbn.setPromptText("ISBN / ID Libro");
        txtIsbn.setPrefWidth(150);

        TextField txtTitulo = new TextField();
        txtTitulo.setPromptText("Título");
        txtTitulo.setPrefWidth(210);

        TextField txtAutor = new TextField();
        txtAutor.setPromptText("Autor");
        txtAutor.setPrefWidth(180);

        TextField txtEditorial = new TextField();
        txtEditorial.setPromptText("Editorial");
        txtEditorial.setPrefWidth(150);

        TextField txtAnio = new TextField();
        txtAnio.setPromptText("Año");
        txtAnio.setPrefWidth(80);

        TextField txtCategoria = new TextField();
        txtCategoria.setPromptText("Categoría");
        txtCategoria.setPrefWidth(130);

        TextField txtCopias = new TextField();
        txtCopias.setPromptText("Copias");
        txtCopias.setPrefWidth(80);

        TextField txtUbicacion = new TextField();
        txtUbicacion.setPromptText("Ubicación");
        txtUbicacion.setPrefWidth(100);

        Button btnCrear = new Button("Crear");
        Button btnGuardarCambios = new Button("Guardar cambios");
        Button btnEliminar = new Button("Eliminar");
        Button btnRecargar = new Button("Recargar");
        Button btnLimpiar = new Button("Limpiar");

        btnCrear.setOnAction(e -> {
            try {
                String isbn = txtIsbn.getText().trim();
                String titulo = txtTitulo.getText().trim();
                String autor = txtAutor.getText().trim();

                if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty()) {
                    mostrarAlerta("Validación", "ISBN, título y autor son obligatorios.");
                    return;
                }

                Libro l = new Libro();
                l.setIdLibro(isbn);
                l.setTitulo(titulo);
                l.setAutor(autor);
                l.setEditorial(valor(txtEditorial.getText()).trim());
                l.setCategoria(valor(txtCategoria.getText()).trim());
                l.setUbicacionEstanteria(valor(txtUbicacion.getText()).trim());

                if (!txtAnio.getText().trim().isEmpty()) {
                    l.setAnioPublicacion(Integer.parseInt(txtAnio.getText().trim()));
                }

                if (!txtCopias.getText().trim().isEmpty()) {
                    l.setCopiasDisponibles(Integer.parseInt(txtCopias.getText().trim()));
                }

                boolean ok = libroService.crearLibro(l);

                if (ok) {
                    mostrarInfo("Éxito", "Libro creado correctamente.");
                    limpiarFormularioLibro(txtIsbn, txtTitulo, txtAutor, txtEditorial, txtAnio, txtCategoria, txtCopias, txtUbicacion);
                    cargarLibros();
                } else {
                    mostrarAlerta("Error", "No se pudo crear el libro.");
                }

            } catch (NumberFormatException ex) {
                mostrarAlerta("Validación", "Año y copias deben ser numéricos.");
            } catch (Exception ex) {
                mostrarAlerta("Error", "Ocurrió un problema al crear el libro.");
            }
        });

        btnGuardarCambios.setOnAction(e -> {
            try {
                Libro seleccionado = tableLibros.getSelectionModel().getSelectedItem();

                if (seleccionado == null) {
                    mostrarAlerta("Error", "Selecciona un libro para actualizar.");
                    return;
                }

                String isbn = txtIsbn.getText().trim();
                String titulo = txtTitulo.getText().trim();
                String autor = txtAutor.getText().trim();

                if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty()) {
                    mostrarAlerta("Validación", "ISBN, título y autor son obligatorios.");
                    return;
                }

                seleccionado.setIdLibro(isbn);
                seleccionado.setTitulo(titulo);
                seleccionado.setAutor(autor);
                seleccionado.setEditorial(valor(txtEditorial.getText()).trim());
                seleccionado.setCategoria(valor(txtCategoria.getText()).trim());
                seleccionado.setUbicacionEstanteria(valor(txtUbicacion.getText()).trim());

                if (!txtAnio.getText().trim().isEmpty()) {
                    seleccionado.setAnioPublicacion(Integer.parseInt(txtAnio.getText().trim()));
                }

                if (!txtCopias.getText().trim().isEmpty()) {
                    seleccionado.setCopiasDisponibles(Integer.parseInt(txtCopias.getText().trim()));
                }

                boolean ok = libroService.actualizarLibro(seleccionado);

                if (ok) {
                    mostrarInfo("Éxito", "Libro actualizado correctamente.");
                    cargarLibros();
                    limpiarFormularioLibro(txtIsbn, txtTitulo, txtAutor, txtEditorial, txtAnio, txtCategoria, txtCopias, txtUbicacion);
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar el libro.");
                }

            } catch (NumberFormatException ex) {
                mostrarAlerta("Validación", "Año y copias deben ser numéricos.");
            } catch (Exception ex) {
                mostrarAlerta("Error", "Ocurrió un problema al actualizar el libro.");
            }
        });

        btnEliminar.setOnAction(e -> {
            Libro seleccionado = tableLibros.getSelectionModel().getSelectedItem();

            if (seleccionado == null) {
                mostrarAlerta("Error", "Selecciona un libro.");
                return;
            }

            boolean ok = libroService.eliminarLibro(seleccionado.getIdLibro());

            if (ok) {
                mostrarInfo("Éxito", "Libro eliminado correctamente.");
                cargarLibros();
                limpiarFormularioLibro(txtIsbn, txtTitulo, txtAutor, txtEditorial, txtAnio, txtCategoria, txtCopias, txtUbicacion);
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el libro.");
            }
        });

        btnRecargar.setOnAction(e -> cargarLibros());
        btnLimpiar.setOnAction(e -> limpiarFormularioLibro(txtIsbn, txtTitulo, txtAutor, txtEditorial, txtAnio, txtCategoria, txtCopias, txtUbicacion));

        tableLibros.setRowFactory(tv -> {
            TableRow<Libro> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Libro l = row.getItem();
                    txtIsbn.setText(valor(l.getIdLibro()));
                    txtTitulo.setText(valor(l.getTitulo()));
                    txtAutor.setText(valor(l.getAutor()));
                    txtEditorial.setText(valor(l.getEditorial()));
                    txtCategoria.setText(valor(l.getCategoria()));
                    txtUbicacion.setText(valor(l.getUbicacionEstanteria()));
                    txtAnio.setText(l.getAnioPublicacion() != null ? String.valueOf(l.getAnioPublicacion()) : "");
                    txtCopias.setText(l.getCopiasDisponibles() != null ? String.valueOf(l.getCopiasDisponibles()) : "");
                }
            });
            return row;
        });

        VBox layout = new VBox(10,
                new HBox(10, txtIsbn, txtTitulo, txtAutor, txtEditorial),
                new HBox(10, txtAnio, txtCategoria, txtCopias, txtUbicacion),
                new HBox(10, btnCrear, btnGuardarCambios, btnEliminar, btnRecargar, btnLimpiar),
                tableLibros
        );

        layout.setPadding(new Insets(15));
        return layout;
    }

    private void configurarTablaLibros() {

        tableLibros.getColumns().clear();

        TableColumn<Libro, String> colIsbn = new TableColumn<>("ISBN");
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("idLibro"));
        colIsbn.setPrefWidth(120);

        TableColumn<Libro, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colTitulo.setPrefWidth(240);

        TableColumn<Libro, String> colAutor = new TableColumn<>("Autor");
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAutor.setPrefWidth(180);

        TableColumn<Libro, String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colCategoria.setPrefWidth(130);

        TableColumn<Libro, Integer> colCopias = new TableColumn<>("Copias");
        colCopias.setCellValueFactory(new PropertyValueFactory<>("copiasDisponibles"));
        colCopias.setPrefWidth(80);

        TableColumn<Libro, String> colUbicacion = new TableColumn<>("Ubicación");
        colUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacionEstanteria"));
        colUbicacion.setPrefWidth(100);

        tableLibros.getColumns().addAll(colIsbn, colTitulo, colAutor, colCategoria, colCopias, colUbicacion);
        tableLibros.setItems(librosData);
        tableLibros.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableLibros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }

    private void cargarLibros() {
        try {
            List<Libro> libros = libroService.obtenerLibros();
            librosData.setAll(libros);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar los libros.");
        }
    }

    private void limpiarFormularioLibro(
            TextField isbn,
            TextField titulo,
            TextField autor,
            TextField editorial,
            TextField anio,
            TextField categoria,
            TextField copias,
            TextField ubicacion
    ) {
        isbn.clear();
        titulo.clear();
        autor.clear();
        editorial.clear();
        anio.clear();
        categoria.clear();
        copias.clear();
        ubicacion.clear();
        tableLibros.getSelectionModel().clearSelection();
    }

    /* ===============================
       PRESTAMOS
    =============================== */

    private VBox crearVistaPrestamos() {

        configurarTablaPrestamos();
        cargarPrestamos();

        Button btnRecargar = new Button("Recargar");
        Button btnDevolver = new Button("Marcar devolución");
        Button btnEliminar = new Button("Eliminar");

        btnRecargar.setOnAction(e -> cargarPrestamos());

        btnDevolver.setOnAction(e -> {
            Prestamo seleccionado = tablePrestamos.getSelectionModel().getSelectedItem();

            if (seleccionado == null) {
                mostrarAlerta("Error", "Selecciona un préstamo.");
                return;
            }

            boolean ok = prestamoService.devolverPrestamo(seleccionado.getId());

            if (ok) {
                mostrarInfo("Éxito", "Préstamo marcado como devuelto.");
                cargarPrestamos();
            } else {
                mostrarAlerta("Error", "No se pudo registrar la devolución.");
            }
        });

        btnEliminar.setOnAction(e -> {
            Prestamo seleccionado = tablePrestamos.getSelectionModel().getSelectedItem();

            if (seleccionado == null) {
                mostrarAlerta("Error", "Selecciona un préstamo.");
                return;
            }

            boolean ok = prestamoService.eliminarPrestamo(seleccionado.getId());

            if (ok) {
                mostrarInfo("Éxito", "Préstamo eliminado correctamente.");
                cargarPrestamos();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el préstamo.");
            }
        });

        VBox layout = new VBox(10,
                new HBox(10, btnRecargar, btnDevolver, btnEliminar),
                tablePrestamos
        );

        layout.setPadding(new Insets(15));
        return layout;
    }

    private void configurarTablaPrestamos() {

        tablePrestamos.getColumns().clear();

        TableColumn<Prestamo, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(60);

        TableColumn<Prestamo, String> colUsuario = new TableColumn<>("Usuario");
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        colUsuario.setPrefWidth(220);

        TableColumn<Prestamo, String> colLibro = new TableColumn<>("Libro");
        colLibro.setCellValueFactory(new PropertyValueFactory<>("tituloLibro"));
        colLibro.setPrefWidth(260);

        TableColumn<Prestamo, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoPrestamo"));
        colEstado.setPrefWidth(100);

        TableColumn<Prestamo, String> colFechaPrestamo = new TableColumn<>("Fecha préstamo");
        colFechaPrestamo.setCellValueFactory(new PropertyValueFactory<>("fechaPrestamo"));
        colFechaPrestamo.setPrefWidth(170);

        TableColumn<Prestamo, String> colFechaDevolucion = new TableColumn<>("Fecha devolución");
        colFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucionEsperada"));
        colFechaDevolucion.setPrefWidth(150);

        tablePrestamos.getColumns().addAll(
                colId,
                colUsuario,
                colLibro,
                colEstado,
                colFechaPrestamo,
                colFechaDevolucion
        );

        tablePrestamos.setItems(prestamosData);
        tablePrestamos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tablePrestamos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }

    private void cargarPrestamos() {
        try {
            List<Prestamo> prestamos = prestamoService.obtenerPrestamos();
            prestamosData.setAll(prestamos);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar los préstamos.");
        }
    }

    /* ===============================
       UTILIDADES
    =============================== */

    private String valor(String s) {
        return s == null ? "" : s;
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void mostrarInfo(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}