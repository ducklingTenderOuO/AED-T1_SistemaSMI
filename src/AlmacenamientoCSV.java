import java.nio.file.*;
import java.util.*;
import java.io.IOException;

public class AlmacenamientoCSV {

    public static void guardarCSV(String archivo, List<Instrumento> instrumentos)
            throws IOException {
        List<String> lineas = new ArrayList<>();

        // Cabecera
        lineas.add("Nombre,Forma,Tipo,Condicion,Autores,Evaluado,Cita");

        // Datos
        for (Instrumento inst : instrumentos) {
            String autoresStr = String.join(";", inst.getAutores());
            lineas.add(String.format("%s,%s,%s,%s,%s,%b,%s",
                    inst.getNombre(),
                    inst.getForma(),
                    inst.getTipo(),
                    inst.getCondicion(),
                    autoresStr,
                    inst.isEvaluado(),
                    inst.getCita()
            ));
        }

        // Escribir todo de una vez
        Files.write(Paths.get(archivo), lineas);
    }

    public static List<Instrumento> cargarCSV(String archivo)
            throws IOException {
        List<Instrumento> instrumentos = new ArrayList<>();

        // Leer todas las líneas
        List<String> lineas = Files.readAllLines(Paths.get(archivo));

        // Saltar cabecera
        for (int i = 1; i < lineas.size(); i++) {
            String[] campos = lineas.get(i).split(",");
            if (campos.length >= 7) {
                String nombre = campos[0];
                String forma = campos[1];
                String tipo = campos[2];
                String condicion = campos[3];
                List<String> autores = new ArrayList<>();
                if (!campos[4].isEmpty()) {
                    autores = Arrays.asList(campos[4].split(";"));
                    autores.replaceAll(String::trim);
                }
                boolean evaluado = Boolean.parseBoolean(campos[5]);
                String cita = campos[6];

                instrumentos.add(new Instrumento(
                        nombre, forma, tipo, condicion,
                        autores, evaluado, cita
                ));
            }
        }
        return instrumentos;
    }
}