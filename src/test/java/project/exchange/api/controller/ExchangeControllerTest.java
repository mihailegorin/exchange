package project.exchange.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import project.exchange.api.model.CurrencyExchangeRates;
import project.exchange.api.service.ExchangeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ExchangeController.class)
public class ExchangeControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ExchangeService exchangeService;


    @Test
    public void addNewCurrency_success() throws Exception {
        mvc.perform(post("/api/exchange/currency?currency={currency}", "USD"))
                .andExpect(status().isOk());
    }

    @Test
    public void getListOfCurrencies_success() throws Exception {
        when(exchangeService.getListOfCurrencies())
                .thenReturn(List.of("USD", "JPY", "GBP"));

        mvc.perform(get("/api/exchange/currency"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currencies").isArray())
                .andExpect(jsonPath("$.currencies[0]").value("USD"))
                .andExpect(jsonPath("$.currencies[1]").value("JPY"))
                .andExpect(jsonPath("$.currencies[2]").value("GBP"));
    }

    @Test
    public void getExchangeRatesForCurrency_success() throws Exception {
        final var currency = "USD";

        Map<String, Double> exchangeRatesMap = new HashMap<>();
        exchangeRatesMap.put("JPY", 147.930279);

        when(exchangeService.getCurrencyExchangeRates(currency))
                .thenReturn(new CurrencyExchangeRates("USD", exchangeRatesMap));

        mvc.perform(get("/api/exchange/exchange-rates?currency={currency}", currency))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.baseCurrency").value("USD"))
                .andExpect(jsonPath("$.exchangeRates.JPY").value("147.930279"));
    }
}
