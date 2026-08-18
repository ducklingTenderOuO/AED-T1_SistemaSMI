import java.util.Scanner;

public class Sistema {
    Instrumento[] inventario = new Instrumento[10];
    int contador = 0;
    Scanner leer = new Scanner(System.in);

    public static void main(String[] args){
        Sistema miPrograma = new Sistema();
        miPrograma.iniciarMenu();
    }

    public void iniciarMenu(){
        int opcion = 0;

        do{
            System.out.println("\nSistema APA");
            System.out.println("1. Registrar nuevo instrumento");
            System.out.println("2. Consultar instrumentos");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");

            opcion = Integer.parseInt(leer.nextLine());

            switch (opcion) {
                case 1:
                    registrarInstrumento();
                    break;
                case 2:
                    menuConsultas();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);
    }

    public void registrarInstrumento() {
        if (contador >= inventario.length){
            aumentarCapacidad();
        }

        System.out.println("\nRegistrar Instrumento");

        System.out.print("Clave: ");
        String clave = leer.nextLine();

        System.out.print("Nombre: ");
        String nombre = leer.nextLine();

        System.out.print("Forma: ");
        String forma = leer.nextLine();

        System.out.print("Tipo: ");
        String tipo = leer.nextLine();

        System.out.print("Condición: ");
        String condicion = leer.nextLine();

        System.out.print("Autor: ");
        String autor = leer.nextLine();

        System.out.print("Tiene evaluación de validez y confiabilidad?: ");
        String respEval = leer.nextLine();
        boolean evaluacion = respEval.equalsIgnoreCase("si");

        String cita = "";
        if (evaluacion) {
            System.out.print("Introduce la cita de evaluación: ");
            cita = leer.nextLine();
        }

        Instrumento nuevo = new Instrumento(clave, nombre, forma, tipo, condicion, autor, cita, evaluacion);
        inventario[contador] = nuevo;
        contador++;
        System.out.println("Instrumento registrado con éxito");

    }

    public void menuConsultas() {
        int opcionConsulta = 0;
        do {
            System.out.println("\nSubmenú de consultas");
            System.out.println("1. Por autor");
            System.out.println("2. Por tipo, identificar o manejar)");
            System.out.println("3. Regresar al menú principal");
            System.out.print("Elige una opción: ");

            try {
                opcionConsulta = Integer.parseInt(leer.nextLine());

                switch (opcionConsulta) {
                    case 1:
                        consultarPorAutor();
                        break;
                    case 2:
                        consultarPorTipo();
                        break;
                    case 3:
                        System.out.println("Regresando al menú principal.");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
            }
        } while (opcionConsulta != 3);
    }

    public void consultarPorAutor() {
        if(contador == 0){
            System.out.println("No hay instrumentos registrados.");
            return;
        }

        System.out.print("\nIntroduce el nombre del autor a buscar: ");
        String autorBuscado = leer.nextLine();
        boolean encontrado = false;

        System.out.println("\nResultados de Búsqueda");
        for (int i = 0; i < contador; i++) {
            if (inventario[i].getAutor().toLowerCase().contains((autorBuscado.toLowerCase()))) {
                System.out.println(inventario[i].toString());
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron instrumentos de ese autor.");
        }
    }

    public void consultarPorTipo() {
        if(contador == 0){
            System.out.println("No hay instrumentos registrados.");
            return;
        }

        System.out.print("\nIntroduce el tipo, identificar o manejar): ");
        String tipoBuscado = leer.nextLine();
        boolean encontrado = false;

        System.out.println("\nResultados de Búsqueda");
        for (int i = 0; i < contador; i++) {
            if (inventario[i].getTipo().equalsIgnoreCase(tipoBuscado)) {
                System.out.println(inventario[i].toString());
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron instrumentos de ese tipo.");
        }
    }

    public void aumentarCapacidad(){
        int capacidadActual = inventario.length;
        int aumento = (int) (capacidadActual * 0.25);
        if (aumento == 0){
            aumento = 1;
        }
        int nuevaCapacidad = capacidadActual + aumento;
        Instrumento[] nuevoArreglo = new Instrumento[nuevaCapacidad];

        for (int i = 0; i < contador; i++){
            nuevoArreglo[i] = inventario[i];
        }
        inventario = nuevoArreglo;

        System.out.println("Se aumento la capacidad en: " + nuevaCapacidad);
    }

    public String eliminarElemento()

}