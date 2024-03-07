package project.exchange.client.model;

import java.time.LocalDate;
import java.util.Map;

public record ExchangeRatesResponse(
        boolean success,
        long timestamp,
        String base,
        LocalDate date,
        Map<String, Double> rates) {
}
