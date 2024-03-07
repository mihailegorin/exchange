package project.exchange.client.api;

import project.exchange.client.config.ExchangeRatesClientConfig;
import project.exchange.client.model.ExchangeRatesResponse;
import project.exchange.client.model.SupportedSymbolsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "exchange", configuration = ExchangeRatesClientConfig.class
)
public interface ExchangeRatesClient {

    @GetMapping("latest")
    ExchangeRatesResponse getLatestExchangeRates(
            @RequestParam(value = "symbols") String symbols
    );

    @GetMapping("symbols")
    SupportedSymbolsResponse getSupportedSymbols();
}
