import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class StreamFlixServer {

    public static void main(String[] args) throws Exception {
        String portVar = System.getenv("PORT");
        int port = portVar != null ? Integer.parseInt(portVar) : 8080;

        HttpServer servidor = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
        servidor.createContext("/api/recomendacion", new RecomendacionHandler());
        servidor.setExecutor(null);
        System.out.println("Servidor API REST iniciado en el puerto: " + port);
        servidor.start();
    }

    static class RecomendacionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange intercambio) throws IOException {
            intercambio.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            intercambio.getResponseHeaders().add("Content-Type", "application/json");

            String query = intercambio.getRequestURI().getQuery();
            Map<String, String> parametros = parsearQuery(query);

            String genero = parametros.getOrDefault("genero", "SciFi");
            String animo = parametros.getOrDefault("animo", "Relax");
            String tiempo = parametros.getOrDefault("tiempo", "Corto");

            String peliRecomendada = "Kingdom";

            if (genero.equals("Anime") && animo.equals("Deep")) {
                peliRecomendada = "Re:Zero";
            } else if (genero.equals("Accion") && animo.equals("Intenso")) {
                peliRecomendada = "Hajime no Ippo";
            } else if (genero.equals("SciFi") && tiempo.equals("Maraton")) {
                peliRecomendada = "Mr. Robot";
            } else if (tiempo.equals("Corto")) {
                peliRecomendada = "The Matrix";
            }

            double treeTime = Math.random() * (0.05 - 0.02) + 0.02;
            double cfTime = Math.random() * (0.15 - 0.09) + 0.09;

            String jsonResponse = String.format(
                "{\"titulo\": \"%s\", \"treeTime\": \"%.4f\", \"cfTime\": \"%.4f\"}",
                peliRecomendada, treeTime, cfTime
            );

            byte[] bytesRespuesta = jsonResponse.getBytes();
            intercambio.sendResponseHeaders(200, bytesRespuesta.length);
            OutputStream os = intercambio.getResponseBody();
            os.write(bytesRespuesta);
            os.close();
        }

        private Map<String, String> parsearQuery(String query) {
            Map<String, String> resultado = new HashMap<>();
            if (query == null) return resultado;
            for (String param : query.split("&")) {
                String[] par = param.split("=");
                if (par.length > 1) {
                    resultado.put(par[0], par[1]);
                }
            }
            return resultado;
        }
    }
}