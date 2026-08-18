import java.util.ArrayList;
import java.util.List;

class Instrumento {
    private String nombre;
    private String forma;
    private String tipo;
    private String condicion;
    private List<String> autores;
    private boolean evaluado;
    private String cita;

    public Instrumento(String nombre, String forma, String tipo, String condicion,
                       List<String> autores, boolean evaluado, String cita) {
        this.nombre = nombre;
        this.forma = forma;
        this.tipo = tipo;
        this.condicion = condicion;
        this.autores = new ArrayList<>(autores);
        this.evaluado = evaluado;
        this.cita = cita;
    }

    public String getNombre() { return nombre; }
    public String getForma() { return forma; }
    public String getTipo() { return tipo; }
    public String getCondicion() { return condicion; }
    public List<String> getAutores() { return autores; }
    public String getPrimerAutor() { return autores.isEmpty() ? "" : autores.get(0); }
    public boolean isEvaluado() { return evaluado; }
    public String getCita() { return cita; }
    public void setEvaluado(boolean evaluado) { this.evaluado = evaluado; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | Autores: %s | Evaluado: %s | %s",
                nombre, forma, tipo, condicion, String.join(", ", autores),
                evaluado ? "Si" : "No", cita);
    }
}