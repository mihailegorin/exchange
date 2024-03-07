package project.exchange.client.model;

import java.util.Map;

public record SupportedSymbolsResponse(
        boolean success,
        Map<String, String> symbols) {
}
