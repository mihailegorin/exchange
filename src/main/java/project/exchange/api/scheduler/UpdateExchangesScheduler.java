package project.exchange.api.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.exchange.api.service.ExchangeService;

@Service
@RequiredArgsConstructor
public class UpdateExchangesScheduler {

    private final ExchangeService exchangeRatesService;

    @Scheduled(fixedRateString = "${app.schedule.exchange}")
    public void hourlyExchangesUpdate() {
        if (exchangeRatesService.getListOfCurrencies().isEmpty()) {
            return;
        }
        exchangeRatesService.updateExchangeRates();
    }

}
