import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class DemoGUI extends Application {

    private SistemaSMI gestor = new SistemaSMI();
    private Scene escenaMenu, escenaRegistro, escenaSubmenuGestion, escenaAccion;

    @Override
    public void start(Stage ventana) {
        ventana.setTitle("Instrumentos");
        
        //botones del menu principal
        VBox layoutMenu = new VBox(15);
        Label lblMenu = new Label("--- MENÚ PRINCIPAL ---");
        Button btnIrRegistro = new Button("Registrar nuevo instrumento");
        Button btnIrGestion = new Button("Gestionar instrumentos");
        Button btnSalir = new Button("Salir");
        layoutMenu.getChildren().addAll(lblMenu, btnIrRegistro, btnIrGestion, btnSalir);
        escenaMenu = new Scene(layoutMenu, 300, 200);

        //botones del registro de instrumento
        VBox layoutRegistro = new VBox(10);
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del instrumento");
        ComboBox<String> cbForma = new ComboBox<>();
        cbForma.getItems().addAll("Test", "Escala", "Cuestionario");
        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("Identificar", "Manejar");
        ComboBox<String> cbCondicion = new ComboBox<>();
        cbCondicion.getItems().addAll("Ansiedad", "Estres", "Ambos");
        TextField txtAutores = new TextField();
        txtAutores.setPromptText("Autores (ej. Juan, Pedro)");
        ComboBox<String> cbValidez = new ComboBox<>();
        cbValidez.getItems().addAll("Sí", "No");
        TextField txtCita = new TextField();
        txtCita.setPromptText("Cita bibliográfica");
        //si la validez es no, no se puede poner cita
        cbValidez.setOnAction(e -> {
            if ("No".equals(cbValidez.getValue())) {
                txtCita.setDisable(true);
                txtCita.setText("");
            } else {
                txtCita.setDisable(false);
            }
        });

        Button btnGuardar = new Button("Guardar");
        Button btnCancelarReg = new Button("Regresar al Menú");

        btnGuardar.setOnAction(e -> {
            boolean evaluado = "Sí".equals(cbValidez.getValue());
            Instrumento nuevo = new Instrumento(
                    txtNombre.getText(), cbForma.getValue(), cbTipo.getValue(),
                    cbCondicion.getValue(), Arrays.asList(txtAutores.getText().split(",")),
                    evaluado, txtCita.getText()
            );
            gestor.agregarInstrumento(nuevo);
            System.out.println("Guardado: " + txtNombre.getText());

            txtNombre.clear(); txtAutores.clear(); txtCita.clear();
            ventana.setScene(escenaMenu);
        });

        btnCancelarReg.setOnAction(e -> ventana.setScene(escenaMenu));

        layoutRegistro.getChildren().addAll(
                new Label("Nombre:"), txtNombre, new Label("Forma:"), cbForma,
                new Label("Tipo:"), cbTipo, new Label("Condición:"), cbCondicion,
                new Label("Autores:"), txtAutores, new Label("Validez:"), cbValidez,
                new Label("Cita:"), txtCita, btnGuardar, btnCancelarReg
        );
        escenaRegistro = new Scene(layoutRegistro, 300, 500);

        VBox layoutAccion = new VBox(10);
        Label lblInstruccion = new Label("Instrucción aquí");
        TextField txtInput = new TextField();
        Button btnEjecutar = new Button("Buscar");
        ListView<String> listaResultados = new ListView<>();
        Button btnRegresarSubmenu = new Button("Regresar al Submenú");
        layoutAccion.getChildren().addAll(lblInstruccion, txtInput, btnEjecutar, listaResultados, btnRegresarSubmenu);
        escenaAccion = new Scene(layoutAccion, 450, 400);

        btnRegresarSubmenu.setOnAction(e -> ventana.setScene(escenaSubmenuGestion));

        //menu de gestion de instrumentos
        VBox layoutSubmenu = new VBox(10);
        Label lblSubmenu = new Label("--- GESTIONAR INSTRUMENTOS ---");
        Button btnBuscarAutor = new Button("Buscar por Autor");
        Button btnBuscarTipo = new Button("Buscar por Tipo");
        Button btnBuscarForma = new Button("Buscar por Forma");
        Button btnBuscarCondicion = new Button("Buscar por Condición");
        Button btnBuscarEvaluacion = new Button("Buscar por Evaluación de Validez");
        Button btnOrdenarClave = new Button("Mostrar ordenados por Clave");
        Button btnOrdenarAutor = new Button("Mostrar ordenados por Primer Autor");
        Button btnEliminar = new Button("Eliminar instrumento por Clave");
        Button btnMostrarTodos = new Button("Mostrar todos");
        Button btnRegresarMain = new Button("Regresar al Menú Principal");

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
                    "¿Buscar instrumentos evaluados? ('si' o 'no'):", true);

            btnEjecutar.setOnAction(ev -> {
                listaResultados.getItems().clear();
                boolean esEvaluado = txtInput.getText().equalsIgnoreCase("si");
                //si se pone algo que no sea si, se va a buscar instrumentos no evaluados
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
        escenaSubmenuGestion = new Scene(layoutSubmenu, 350, 450);
        //accion de botones del menu principal
        btnIrRegistro.setOnAction(e -> ventana.setScene(escenaRegistro));
        btnIrGestion.setOnAction(e -> ventana.setScene(escenaSubmenuGestion));
        btnSalir.setOnAction(e -> ventana.close());


        ventana.setScene(escenaMenu);
        ventana.show();
    }

    //esto es para no ocupar reescribir el layout de gestion de instrumentos por cada opcion
    private void prepararPantallaAccion(Label lbl, TextField txt, Button btn, ListView<String> lista,
                                        String textoInstruccion, boolean requiereInput) {
        lbl.setText(textoInstruccion);
        txt.clear();
        lista.getItems().clear();

        //si no se ocupa escribir se esconden las cajas de texto y boton
        txt.setVisible(requiereInput);
        txt.setManaged(requiereInput);
        btn.setVisible(requiereInput);
        btn.setManaged(requiereInput);
    }
}
