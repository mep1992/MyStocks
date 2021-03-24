package com.mystocks.mystocks.client;

import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.mystocks.mystocks.client.PriceDto.PriceData;
import com.mystocks.mystocks.client.PriceDto.PriceMetaData;

class PricingClientTest {

    private static final String BASE_URL = "https://www.alphavantage.co/query";
    private static final String FAKE_API_KEY = "SOMEAPIKEY";
    private static final String SYMBOL = "TEAM";
    private static final String URI = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=TEAM&interval=5min&apikey=SOMEAPIKEY";

    @Test
    void shouldMakeCallTo3rdPartyService() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        var lastRefreshed = "2021-03-22 16:05:00";
        var eqityPrice = "221.7400";
        var priceDto = PriceDto.builder()
            .metaData(new PriceMetaData(lastRefreshed))
            .timeSeries(Map.of(lastRefreshed, new PriceData(eqityPrice)))
            .build();

        var restTemplate = mock(RestTemplate.class);
        when(restTemplate.exchange(URI, HttpMethod.GET, entity, PriceDto.class))
            .thenReturn(ResponseEntity.ok(priceDto));

        PricingClient pricingClient = new PricingClient(restTemplate, BASE_URL, FAKE_API_KEY);
        var returnedPrice = pricingClient.getLastOpenPrice(SYMBOL);

        assertThat(returnedPrice, is(new BigDecimal(eqityPrice)));
    }
}