package project.exchange.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.exchange.core.model.ExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

}
