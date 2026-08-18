import java.util.*;

class SistemaSMI {
    private List<Instrumento> instrumentos;
    private Map<String, Instrumento> mapaPorNombre;

    public SistemaSMI() {
        this.instrumentos = new ArrayList<>();
        this.mapaPorNombre = new HashMap<>();
    }

    public boolean agregarInstrumento(Instrumento inst) {
        if (mapaPorNombre.containsKey(inst.getNombre().toLowerCase())) {
            return false;
        }
        instrumentos.add(inst);
        mapaPorNombre.put(inst.getNombre().toLowerCase(), inst);
        return true;
    }

    public void cargarInstrumentos(List<Instrumento> lista) {
        for (Instrumento inst : lista) {
            if (!mapaPorNombre.containsKey(inst.getNombre().toLowerCase())) {
                instrumentos.add(inst);
                mapaPorNombre.put(inst.getNombre().toLowerCase(), inst);
            }
        }
    }

    public List<Instrumento> buscarPorAutor(String autor) {
        List<Instrumento> resultado = new ArrayList<>();
        for (Instrumento inst : instrumentos) {
            for (String a : inst.getAutores()) {
                if (a.toLowerCase().contains(autor.toLowerCase())) {
                    resultado.add(inst);
                    break;
                }
            }
        }
        return resultado;
    }

    public List<Instrumento> buscarPorTipo(String tipo) {
        List<Instrumento> resultado = new ArrayList<>();
        for (Instrumento inst : instrumentos) {
            if (inst.getTipo().toLowerCase().contains(tipo.toLowerCase())) {
                resultado.add(inst);
            }
        }
        return resultado;
    }

    public List<Instrumento> buscarPorForma(String forma) {
        List<Instrumento> resultado = new ArrayList<>();
        for (Instrumento inst : instrumentos) {
            if (inst.getForma().toLowerCase().equals(forma.toLowerCase())) {
                resultado.add(inst);
            }
        }
        return resultado;
    }

    public List<Instrumento> buscarPorCondicion(String condicion) {
        List<Instrumento> resultado = new ArrayList<>();
        for (Instrumento inst : instrumentos) {
            if (inst.getCondicion().toLowerCase().contains(condicion.toLowerCase())) {
                resultado.add(inst);
            }
        }
        return resultado;
    }

    public List<Instrumento> buscarPorEvaluacion(boolean evaluado) {
        List<Instrumento> resultado = new ArrayList<>();
        for (Instrumento inst : instrumentos) {
            if (inst.isEvaluado() == evaluado) {
                resultado.add(inst);
            }
        }
        return resultado;
    }

    public List<Instrumento> ordenarPorClave() {
        List<Instrumento> copia = new ArrayList<>(instrumentos);
        copia.sort(Comparator.comparing(Instrumento::getNombre));
        return copia;
    }

    public List<Instrumento> ordenarPorPrimerAutor() {
        List<Instrumento> copia = new ArrayList<>(instrumentos);
        copia.sort(Comparator.comparing(Instrumento::getPrimerAutor));
        return copia;
    }

    public boolean eliminarPorClave(String nombre) {
        Instrumento removido = mapaPorNombre.remove(nombre.toLowerCase());
        if (removido != null) {
            return instrumentos.remove(removido);
        }
        return false;
    }

    public List<Instrumento> obtenerTodos() {
        return new ArrayList<>(instrumentos);
    }

    public int cantidad() {
        return instrumentos.size();
    }
}