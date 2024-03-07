package project.exchange.client.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.exchange.client.api.ExchangeRatesClient;
import project.exchange.client.model.ExchangeRatesResponse;
import project.exchange.client.model.SupportedSymbolsResponse;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeRatesServiceTest {

    @Mock
    private ExchangeRatesClient exchangeRatesClient;
    @InjectMocks
    private ExchangeRatesService exchangeRatesService;

    @Test
    public void getLatestExchangeRates_success() {
        final var currencies = List.of("GBP", "JPY", "EUR", "AED");
        final var exchangeRatesResponse = new ExchangeRatesResponse(
                true,
                1709806563,
                "EUR",
                LocalDate.of(2024, 3, 6),
                new HashMap<>() {{
                    put("GBP", 0.854602);
                    put("JPY", 161.176283);
                    put("EUR", 1.0);
                    put("AED", 4.002114);
                }}
        );

        when(exchangeRatesClient.getLatestExchangeRates(String.join(", ", currencies)))
                .thenReturn(exchangeRatesResponse);

        final var result = exchangeRatesService.getLatestExchangeRates(currencies);
        assertEquals(exchangeRatesResponse, result);
    }

    @Test
    public void getSupportedSymbols_success() {
        final var supportedSymbolsResponse = new SupportedSymbolsResponse(
                true,
                new HashMap<>() {{
                    put("GBP", "British Pound Sterling");
                    put("JPY", "Japanese Yen");
                    put("EUR", "Euro");
                    put("AED", "United Arab Emirates Dirham");
                }}
        );

        when(exchangeRatesClient.getSupportedSymbols()).thenReturn(supportedSymbolsResponse);

        final var result = exchangeRatesService.getSupportedSymbols();
        assertEquals(supportedSymbolsResponse, result);
    }

}
