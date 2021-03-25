package com.mystocks.mystocks.client;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.mystocks.mystocks.domain.PricingService;

@Service
public class PricingClient implements PricingService {

    private final RestTemplate restTemplate;
    private final String endpoint;
    private final String apiKey;

    public PricingClient(RestTemplate restTemplate,
                         @Value("${client.endpoint}") String endpoint,
                         @Value("${client.apikey}") String apiKey) {
        this.restTemplate = restTemplate;
        this.endpoint = endpoint;
        this.apiKey = apiKey;
    }

    public BigDecimal getLastOpenPrice(String stock) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpoint)
            .queryParam("function", "TIME_SERIES_INTRADAY")
            .queryParam("symbol", stock)
            .queryParam("interval", "5min")
            .queryParam("apikey", apiKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        builder.toUriString();

        var dto = restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.GET,
            entity,
            PriceDto.class)
            .getBody();
        var lastRefreshed = dto.metaData.getLastRefreshed();
        return new BigDecimal(dto.timeSeries.get(lastRefreshed).getOpenPrice());
    }
}
