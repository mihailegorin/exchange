package project.exchange.api.util;

import java.util.HashMap;
import java.util.Map;

public class ExchangesConvertor {

    public static Map<String, Double> calculateCurrencyExchangeRates(Map<String, Double> eurExchangeRates, String targetCurrency) {
        final var targetExchangeRates = new HashMap<String, Double>();

        for (Map.Entry<String, Double> entry : eurExchangeRates.entrySet()) {
            String currency = entry.getKey();
            double rate = entry.getValue();

            if (currency.equals(targetCurrency)) {
                continue;
            }

            double targetRate = rate / eurExchangeRates.get(targetCurrency);
            targetExchangeRates.put(currency, Math.round(targetRate * 1e6) / 1e6);
        }

        return targetExchangeRates;
    }

}
