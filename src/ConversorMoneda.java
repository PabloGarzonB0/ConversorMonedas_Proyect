import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConversorMoneda {
    private final HttpClient client;
    private final Gson gson;

    public ConversorMoneda(){
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public Moneda buscarMoneda(String monedaBase){
        String url = "https://v6.exchangerate-api.com/v6/bac1de5958d6c66c46aad8e9/latest/"+monedaBase;
        URI direccion = URI.create(url);

        HttpRequest request = HttpRequest.newBuilder().uri(direccion).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //STATUS HTTP
            if (response.statusCode() != 200){
                throw new RuntimeException("Error en la solicitud HTTP: Codigo "+ response.statusCode());
            }

            // De-serializado JSON
            Moneda moneda = gson.fromJson(response.body(), Moneda.class);

            if(moneda == null || moneda.conversion_rates() == null){
                throw new RuntimeException("Error al procesar los datos de la moneda");
            }
            return moneda;

        }catch (Exception e) {
            throw new RuntimeException("Error al buscar la moneda: " + e.getMessage(), e);
        }
    }

    public float convertirMoneda(Moneda monedaBase, String monedaDestino, String monedaOrigen, float monto){
        //Disponibilidad de monedas
        Map<String, Float> tasas = monedaBase.conversion_rates();
        if (!tasas.containsKey(monedaDestino)){
            throw new RuntimeException("La moneda destion "+ monedaDestino + " no se encuentra disponible. ");
        }
        if (!tasas.containsKey(monedaOrigen)){
            throw new RuntimeException("La moneda origen " + monedaOrigen + " no esta disponible.");
        }

        // Obtener la tasa de conversion indirecta usando la moneda base
        float tasaOrigen = tasas.get(monedaOrigen);
        float tasaDestino = tasas.get(monedaDestino);

        return ((tasaDestino/ tasaOrigen)* monto);
    }

    public List<String> obtenerMonedasDisponibles(Moneda monedaBase) {
        return new ArrayList<>(monedaBase.conversion_rates().keySet());
    }

}
