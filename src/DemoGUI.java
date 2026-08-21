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
                // Validar campos
                if (txtNombre.getText().isEmpty() || cbForma.getValue() == null ||
                        cbTipo.getValue() == null || cbCondicion.getValue() == null) {
                    mostrarAlerta("Error", "Todos los campos obligatorios deben llenarse");
                    return;
                }

                boolean evaluado = "Si".equals(cbValidez.getValue());
                // Convertir autores: separar por coma y eliminar espacios
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
        Label lblInstruccion = new Label("");
        TextField txtInput = new TextField();
        Button btnEjecutar = new Button("Buscar");
        ListView<String> listaResultados = new ListView<>();
        Button btnRegresarSubmenu = new Button("Regresar al Submenú");
        layoutAccion.getChildren().addAll(lblInstruccion, txtInput, btnEjecutar,
                listaResultados, btnRegresarSubmenu);
        escenaAccion = new Scene(layoutAccion, 500, 450);

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

        // Estilo a todos los botones
        Button[] botones = {btnBuscarAutor, btnBuscarTipo, btnBuscarForma,
                btnBuscarCondicion, btnBuscarEvaluacion, btnOrdenarClave,
                btnOrdenarAutor, btnEliminar, btnMostrarTodos};
        for (Button b : botones) {
            b.setStyle("-fx-min-width: 250px;");
        }
        btnRegresarMain.setStyle("-fx-min-width: 250px; -fx-background-color: #868e96;");

        //buscar por autor
        btnBuscarAutor.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, txtInput, btnEjecutar, listaResultados,
                    "Ingresa el nombre del autor a buscar:", true);
            btnEjecutar.setOnAction(ev -> {
                listaResultados.getItems().clear();
                List<Instrumento> res = gestor.buscarPorAutor(txtInput.getText());
                for(Instrumento i : res) listaResultados.getItems().add(i.toString());
            });
            ventana.setScene(escenaAccion);
        });

        //buscar por tipo
        btnBuscarTipo.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, txtInput, btnEjecutar, listaResultados,
                    "Ingresa el tipo (identificar/manejar):", true);
            btnEjecutar.setOnAction(ev -> {
                listaResultados.getItems().clear();
                List<Instrumento> res = gestor.buscarPorTipo(txtInput.getText());
                for(Instrumento i : res) listaResultados.getItems().add(i.toString());
            });
            ventana.setScene(escenaAccion);
        });

        //buscar por forma
        btnBuscarForma.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, txtInput, btnEjecutar, listaResultados,
                    "Ingresa la forma (test/escala/cuestionario):", true);
            btnEjecutar.setOnAction(ev -> {
                listaResultados.getItems().clear();
                List<Instrumento> res = gestor.buscarPorForma(txtInput.getText());
                for(Instrumento i : res) listaResultados.getItems().add(i.toString());
            });
            ventana.setScene(escenaAccion);
        });

        //buscar por condición
        btnBuscarCondicion.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, txtInput, btnEjecutar, listaResultados,
                    "Ingresa la condición (ansiedad/estres):", true);
            btnEjecutar.setOnAction(ev -> {
                listaResultados.getItems().clear();
                List<Instrumento> res = gestor.buscarPorCondicion(txtInput.getText());
                for(Instrumento i : res) listaResultados.getItems().add(i.toString());
            });
            ventana.setScene(escenaAccion);
        });

        //buscar por evaluación
        btnBuscarEvaluacion.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, txtInput, btnEjecutar, listaResultados,
                    "¿El instrumento está evaluado? ('si' , 'no'):", true);
            btnEjecutar.setOnAction(ev -> {
                listaResultados.getItems().clear();
                boolean esEvaluado = txtInput.getText().equalsIgnoreCase("si");
                List<Instrumento> res = gestor.buscarPorEvaluacion(esEvaluado);
                for(Instrumento i : res) listaResultados.getItems().add(i.toString());
            });
            ventana.setScene(escenaAccion);
        });

        //ordenar por clave
        btnOrdenarClave.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, txtInput, btnEjecutar, listaResultados,
                    "Instrumentos ordenados por Clave:", false);
            List<Instrumento> res = gestor.ordenarPorClave();
            for(Instrumento i : res) listaResultados.getItems().add(i.toString());
            ventana.setScene(escenaAccion);
        });

        //ordenar por autor
        btnOrdenarAutor.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, txtInput, btnEjecutar, listaResultados,
                    "Instrumentos ordenados por Primer Autor:", false);
            List<Instrumento> res = gestor.ordenarPorPrimerAutor();
            for(Instrumento i : res) listaResultados.getItems().add(i.toString());
            ventana.setScene(escenaAccion);
        });

        //eliminar por clave
        btnEliminar.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, txtInput, btnEjecutar, listaResultados,
                    "Ingresa el nombre del instrumento a eliminar:", true);
            btnEjecutar.setOnAction(ev -> {
                listaResultados.getItems().clear();
                if (gestor.eliminarPorClave(txtInput.getText())) {
                    listaResultados.getItems().add("Se eliminó el instrumento correctamente.");
                } else {
                    listaResultados.getItems().add("No se encontró ningún instrumento con ese nombre.");
                }
            });
            ventana.setScene(escenaAccion);
        });

        //mostrar todos los instrumentos
        btnMostrarTodos.setOnAction(e -> {
            prepararPantallaAccion(lblInstruccion, txtInput, btnEjecutar, listaResultados,
                    "Todos los instrumentos registrados:", false);
            List<Instrumento> res = gestor.obtenerTodos();
            for(Instrumento i : res) listaResultados.getItems().add(i.toString());
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
            //guardado automatico al salir
            try {
                AlmacenamientoCSV.guardarCSV("Instrumentos.csv", gestor.obtenerTodos());
            } catch (Exception ex) {
                System.out.println("Error al guardar: " + ex.getMessage());
            }
            ventana.close();
        });

        //carga automatica al iniciar
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

    //esto es para no ocupar reescribir el layout de gestión de instrumentos por cada gestión
    private void prepararPantallaAccion(Label lbl, TextField txt, Button btn,
                                        ListView<String> lista, String textoInstruccion,
                                        boolean requiereInput) {
        lbl.setText(textoInstruccion);
        txt.clear();
        lista.getItems().clear();
        txt.setVisible(requiereInput);
        txt.setManaged(requiereInput);
        btn.setVisible(requiereInput);
        btn.setManaged(requiereInput);
    }

    // Metodo para mostrar alertas
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