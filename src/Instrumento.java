public class Instrumento {
    private String clave, nombre, forma, tipo, condicion, autor, cita_evaluacion;
    private boolean evaluacion;

    public Instrumento(String clave, String nombre, String forma, String tipo, String condicion, String autor, String cita_evaluacion, boolean evaluacion) {
        this.clave = clave;
        this.nombre = nombre;
        this.forma = forma;
        this.tipo = tipo;
        this.condicion = condicion;
        this.autor = autor;
        this.cita_evaluacion = cita_evaluacion;
        this.evaluacion = evaluacion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCita_evaluacion() {
        return cita_evaluacion;
    }

    public void setCita_evaluacion(String cita_evaluacion) {
        this.cita_evaluacion = cita_evaluacion;
    }

    public boolean isEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(boolean evaluacion) {
        this.evaluacion = evaluacion;
    }

    @Override
    public String toString() {
        return "Clave: " + clave + " | Nombre: " + nombre + " | Autor: " + autor +
                "\nForma: " + forma + " | Tipo: " + tipo + " | Condición: " + condicion +
                "\nEvaluado: " + (evaluacion ? "Sí (" + cita_evaluacion + ")" : "No") + "\n";
    }
}