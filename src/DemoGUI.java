import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.*;

public class DemoGUI extends Application {

    private SistemaSMI gestor = new SistemaSMI();
    private Scene escenaMenu, escenaRegistro, escenaSubmenuGestion, escenaAccion;

    @Override
    public void start(Stage ventana) {
        ventana.setTitle("Sistema de Instrumentos - APA");

        //botones del menú principal
        VBox layoutMenu = new VBox(15);
        layoutMenu.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label lblMenu = new Label("--- MENU PRINCIPAL ---");
        lblMenu.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button btnIrRegistro = new Button("Registrar nuevo instrumento");
        btnIrRegistro.setStyle("-fx-min-width: 250px;");

        Button btnIrGestion = new Button("Gestionar instrumentos");
        btnIrGestion.setStyle("-fx-min-width: 250px;");

        Button btnSalir = new Button("Salir");
        btnSalir.setStyle("-fx-min-width: 250px; -fx-background-color: #ff6b6b;");

        layoutMenu.getChildren().addAll(lblMenu, btnIrRegistro, btnIrGestion, btnSalir);
        escenaMenu = new Scene(layoutMenu, 350, 300);

        //botones del registro de instrumento
        VBox layoutRegistro = new VBox(8);
        layoutRegistro.setStyle("-fx-padding: 20;");

        Label lblTituloRegistro = new Label("--- REGISTRAR NUEVO INSTRUMENTO ---");
        lblTituloRegistro.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del instrumento");
        txtNombre.setStyle("-fx-min-width: 250px;");

        ComboBox<String> cbForma = new ComboBox<>();
        cbForma.getItems().addAll("Test", "Escala", "Cuestionario");
        cbForma.setPromptText("Seleccione la forma");

        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("Identificar", "Manejar", "Ambos");
        cbTipo.setPromptText("Seleccione el tipo");

        ComboBox<String> cbCondicion = new ComboBox<>();
        cbCondicion.getItems().addAll("Ansiedad", "Estres", "Ambos");
        cbCondicion.setPromptText("Seleccione la condicion");

        TextField txtAutores = new TextField();
        txtAutores.setPromptText("Autores (ej. Juan, Pedro)");

        ComboBox<String> cbValidez = new ComboBox<>();
        cbValidez.getItems().addAll("Si", "No");
        cbValidez.setPromptText("Tiene evaluacion de validez?");
        cbValidez.setValue("No");

        TextField txtCita = new TextField();
        txtCita.setPromptText("Cita bibliografica");
        txtCita.setDisable(true);

        //si la validez fue marcada como no, no se puede poner cita
        cbValidez.setOnAction(e -> {
            if ("No".equals(cbValidez.getValue())) {
                txtCita.setDisable(true);
                txtCita.setText("");
            } else {
                txtCita.setDisable(false);
            }
        });

        Button btnGuardar = new Button("Guardar Instrumento");
        btnGuardar.setStyle("-fx-background-color: #51cf66;");

        Button btnCancelarReg = new Button("Regresar al Menu");
        btnCancelarReg.setStyle("-fx-background-color: #868e96;");

        btnGuardar.setOnAction(e -> {
            try {
                if (txtNombre.getText().isEmpty() || cbForma.getValue() == null ||
                        cbTipo.getValue() == null || cbCondicion.getValue() == null) {
                    mostrarAlerta("Error", "Todos los campos obligatorios deben llenarse");
                    return;
                }

                boolean evaluado = "Si".equals(cbValidez.getValue());
                List<String> autores = new ArrayList<>();
                if (!txtAutores.getText().isEmpty()) {
                    String[] partes = txtAutores.getText().split(",");
                    for (String p : partes) {
                        autores.add(p.trim());
                    }
                }

                Instrumento nuevo = new Instrumento(
                        txtNombre.getText(),
                        cbForma.getValue(),
                        cbTipo.getValue(),
                        cbCondicion.getValue(),
                        autores,
                        evaluado,
                        evaluado ? txtCita.getText() : ""
                );

                if (gestor.agregarInstrumento(nuevo)) {
                    System.out.println("Guardado: " + txtNombre.getText());
                    txtNombre.clear();
                    txtAutores.clear();
                    txtCita.clear();
                    cbForma.setValue(null);
                    cbTipo.setValue(null);
                    cbCondicion.setValue(null);
                    cbValidez.setValue("No");
                } else {
                    System.out.println("Error: Ya existe un instrumento con ese nombre.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        });

        btnCancelarReg.setOnAction(e -> ventana.setScene(escenaMenu));

        layoutRegistro.getChildren().addAll(
                lblTituloRegistro,
                new Label("Nombre:"), txtNombre,
                new Label("Forma:"), cbForma,
                new Label("Tipo:"), cbTipo,
                new Label("Condicion:"), cbCondicion,
                new Label("Autores:"), txtAutores,
                new Label("Validez:"), cbValidez,
                new Label("Cita:"), txtCita,
                btnGuardar,
                btnCancelarReg
        );
        escenaRegistro = new Scene(layoutRegistro, 350, 650);

        VBox layoutAccion = new VBox(10);
        layoutAccion.setStyle("-fx-padding: 20;");

        Label lblInstruccion = new Label("");
        lblInstruccion.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        ComboBox<String> cbFiltro = new ComboBox<>();
        cbFiltro.setPromptText("Seleccione una opcion");
        cbFiltro.setStyle("-fx-min-width: 200px;");

        TextField txtInput = new TextField();
        txtInput.setPromptText("Ingrese el valor a buscar...");

        Button btnEjecutar = new Button("Ejecutar");
        btnEjecutar.setStyle("-fx-background-color: #4dabf7;");

        ListView<String> listaResultados = new ListView<>();
        listaResultados.setStyle("-fx-min-height: 200px;");

        Button btnRegresarSubmenu = new Button("Regresar al Submenu");

        layoutAccion.getChildren().addAll(lblInstruccion, cbFiltro, txtInput, btnEjecutar,
                listaResultados, btnRegresarSubmenu);
        escenaAccion = new Scene(layoutAccion, 500, 500);

        btnRegresarSubmenu.setOnAction(e -> ventana.setScene(escenaSubmenuGestion));

        //botones del menu de gestion de instrumentos
        VBox layoutSubmenu = new VBox(8);
        layoutSubmenu.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label lblSubmenu = new Label("--- GESTIONAR INSTRUMENTOS ---");
        lblSubmenu.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button btnBuscarAutor = new Button("Buscar por Autor");
        Button btnBuscarTipo = new Button("Buscar por Tipo");
        Button btnBuscarForma = new Button("Buscar por Forma");
        Button btnBuscarCondicion = new Button("Buscar por Condicion");
        Button btnBuscarEvaluacion = new Button("Buscar por Evaluacion");
        Button btnOrdenarClave = new Button("Ordenar por Clave");
        Button btnOrdenarAutor = new Button("Ordenar por Primer Autor");
        Button btnEliminar = new Button("Eliminar por Clave");
        Button btnMostrarTodos = new Button("Mostrar Todos");
        Button btnRegresarMain = new Button("Regresar al Menu Principal");

        Button[] botones = {btnBuscarAutor, btnBuscarTipo, btnBuscarForma,
                btnBuscarCondicion, btnBuscarEvaluacion, btnOrdenarClave,
                btnOrdenarAutor, btnEliminar, btnMostrarTodos};
        for (Button b : botones) {
            b.setStyle("-fx-min-width: 250px;");
        }
        btnRegresarMain.setStyle("-fx-min-width: 250px; -fx-background-color: #868e96;");

        //buscar por autor
        btnBuscarAutor.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, cbFiltro, txtInput, btnEjecutar,
                    listaResultados, "Ingresa el nombre del autor a buscar:", false, null);
            btnEjecutar.setOnAction(ev -> {
                listaResultados.getItems().clear();
                String autor = txtInput.getText();
                if (!autor.isEmpty()) {
                    List<Instrumento> res = gestor.buscarPorAutor(autor);
                    for(Instrumento i : res) listaResultados.getItems().add(i.toString());
                    if (res.isEmpty()) {
                        listaResultados.getItems().add("No se encontraron resultados");
                    }
                }
            });
            ventana.setScene(escenaAccion);
        });

        //buscar por tipo
        btnBuscarTipo.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, cbFiltro, txtInput, btnEjecutar,
                    listaResultados, "Seleccione el tipo:", true, "Identificar", "Manejar", "Ambos");
            btnEjecutar.setOnAction(ev -> {
                listaResultados.getItems().clear();
                String tipo = cbFiltro.getValue();
                if (tipo != null) {
                    List<Instrumento> res = gestor.buscarPorTipo(tipo);
                    for(Instrumento i : res) listaResultados.getItems().add(i.toString());
                    if (res.isEmpty()) {
                        listaResultados.getItems().add("No se encontraron resultados");
                    }
                }
            });
            ventana.setScene(escenaAccion);
        });

