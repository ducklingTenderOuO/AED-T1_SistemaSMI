import archivo.ArchivoCSV;
import modelo.Instrumento;
import servicio.Gestionar;


import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArchivoCSV ar = new ArchivoCSV( "C:/Users/danie/OneDrive/Escritorio/instrumentos (1).csv");
        ArrayList<Instrumento> instrumentos = ar.passList();
        Gestionar ge = new Gestionar(instrumentos);
       

        int option;

        do {

            System.out.println("1. Leer archivo");
            System.out.println("2. Por autor");
            System.out.println("3. Buscar por tipo");
            System.out.println("4. Buscar por forma");
            System.out.println("5. Buscar por condicion");
            System.out.println("6. Buscar por evaluacion");
            System.out.println("7. Agregar informacion a CSV");
            System.out.println("8. Eliminar datos de CSV");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            option = sc.nextInt();

            switch (option) {

                case 1:
                    ar.readFile();
                    break;

                case 2:
                sc.nextLine();

                    System.out.print("Autor: ");
                    String author = sc.nextLine();

                    ArrayList<Instrumento> resultado = ge.buscarAutor(author);

                    for (Instrumento instrumento : resultado) {
                        System.out.println(instrumento.getName());
                    }

                    break;
                case 3:
                    sc.nextLine();
                    System.out.println("Ingrese el tipo");

                    String type = sc.nextLine();
                    ArrayList<Instrumento> tipos = ge.buscarTipo(type);
                    for (Instrumento instrumento : tipos) {
                        System.out.println(instrumento.getName());
                    }
                    break;

                    case 4:
                        sc.nextLine();
                        System.out.println("Ingrese la forma");
                        String formm = sc.nextLine();

                        ArrayList<Instrumento> form = ge.buscarForma(formm);
                        for (Instrumento instrumento : form) {
                        System.out.println(instrumento.getName());
                    }
                    break;
                    case 5:
                        sc.nextLine();

                        System.out.print("Condicion a buscar: ");
                        String condition = sc.nextLine();

                        ArrayList<Instrumento> resultadoCondicion = ge.searchCondition(condition);

                        for (Instrumento instrumento : resultadoCondicion) {
                        System.out.println(instrumento.getName());
                    }

                    break;

                    case 6:
                        sc.nextLine();
                        System.out.println("Ingrese por evaluacion");
                        Boolean evaluated = sc.nextBoolean();

                        ArrayList<Instrumento> resultadoEvaluacion = ge.searchEvaluated(evaluated);

                        for (Instrumento instrumento : resultadoEvaluacion) {
                            System.out.println(instrumento.getName());
                        }


                    break;


                    case 7:
                        System.out.println("No implementado");

                    break;


                    case 8:
                        System.out.println("No implementado");

                    break;


                case 0:
                    System.out.println("");
                    break;

                default:
                    System.out.println("Opcion no valida");
                    break;
            }

        } while (option != 0);

        sc.close();
    }
}