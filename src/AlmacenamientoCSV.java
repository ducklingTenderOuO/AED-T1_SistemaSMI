import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class AlmacenamientoCSV {

    public static void guardarCSV(String archivo, List<Instrumento> instrumentos)
            throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("Nombre,Forma,Tipo,Condicion,Autores,Evaluado,Cita"); //head

            for (Instrumento inst : instrumentos) {
                String autoresStr = String.join(";", inst.getAutores());
                pw.printf("%s,%s,%s,%s,%s,%b,%s%n", // lineaaa
                        inst.getNombre(),
                        inst.getForma(),
                        inst.getTipo(),
                        inst.getCondicion(),
                        autoresStr,
                        inst.isEvaluado(),
                        inst.getCita()
                );
            }
        }
    }

    public static List<Instrumento> cargarCSV(String archivo)
            throws IOException {
        List<Instrumento> instrumentos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine();
            if (linea == null) {
                return instrumentos;
            }

            while ((linea = br.readLine()) != null) {
                String[] campos = parseCSVLine(linea);
                if (campos.length == 7) {
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
        }
        return instrumentos;
    }

    private static String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());

        return result.toArray(new String[0]);
    }
}