        //buscar por forma
        btnBuscarForma.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, cbFiltro, txtInput, btnEjecutar,
                    listaResultados, "Seleccione la forma:", true, "Test", "Escala", "Cuestionario");
            btnEjecutar.setOnAction(ev -> {
                listaResultados.getItems().clear();
                String forma = cbFiltro.getValue();
                if (forma != null) {
                    List<Instrumento> res = gestor.buscarPorForma(forma);
                    for(Instrumento i : res) listaResultados.getItems().add(i.toString());
                    if (res.isEmpty()) {
                        listaResultados.getItems().add("No se encontraron resultados");
                    }
                }
            });
            ventana.setScene(escenaAccion);
        });

        //buscar por condición
        btnBuscarCondicion.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, cbFiltro, txtInput, btnEjecutar,
                    listaResultados, "Seleccione la condicion:", true, "Ansiedad", "Estres", "Ambos");
            btnEjecutar.setOnAction(ev -> {
                listaResultados.getItems().clear();
                String condicion = cbFiltro.getValue();
                if (condicion != null) {
                    List<Instrumento> res = gestor.buscarPorCondicion(condicion);
                    for(Instrumento i : res) listaResultados.getItems().add(i.toString());
                    if (res.isEmpty()) {
                        listaResultados.getItems().add("No se encontraron resultados");
                    }
                }
            });
            ventana.setScene(escenaAccion);
        });

        //buscar por evaluación
        btnBuscarEvaluacion.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, cbFiltro, txtInput, btnEjecutar,
                    listaResultados, "Seleccione si esta evaluado:", true, "Si", "No");
            btnEjecutar.setOnAction(ev -> {
                listaResultados.getItems().clear();
                String valor = cbFiltro.getValue();
                if (valor != null) {
                    boolean evaluado = valor.equals("Si");
                    List<Instrumento> res = gestor.buscarPorEvaluacion(evaluado);
                    for(Instrumento i : res) listaResultados.getItems().add(i.toString());
                    if (res.isEmpty()) {
                        listaResultados.getItems().add("No se encontraron resultados");
                    }
                }
            });
            ventana.setScene(escenaAccion);
        });

        //ordenar por clave
        btnOrdenarClave.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, cbFiltro, txtInput, btnEjecutar,
                    listaResultados, "Instrumentos ordenados por Clave:", false, null);
            List<Instrumento> res = gestor.ordenarPorClave();
            for(Instrumento i : res) listaResultados.getItems().add(i.toString());
            if (res.isEmpty()) {
                listaResultados.getItems().add("No hay instrumentos registrados");
            }
            ventana.setScene(escenaAccion);
        });

        //ordenar por autor
        btnOrdenarAutor.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, cbFiltro, txtInput, btnEjecutar,
                    listaResultados, "Instrumentos ordenados por Primer Autor:", false, null);
            List<Instrumento> res = gestor.ordenarPorPrimerAutor();
            for(Instrumento i : res) listaResultados.getItems().add(i.toString());
            if (res.isEmpty()) {
                listaResultados.getItems().add("No hay instrumentos registrados");
            }
            ventana.setScene(escenaAccion);
        });

        //eliminar por clave
        btnEliminar.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, cbFiltro, txtInput, btnEjecutar,
                    listaResultados, "Ingresa el nombre del instrumento a eliminar:", false, null);
            btnEjecutar.setOnAction(ev -> {
                listaResultados.getItems().clear();
                String nombre = txtInput.getText();
                if (!nombre.isEmpty()) {
                    if (gestor.eliminarPorClave(nombre)) {
                        listaResultados.getItems().add("Instrumento eliminado correctamente");
                    } else {
                        listaResultados.getItems().add("No se encontro instrumento con ese nombre");
                    }
                }
            });
            ventana.setScene(escenaAccion);
        });

        //mostrar todos los instrumentos
        btnMostrarTodos.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, cbFiltro, txtInput, btnEjecutar,
                    listaResultados, "Todos los instrumentos registrados:", false, null);
            List<Instrumento> res = gestor.obtenerTodos();
            for(Instrumento i : res) listaResultados.getItems().add(i.toString());
            if (res.isEmpty()) {
                listaResultados.getItems().add("No hay instrumentos registrados");
            }
            ventana.setScene(escenaAccion);
        });

        btnRegresarMain.setOnAction(e -> ventana.setScene(escenaMenu));

        layoutSubmenu.getChildren().addAll(
                lblSubmenu, btnBuscarAutor, btnBuscarTipo, btnBuscarForma,
                btnBuscarCondicion, btnBuscarEvaluacion, btnOrdenarClave,
                btnOrdenarAutor, btnEliminar, btnMostrarTodos, btnRegresarMain
        );
        escenaSubmenuGestion = new Scene(layoutSubmenu, 350, 500);

        //acción de botones del menú principal
        btnIrRegistro.setOnAction(e -> ventana.setScene(escenaRegistro));
        btnIrGestion.setOnAction(e -> ventana.setScene(escenaSubmenuGestion));
        btnSalir.setOnAction(e -> {
            try {
                AlmacenamientoCSV.guardarCSV("Instrumentos.csv", gestor.obtenerTodos());
            } catch (Exception ex) {
                System.out.println("Error al guardar: " + ex.getMessage());
            }
            ventana.close();
        });

        try {
            List<Instrumento> cargados = AlmacenamientoCSV.cargarCSV("Instrumentos.csv");
            gestor.cargarInstrumentos(cargados);
            System.out.println("Cargados " + cargados.size() + " instrumentos");
        } catch (Exception e) {
            System.out.println("No se encontro archivo previo");
        }

        ventana.setScene(escenaMenu);
        ventana.show();
    }

    private void prepararPantallaAccion(Label lbl, ComboBox<String> cb, TextField txt, Button btn,
                                        ListView<String> lista, String textoInstruccion,
                                        boolean usarComboBox, String... opciones) {
        lbl.setText(textoInstruccion);
        txt.clear();
        lista.getItems().clear();

        if (usarComboBox && opciones != null) {
            cb.getItems().clear();
            cb.getItems().addAll(opciones);
            cb.setValue(null);
            cb.setVisible(true);
            cb.setManaged(true);
            txt.setVisible(false);
            txt.setManaged(false);
            btn.setVisible(true);
            btn.setManaged(true);
        } else {
            cb.setVisible(false);
            cb.setManaged(false);
            txt.setVisible(true);
            txt.setManaged(true);
            btn.setVisible(true);
            btn.setManaged(true);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}