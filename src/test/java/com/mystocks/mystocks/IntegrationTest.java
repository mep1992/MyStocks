package com.mystocks.mystocks;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mystocks.mystocks.api.dto.BalanceDto;
import com.mystocks.mystocks.domain.Randomiser;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final Randomiser randomiser = new Randomiser();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${client.endpoint}")
    String clientEndpoint;

    @Value("${client.apikey}")
    String clientApikey;

    @Test
    void shouldDeposit() throws Exception {
        var depositAmount = randomiser.amount();

        var requestDto = new BalanceDto(String.valueOf(depositAmount));
        var expectedDto = new BalanceDto(String.valueOf(depositAmount));
        mockMvc
            .perform(post("/api/balance/deposit")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(expectedDto)));
    }
}
