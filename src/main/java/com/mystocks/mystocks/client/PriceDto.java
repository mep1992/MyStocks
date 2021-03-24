package com.mystocks.mystocks.client;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {
    @JsonProperty("Meta Data")
    PriceMetaData metaData;

    @JsonProperty("Time Series (5min)")
    Map<String, PriceData> timeSeries;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PriceMetaData {
        @JsonProperty("3. Last Refreshed")
        String lastRefreshed;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PriceData {
        @JsonProperty("1. open")
        String openPrice;
    }
}
