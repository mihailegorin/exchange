package project.exchange.api.model;

import java.util.Map;

public record CurrencyExchangeRates(
        String baseCurrency,
        Map<String, Double> exchangeRates
) {
}
