package project.exchange.client.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeRatesClientConfig {

    @Value("${app.feign-api-keys.exchange}")
    private String accessKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.query("access_key", accessKey);
    }

}
