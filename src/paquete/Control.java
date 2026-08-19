package paquete;

import java.util.List;
import java.util.Scanner;

public class Control {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Scanner numeros = new Scanner(System.in);
        Coleccion coleccion= new Coleccion();
        int opcion = 0;

        //agregados manualmente para pruebas
        coleccion.agregarInstrumento(new Instrumento("inst1","autor1","cita1", 123,Instrumento.forma.CUESTIONARIO,
                Instrumento.tipo.IDENTIFICAR,Instrumento.condicion.ANSIEDAD_Y_ESTRES, Instrumento.evaluacion.VALIDEZ_Y_CONFIABILIDAD));

        do {
            System.out.println();
            System.out.println("*** MENU DE OPCIONES ***");
            System.out.println("[1] Buscar por autor");
            System.out.println("[2] Ordenar por tipo");
            System.out.println("[3] Ordenar por forma");
            System.out.println("[4] Ordenar por condición");
            System.out.println("[5] Ordenar por evaluación");
            System.out.println("[6] Ordenar por clave");
            System.out.println("[7] Ordenar por autor");
            System.out.println("[8] Eliminar instrumento");
            System.out.println("[9] Agregar instrumento");
            System.out.println("[10] Salir del programa");
            System.out.print("==> ");
            opcion = numeros.nextInt();

            if (opcion == 1) {
                System.out.println("Escribe el nombre del autor para mostrar sus instrumentos");
                String autor = sc.next();
                List<Instrumento> listaAutor = coleccion.buscarAutor(autor);
                for (Instrumento i : listaAutor) {
                    System.out.println(i);
                }

            } else if (opcion == 2) {
                System.out.println("Escribe el tipo de instrumento (manejar, identificar)");
                String tipoInstrumento = sc.next();
                List<Instrumento> listaTipo = coleccion.ordenarTipo(tipoInstrumento);
                for (Instrumento i : listaTipo) {
                    System.out.println(i);
                }

            } else if (opcion == 3) {
                System.out.println("Escribe la forma (test, cuestionario, escala)");
                String formaInstrumento = sc.next();
                List<Instrumento> listaForma = coleccion.ordenarTipo(formaInstrumento);
                for (Instrumento i : listaForma) {
                    System.out.println(i);
                }

            } else if (opcion == 4) {
                System.out.println("Escribe la condición (ansiedad, estres, ansiedad y estres)");
                String condicionInstrumento = sc.next();
                List<Instrumento> listaCondicion = coleccion.ordenarTipo(condicionInstrumento);
                for (Instrumento i : listaCondicion) {
                    System.out.println(i);
                }

            } else if (opcion == 5) {
                System.out.println("Escribe la evaluación (validez, confiabilidad, validez y confiabilidad)");
                String evaluacionInstrumento = sc.next();
                List<Instrumento> listaEvaluacion = coleccion.ordenarTipo(evaluacionInstrumento);
                for (Instrumento i : listaEvaluacion) {
                    System.out.println(i);
                }

            } else if (opcion == 6) {/*
                List<Instrumento> listaClave = coleccion.ordenarClave();
                for (Instrumento i : listaClave) {
                    System.out.println(i);
                }*/

            } else if (opcion == 7) {/*
                List<Instrumento> listaAutores = coleccion.ordenarAutor();
                for (Instrumento i : listaAutores) {
                    System.out.println(i);
                }*/

            } else if (opcion == 8) {
                System.out.print("Indique el nombre del instrumento que quiere eliminar: ");
                String instrumento = sc.next();
                coleccion.eliminarInstrumento(instrumento);

            } else if(opcion==9){
                System.out.print("Nombre instrumento: ");
                String nombre= sc.next();

                System.out.print("Autor: ");
                String autor= sc.next();

                System.out.print("Cita: ");
                String cita= sc.next();

                System.out.print("Clave: ");
                int clave= numeros.nextInt();

                System.out.print("Forma (test, cuestionario, escala): ");
                String f=sc.next();
                Instrumento.forma forma;
                if(f.equalsIgnoreCase("test")) {forma=Instrumento.forma.TEST;}
                else if(f.equals("cuestionario")) {forma=Instrumento.forma.CUESTIONARIO;}
                else{ forma= Instrumento.forma.ESCALA;}

                System.out.print("Tipo (manejar, identificar): ");
                String t=sc.next();
                Instrumento.tipo tipo;
                if(t.equalsIgnoreCase("manejar")) {tipo=Instrumento.tipo.MANEJAR;}
                else{ tipo= Instrumento.tipo.IDENTIFICAR;}

                System.out.print("Condición (estrés, ansiedad, estrés y ansiedad): ");
                String c=sc.next();
                Instrumento.condicion condicion;
                if(c.equalsIgnoreCase("estres")) {condicion=Instrumento.condicion.ESTRES;}
                else if(f.equals("ansiedad")) {condicion=Instrumento.condicion.ANSIEDAD;}
                else{ condicion= Instrumento.condicion.ANSIEDAD_Y_ESTRES;}

                System.out.print("Condición (estrés, ansiedad, estrés y ansiedad):");
                String e=sc.next();
                Instrumento.evaluacion evaluacion;
                if(f.equalsIgnoreCase("validez")) {evaluacion=Instrumento.evaluacion.VALIDEZ;}
                else if(f.equals("cuestionario")) {evaluacion=Instrumento.evaluacion.CONFIABILIDAD;}
                else{ evaluacion= Instrumento.evaluacion.VALIDEZ_Y_CONFIABILIDAD;}

                Instrumento instrumento= new Instrumento(nombre, autor, cita, clave, forma, tipo, condicion, evaluacion);
                coleccion.agregarInstrumento(instrumento);

            }else if(opcion==10){
                System.out.println("Saliendo del programa...");
            }else{
                System.out.println("No seleccionaste una opción válida");
            }
        }while(opcion!=9);
    }
}
