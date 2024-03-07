package project.exchange.client.service;

import project.exchange.client.api.ExchangeRatesClient;
import project.exchange.client.model.ExchangeRatesResponse;
import project.exchange.client.model.SupportedSymbolsResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExchangeRatesService {

    private final ExchangeRatesClient exchangeRatesApiClient;


    public ExchangeRatesResponse getLatestExchangeRates(List<String> currencies) {
        var currenciesSymbols = String.join(", ", currencies);
        return exchangeRatesApiClient.getLatestExchangeRates(currenciesSymbols);
    }

    public SupportedSymbolsResponse getSupportedSymbols() {
        return exchangeRatesApiClient.getSupportedSymbols();
    }

}
