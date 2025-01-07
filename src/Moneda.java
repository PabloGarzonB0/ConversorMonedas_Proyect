import java.util.Map;

public record Moneda(String base_code, Map<String, Float> conversion_rates) {
}
