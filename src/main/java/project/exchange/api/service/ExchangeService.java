package project.exchange.api.service;

import project.exchange.api.model.CurrencyExchangeRates;
import lombok.RequiredArgsConstructor;
import project.exchange.api.util.ExchangesConvertor;
import org.springframework.stereotype.Service;
import project.exchange.client.service.ExchangeRatesService;
import project.exchange.core.model.ExchangeRate;
import project.exchange.core.repository.ExchangeRateRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRatesService exchangeRatesService;
    private final ExchangeRateRepository exchangeRateRepository;

    private final Map<String, Double> euroExchangeRates = new HashMap<>();
    private final List<String> currencies = new ArrayList<>();


    public void addNewCurrency(String currency) {
        final var response = exchangeRatesService.getSupportedSymbols();

        if (!response.symbols().containsKey(currency)) {
            throw new IllegalArgumentException("Unsupported currency: " + currency);
        }

        currencies.add(currency);
        updateExchangeRates();
    }

    public void updateExchangeRates() {
        final var response = exchangeRatesService.getLatestExchangeRates(currencies);

        euroExchangeRates.clear();
        euroExchangeRates.putAll(response.rates());

        System.out.println("Exchange rates are updated");

        // log exchange rates into DB
        final var exchangeRates = response.rates().entrySet().stream()
                .map(entry -> ExchangeRate.builder()
                        .timestamp(BigInteger.valueOf(response.timestamp()))
                        .baseCurrency(response.base())
                        .targetCurrency(entry.getKey())
                        .rate(BigDecimal.valueOf(entry.getValue()))
                        .build()
                ).toList();

        exchangeRateRepository.saveAll(exchangeRates);
    }

    public List<String> getListOfCurrencies() {
        return currencies;
    }

    public CurrencyExchangeRates getCurrencyExchangeRates(String currency) {
        if (!euroExchangeRates.containsKey(currency)) {
            throw new IllegalArgumentException("Currency is absent");
        }

        final var response = ExchangesConvertor.calculateCurrencyExchangeRates(euroExchangeRates, currency);

        return new CurrencyExchangeRates(currency, response);
    }

}
