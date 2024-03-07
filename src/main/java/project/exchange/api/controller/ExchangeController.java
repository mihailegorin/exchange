package project.exchange.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.exchange.api.controller.api.ExchangeApi;
import project.exchange.api.model.CurrencyExchangeRates;
import project.exchange.api.model.CurrencyListResponse;
import project.exchange.api.service.ExchangeService;

@RestController
@RequiredArgsConstructor
public class ExchangeController implements ExchangeApi {

    private final ExchangeService exchangeRatesService;


    @Override
    public ResponseEntity<Void> addNewCurrency(String currency) {
        exchangeRatesService.addNewCurrency(currency);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CurrencyListResponse> getListOfCurrencies() {
        return ResponseEntity.ok().body(new CurrencyListResponse(exchangeRatesService.getListOfCurrencies()));
    }

    @Override
    public ResponseEntity<CurrencyExchangeRates> getExchangeRatesForCurrency(String currency) {
        return ResponseEntity.ok().body(exchangeRatesService.getCurrencyExchangeRates(currency));
    }

}
