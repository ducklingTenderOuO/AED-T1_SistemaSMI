package servicio;
import java.util.ArrayList;

import modelo.Instrumento;

public class Gestionar {

   private ArrayList<Instrumento> instrumentos; 


   public Gestionar(ArrayList<Instrumento> instrumentos){
    this.instrumentos=instrumentos;
   }

   public Gestionar(){
    instrumentos=new ArrayList<>();
   }
   
   //buscar por autor
   public ArrayList<Instrumento> buscarAutor(String authors){

    return new ArrayList<>(instrumentos.stream()
        .filter(n->n.getAuthors().contains(authors))
        .toList());
   }


   //buscar por tipo
   public ArrayList<Instrumento> buscarTipo(String type){

    return new ArrayList<>(instrumentos.stream()
        .filter(n->n.getType().equals(type))
        .toList());
    }

   // buscar por forma
    public ArrayList<Instrumento> buscarForma(String form){

        return new ArrayList<>(instrumentos.stream()
            .filter(n->n.getForm().equals(form))
            .toList()
            
        );
    }

   //buscar por condicion
    public ArrayList<Instrumento> searchCondition(String condition){
        ArrayList<Instrumento> resultado = new ArrayList<>(
        instrumentos.stream().filter(a->a.getCondition().equals(condition)).toList()
        );
        return resultado;
    }

    //buscar por evaluated
    public ArrayList<Instrumento> searchEvaluated(boolean evaluated){


        return new ArrayList<>(instrumentos.stream()
        .filter(n->n.getEvaluated() == evaluated)
        .toList()
    
        );


    }

    public boolean removeInstrument(int code) {

        boolean eliminado = instrumentos
        .removeIf(instrumento -> instrumento.getCode() == code);
        return eliminado;
    }

    //hacer un metodo para aumentar la dimension del arreglo si se llego al limite de elementos almacenados
    //la cantidad a aumentar es un 25% de la capacidad
   
    
}
