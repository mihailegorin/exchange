package project.exchange.api.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.exchange.api.model.CurrencyExchangeRates;
import project.exchange.api.model.CurrencyListResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/api/exchange")
public interface ExchangeApi {

    @PostMapping(
            value = "/currency", produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity<Void> addNewCurrency(
            @RequestParam(name = "currency") String currency
    );

    @GetMapping(
            value = "/currency", produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity<CurrencyListResponse> getListOfCurrencies();

    @GetMapping(
            value = "/exchange-rates", produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity<CurrencyExchangeRates> getExchangeRatesForCurrency(
            @RequestParam(name = "currency") String currency
    );

}
