package paquete;

public class Instrumento {

    private String nombreInstrumento;
    private String autor;
    private String cita;
    private forma forma;
    private tipo tipo;
    private condicion condicion;
    private evaluacion evaluacion;
    private int clave;

    public Instrumento(String n,String a,String cita,int clave,forma f,tipo t,condicion c,evaluacion e){
        nombreInstrumento= n;
        autor= a;
        this.cita= cita;
        forma= f;
        tipo= t;
        condicion= c;
        evaluacion= e;
        this.clave= clave;
    }

    enum forma{ TEST, CUESTIONARIO,ESCALA;}
    enum tipo{ MANEJAR, IDENTIFICAR;}
    enum condicion{ ANSIEDAD, ESTRES, ANSIEDAD_Y_ESTRES;}
    enum evaluacion{ VALIDEZ, CONFIABILIDAD, VALIDEZ_Y_CONFIABILIDAD;}

    public String getAutor(){return autor;}
    public String getNombreInstrumento(){return nombreInstrumento;}
    public String getCita(){return cita;}
    public String toString(){
        return "Instrumento: "+nombreInstrumento+" "+"/ Autor: "+autor+" "+"/ Cita: "+cita+" "+"/ Clave: "+clave+" "+
                "/ Forma: "+forma+" "+"/ Tipo: "+tipo+" "+"/ Evaluación: "+evaluacion+" "+"/ Condición: "+condicion;}
    public tipo getTipo(){return tipo;}
    public forma getForma(){return forma;}
    public condicion getCondicion(){return condicion;}
    public evaluacion getEvaluacion(){return evaluacion;}
    public int getClave(){return clave;
    }
}
