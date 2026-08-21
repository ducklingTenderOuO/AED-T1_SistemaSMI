import java.util.*;
import java.util.stream.Collectors;

public class SistemaSMI {
    private List<Instrumento> instrumentos;
    private Map<String, Instrumento> mapaPorNombre;
    private int size;

    public SistemaSMI() {
        this.instrumentos = new ArrayList<>();
        this.mapaPorNombre = new HashMap<>();
        this.size = 0;
    }

    public SistemaSMI(int capacidadInicial) {
        this.instrumentos = new ArrayList<>(capacidadInicial);
        this.mapaPorNombre = new HashMap<>();
        this.size = 0;
    }

    public boolean agregarInstrumento(Instrumento inst) {
        if (mapaPorNombre.containsKey(inst.getNombre().toLowerCase())) {
            return false;
        }
        instrumentos.add(inst);
        mapaPorNombre.put(inst.getNombre().toLowerCase(), inst);
        size++;
        return true;
    }

    public void cargarInstrumentos(List<Instrumento> lista) {
        for (Instrumento inst : lista) {
            if (!mapaPorNombre.containsKey(inst.getNombre().toLowerCase())) {
                instrumentos.add(inst);
                mapaPorNombre.put(inst.getNombre().toLowerCase(), inst);
                size++;
            }
        }
    }

    //buscar por autor
    public List<Instrumento> buscarPorAutor(String autor) {
        return instrumentos.stream()
                .filter(inst -> inst.getAutores().stream()
                        .anyMatch(a -> a.toLowerCase().contains(autor.toLowerCase())))
                .collect(Collectors.toList());
    }

    //buscar por tipo
    public List<Instrumento> buscarPorTipo(String tipo) {
        return instrumentos.stream()
                .filter(inst -> inst.getTipo().toLowerCase().contains(tipo.toLowerCase()))
                .collect(Collectors.toList());
    }

    //buscar por forma
    public List<Instrumento> buscarPorForma(String forma) {
        return instrumentos.stream()
                .filter(inst -> inst.getForma().toLowerCase().equals(forma.toLowerCase()))
                .collect(Collectors.toList());
    }

    //buscar por condicion
    public List<Instrumento> buscarPorCondicion(String condicion) {
        return instrumentos.stream()
                .filter(inst -> inst.getCondicion().toLowerCase().contains(condicion.toLowerCase()))
                .collect(Collectors.toList());
    }

    //buscar por evaluacion
    public List<Instrumento> buscarPorEvaluacion(boolean evaluado) {
        return instrumentos.stream()
                .filter(inst -> inst.isEvaluado() == evaluado)
                .collect(Collectors.toList());
    }

    //eliminar por clave
    public boolean eliminarPorClave(String nombre) {
        boolean eliminado = instrumentos.removeIf(inst ->
                inst.getNombre().equalsIgnoreCase(nombre)
        );
        if (eliminado) {
            mapaPorNombre.remove(nombre.toLowerCase());
            size--;
        }
        return eliminado;
    }

    //ordenar por clave
    public List<Instrumento> ordenarPorClave() {
        return instrumentos.stream()
                .sorted(Comparator.comparing(Instrumento::getNombre))
                .collect(Collectors.toList());
    }

    //ordenar por primer autor
    public List<Instrumento> ordenarPorPrimerAutor() {
        return instrumentos.stream()
                .sorted(Comparator.comparing(inst ->
                        inst.getAutores().isEmpty() ? "" : inst.getAutores().get(0)
                ))
                .collect(Collectors.toList());
    }

    public List<Instrumento> obtenerTodos() {
        return new ArrayList<>(instrumentos);
    }

    public int cantidad() {
        return size;
    }
}