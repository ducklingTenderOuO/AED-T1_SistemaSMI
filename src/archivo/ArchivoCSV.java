package archivo;
//import java.io.File;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

import modelo.Instrumento;

//import servicio.Instrumento;

public class ArchivoCSV {

    private String path;
    private String line;
    private String[] newLine;

    //function passArray
    //private String OneLine;
    private String[] pass;
    private String[] splitAuthors;

    

    public ArchivoCSV(String path){
        this.path=path;
    }

    //leer archivo completo
    public void readFile(){
    try(BufferedReader br = new BufferedReader(new FileReader(path))){
        
        while((line=br.readLine()) != null){
            newLine = line.split(",");
            imprimirTexto();
            System.out.println();
        }

    }catch(IOException e){
        System.out.println("Ocurrio un problema");
    }

    }

    //recorrer el texto y imprimir
    public void imprimirTexto(){
        for(int i=0; i<newLine.length;i++){
            System.out.println(newLine[i] + " , ");
        }
    }

    //pasar archivo csv a array
    //leer csv
    //pasar los datos a un arrar
    //hacer busquedas en ese array

    // code/name/form/type/condition/authors/evaluated
    ArrayList<Instrumento> instrumentos = new ArrayList<>();

    public ArrayList<Instrumento> passList(){

        //BufferedReader brSecond = new BufferedReader();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){

            br.readLine();

            // read the line complete
            // split para colocar en una posicion distinta en el array
            while((line = br.readLine()) != null){
               pass = line.split(",");
               //all document is String

               int code = Integer.parseInt(pass[0]);
                String name = pass[1];
                String form = pass[2];
                String type = pass[3];
                String condition = pass[4];
            
                //ciclo para autores
                //otro split
                ArrayList<String> authors = new ArrayList<>();
                splitAuthors = pass[5].split("\\|");

                for (String autores : splitAuthors) {
                    authors.add(autores);
                }
                
                boolean evaluated = Boolean.parseBoolean(pass[6]);
                

               Instrumento mInstrumento = new Instrumento(code, name, form, type, condition, authors, evaluated);

                //miInstrumento es un solo instrumento, ademas fuera  del while este deja de existir
                //por lo tanto olvide agregar mi instrumento al otro array que tengo arriba
                instrumentos.add(mInstrumento);
            }
            //return mInstrumento; error


        }catch(IOException e){
            System.out.println("Ocurrio un error");
        }

            //El return va de este lado
        return instrumentos;

    }


    



    
}
