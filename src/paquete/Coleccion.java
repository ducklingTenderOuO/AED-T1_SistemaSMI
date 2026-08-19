package paquete;

import java.util.ArrayList;
import java.util.List;

public class Coleccion {

    //ArrayList<Instrumento> instrumentos = new ArrayList<>();
    Instrumento[] instrumentos;
    int cantidad;

    public Coleccion(){
        instrumentos= new Instrumento[4];
        cantidad=0;
    }

    public Coleccion(int cantidad){
        instrumentos= new Instrumento[4];
        this.cantidad=cantidad;
    }

    private void aumentarDimensionArreglo(){
        int extra=0;
        extra= (int)(instrumentos.length*.25);
        cantidad= cantidad+extra;

        Instrumento[] listaSecundaria;
        for(int i=0; i< instrumentos.length; i++){
            //listaSecundaria= instrumentos[i];
        }
    }

    public List<Instrumento> buscarAutor(String autor){
        List<Instrumento> listaAutores= new ArrayList<>();
        for(int i=0; i<cantidad; i++){
            if(instrumentos[i].getAutor().equalsIgnoreCase(autor)){
                listaAutores.add(instrumentos[i]);
            }
        }
        return listaAutores;
    }

    public List<Instrumento> ordenarTipo(String t){
        List<Instrumento> listaTipo= new ArrayList<>();
        for(int i=0; i<cantidad; i++){
            if (instrumentos[i].getTipo().toString().equalsIgnoreCase(t)){
                listaTipo.add(instrumentos[i]);
            }
        }
        return listaTipo;
    }

    public List<Instrumento> ordenarForma(String f){
        List<Instrumento> listaForma= new ArrayList<>();
        for(int i=0; i<cantidad; i++){
            if (instrumentos[i].getForma().toString().equalsIgnoreCase(f)){
                listaForma.add(instrumentos[i]);
            }
        }
        return listaForma;
    }

    public List<Instrumento> ordenarCondicion(String c){
        List<Instrumento> listaCondicion= new ArrayList<>();
        for(int i=0; i<cantidad; i++){
            if (instrumentos[i].getCondicion().toString().equalsIgnoreCase(c)){
                listaCondicion.add(instrumentos[i]);
            }
        }
        return listaCondicion;
    }

    public List<Instrumento> ordenarEvaluacion(String e){
        List<Instrumento> listaEvaluacion= new ArrayList<>();
        for(int i=0; i<cantidad; i++){
            if (instrumentos[i].getEvaluacion().toString().equalsIgnoreCase(e)){
                listaEvaluacion.add(instrumentos[i]);
            }
        }
        return listaEvaluacion;
    }

    /*
    public List<Instrumento> ordenarClave(){
        return instrumentos.stream()
                .sorted( (inst1,inst2) -> Integer.compare(inst1.getClave(), inst2.getClave()) )
                .toList();
    }

    public List<Instrumento> ordenarAutor(){
        return instrumentos.stream()
                .sorted( (inst1,inst2) -> inst1.getAutor().compareToIgnoreCase(inst2.getAutor()))
                .toList();
    }
    */

    public void eliminarInstrumento(String nombre){
        int indice=0;
        boolean encontrado=false;

        for(int i=0; i<cantidad; i++){
            if(instrumentos[i].getNombreInstrumento().equalsIgnoreCase(nombre)){
                indice= i;
                encontrado= true;
                System.out.println("Instrumento encontrado y removido");
            }
        }
        //recorrer inst
        if(encontrado==true){
            for(int i=0; i<cantidad; i++){
                if(instrumentos[i]==null){
                    if (instrumentos[i+1]!=null) {
                        instrumentos[i]= instrumentos[i+1];
                        instrumentos[i+1]= null;
                    }
                }
            }

        }else{
            System.out.println("No se encontró el instrumendo ingresado");
        }
    }

    public Instrumento agregarInstrumento(Instrumento inst){
        if(instrumentos.length == cantidad){
            aumentarDimensionArreglo();
        }
        instrumentos[cantidad]= inst;
        cantidad++;
        return inst;
    }

}
