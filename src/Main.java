import java.util.*;
import java.io.*;

public class Main {
    private SistemaSMI gestor;
    private Scanner scanner;

    public Main() {
        this.gestor = new SistemaSMI();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMain() {
        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE INSTRUMENTOS PSICOLOGICOS ===");
            System.out.println("1. Agregar instrumento");
            System.out.println("2. Buscar por autor");
            System.out.println("3. Buscar por tipo (identificar/manejar)");
            System.out.println("4. Buscar por forma (test/escala/cuestionario)");
            System.out.println("5. Buscar por condicion (ansiedad/estres)");
            System.out.println("6. Buscar por evaluacion de validez");
            System.out.println("7. Mostrar todos ordenados por clave");
            System.out.println("8. Mostrar todos ordenados por primer autor");
            System.out.println("9. Eliminar instrumento por clave");
            System.out.println("10. Guardar en CSV");
            System.out.println("11. Cargar desde CSV");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch(opcion) {
                case 1: agregarInstrumento(); break;
                case 2: buscarPorAutor(); break;
                case 3: buscarPorTipo(); break;
                case 4: buscarPorForma(); break;
                case 5: buscarPorCondicion(); break;
                case 6: buscarPorEvaluacion(); break;
                case 7: mostrarOrdenadosPorClave(); break;
                case 8: mostrarOrdenadosPorAutor(); break;
                case 9: eliminarInstrumento(); break;
                case 10: guardarCSV(); break;
                case 11: cargarCSV(); break;
                case 0: System.out.println("Hasta luego!"); break;
                default: System.out.println("Opcion no valida");
            }
        } while(opcion != 0);
    }

    private void agregarInstrumento() {
        System.out.println("\n--- AGREGAR NUEVO INSTRUMENTO ---");

        System.out.print("Nombre (clave): ");
        String nombre = scanner.nextLine();

        System.out.print("Forma (test/escala/cuestionario): ");
        String forma = scanner.nextLine();

        System.out.print("Tipo (identificar/manejar/ambos): ");
        String tipo = scanner.nextLine();

        System.out.print("Condicion (ansiedad/estres/ambos): ");
        String condicion = scanner.nextLine();

        System.out.print("Autores (separados por coma): ");
        String autoresInput = scanner.nextLine();
        List<String> autores = Arrays.asList(autoresInput.split(","));
        autores.replaceAll(String::trim);

        System.out.print("Tiene evaluacion de validez? (true/false): ");
        boolean evaluado = scanner.nextBoolean();
        scanner.nextLine();

        System.out.print("Cita bibliografica: ");
        String cita = scanner.nextLine();

        Instrumento inst = new Instrumento(nombre, forma, tipo, condicion, autores, evaluado, cita);
        if (gestor.agregarInstrumento(inst)) {
            System.out.println("Instrumento agregado exitosamente.");
        } else {
            System.out.println("Error: Ya existe un instrumento con ese nombre.");
        }
    }

    private void buscarPorAutor() {
        System.out.print("Ingrese nombre del autor: ");
        String autor = scanner.nextLine();
        List<Instrumento> resultados = gestor.buscarPorAutor(autor);
        mostrarResultados(resultados, "instrumentos de " + autor);
    }

    private void buscarPorTipo() {
        System.out.print("Ingrese tipo (identificar/manejar): ");
        String tipo = scanner.nextLine();
        List<Instrumento> resultados = gestor.buscarPorTipo(tipo);
        mostrarResultados(resultados, "instrumentos de tipo " + tipo);
    }

    private void buscarPorForma() {
        System.out.print("Ingrese forma (test/escala/cuestionario): ");
        String forma = scanner.nextLine();
        List<Instrumento> resultados = gestor.buscarPorForma(forma);
        mostrarResultados(resultados, "instrumentos de forma " + forma);
    }

    private void buscarPorCondicion() {
        System.out.print("Ingrese condicion (ansiedad/estres): ");
        String condicion = scanner.nextLine();
        List<Instrumento> resultados = gestor.buscarPorCondicion(condicion);
        mostrarResultados(resultados, "instrumentos sobre " + condicion);
    }

    private void buscarPorEvaluacion() {
        System.out.print("Buscar evaluados? (true/false): ");
        boolean evaluado = scanner.nextBoolean();
        scanner.nextLine();
        List<Instrumento> resultados = gestor.buscarPorEvaluacion(evaluado);
        String mensaje = evaluado ? "instrumentos con evaluacion" : "instrumentos sin evaluacion";
        mostrarResultados(resultados, mensaje);
    }

    private void mostrarOrdenadosPorClave() {
        List<Instrumento> ordenados = gestor.ordenarPorClave();
        System.out.println("\n--- INSTRUMENTOS ORDENADOS POR CLAVE ---");
        for (Instrumento inst : ordenados) {
            System.out.println(inst);
        }
        System.out.println("Total: " + ordenados.size() + " instrumentos");
    }

    private void mostrarOrdenadosPorAutor() {
        List<Instrumento> ordenados = gestor.ordenarPorPrimerAutor();
        System.out.println("\n--- INSTRUMENTOS ORDENADOS POR PRIMER AUTOR ---");
        for (Instrumento inst : ordenados) {
            System.out.println(inst);
        }
        System.out.println("Total: " + ordenados.size() + " instrumentos");
    }

    private void eliminarInstrumento() {
        System.out.print("Ingrese la clave (nombre) del instrumento a eliminar: ");
        String clave = scanner.nextLine();
        if (gestor.eliminarPorClave(clave)) {
            System.out.println("Instrumento eliminado exitosamente.");
        } else {
            System.out.println("No se encontro un instrumento con esa clave.");
        }
    }

    private void guardarCSV() {
        System.out.print("Nombre del archivo CSV: ");
        String archivo = scanner.nextLine();
        if (!archivo.endsWith(".csv")) {
            archivo += ".csv";
        }
        try {
            AlmacenamientoCSV.guardarCSV(archivo, gestor.obtenerTodos());
            System.out.println("Datos guardados correctamente en " + archivo);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    private void cargarCSV() {
        System.out.print("Nombre del archivo CSV: ");
        String archivo = scanner.nextLine();
        if (!archivo.endsWith(".csv")) {
            archivo += ".csv";
        }
        try {
            List<Instrumento> cargados = AlmacenamientoCSV.cargarCSV(archivo);
            gestor.cargarInstrumentos(cargados);
            System.out.println("Datos cargados correctamente desde " + archivo);
            System.out.println("Total: " + cargados.size() + " instrumentos cargados");
        } catch (IOException e) {
            System.out.println("Error al cargar: " + e.getMessage());
        }
    }

    private void mostrarResultados(List<Instrumento> resultados, String descripcion) {
        System.out.println("\n--- RESULTADOS: " + descripcion.toUpperCase() + " ---");
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron instrumentos.");
        } else {
            for (Instrumento inst : resultados) {
                System.out.println(inst);
            }
            System.out.println("Total: " + resultados.size() + " instrumentos");
        }
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.mostrarMain();
    }
}