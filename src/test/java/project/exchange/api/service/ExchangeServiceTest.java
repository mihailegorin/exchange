package project.exchange.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.exchange.client.model.ExchangeRatesResponse;
import project.exchange.client.model.SupportedSymbolsResponse;
import project.exchange.client.service.ExchangeRatesService;
import project.exchange.core.repository.ExchangeRateRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {

    @Mock
    private ExchangeRatesService exchangeRatesService;
    @Mock
    private ExchangeRateRepository exchangeRateRepository;
    @InjectMocks
    private ExchangeService exchangeService;

    @Test
    public void addNewCurrency_success() {
        final var currency = "AED";
        final var supportedSymbolsResponse = new SupportedSymbolsResponse(
                true,
                new HashMap<>() {{
                    put("GBP", "British Pound Sterling");
                    put("JPY", "Japanese Yen");
                    put("EUR", "Euro");
                    put("AED", "United Arab Emirates Dirham");
                }}
        );

        when(exchangeRatesService.getSupportedSymbols()).thenReturn(supportedSymbolsResponse);

        final var exchangeRatesResponse = new ExchangeRatesResponse(
                true,
                1709806563,
                "EUR",
                LocalDate.of(2024, 3, 6),
                new HashMap<>() {{
                    put("AED", 4.002114);
                }}
        );

        when(exchangeRatesService.getLatestExchangeRates(List.of(currency)))
                .thenReturn(exchangeRatesResponse);

        exchangeService.addNewCurrency(currency);

        verify(exchangeRateRepository, times(1)).saveAll(any());
    }

    @Test
    public void addNewCurrency_unsupportedCurrency() {
        final var currency = "AED";
        final var supportedSymbolsResponse = new SupportedSymbolsResponse(
                true,
                new HashMap<>() {{
                    put("GBP", "British Pound Sterling");
                    put("JPY", "Japanese Yen");
                    put("EUR", "Euro");
                }}
        );

        when(exchangeRatesService.getSupportedSymbols()).thenReturn(supportedSymbolsResponse);

        final var thrown = assertThrows(IllegalArgumentException.class, () -> exchangeService.addNewCurrency(currency));
        assertEquals("Unsupported currency: AED", thrown.getMessage());

        verify(exchangeRateRepository, times(0)).saveAll(any());
    }

    @Test
    public void getCurrencyExchangeRates_absentCurrency() {
        final var currency = "USD";
        final var thrown = assertThrows(IllegalArgumentException.class, () -> exchangeService.getCurrencyExchangeRates(currency));
        assertEquals("Currency is absent", thrown.getMessage());
    }

}
