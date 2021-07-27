package com.baojun.jpm;

import com.baojun.jpm.config.AppConfig;
import com.baojun.jpm.controller.AccountController;
import com.baojun.jpm.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountController.class)
@EnableConfigurationProperties(value = AppConfig.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenNoAccount_thenReturns400() throws Exception {
        mockMvc.perform(post("/account/validate")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenNullAccountNumberNoProvider_thenReturns400() throws Exception {
        Account account = new Account(null);

        mockMvc.perform(post("/account/validate")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenNullAccountNumberWithProvider_thenReturns400() throws Exception {
        List<String> providers = new ArrayList<>(Arrays.asList("provider1", "provider2"));
        Account account = new Account(null, Optional.of(providers));

        mockMvc.perform(post("/account/validate")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEmptyAccountNumberWithProvider_thenReturns400() throws Exception {
        List<String> providers = new ArrayList<>(Arrays.asList("provider1", "provider2"));
        Account account = new Account(Optional.empty(), Optional.of(providers));

        mockMvc.perform(post("/account/validate")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenValidAccountNumberNoProvider_thenReturnsAllProvidersFromConfigValidStatus() throws Exception {
        Account account = new Account(Optional.of("12345678"));

        mockMvc.perform(post("/account/validate")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(3)))
                .andExpect(jsonPath("$.result[0].provider", is("provider1")))
                .andExpect(jsonPath("$.result[0].isValid", is(true)))
                .andExpect(jsonPath("$.result[1].provider", is("provider2")))
                .andExpect(jsonPath("$.result[1].isValid", is(true)))
                .andExpect(jsonPath("$.result[2].provider", is("provider3")))
                .andExpect(jsonPath("$.result[2].isValid", is(true)));
    }

    @Test
    void whenValidAccountNumberEmptyProvider_thenReturnsAllProvidersFromConfigValidStatus() throws Exception {
        Account account = new Account(Optional.of("12345678"), Optional.of(new ArrayList<>()));

        mockMvc.perform(post("/account/validate")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(3)))
                .andExpect(jsonPath("$.result[0].provider", is("provider1")))
                .andExpect(jsonPath("$.result[0].isValid", is(true)))
                .andExpect(jsonPath("$.result[1].provider", is("provider2")))
                .andExpect(jsonPath("$.result[1].isValid", is(true)))
                .andExpect(jsonPath("$.result[2].provider", is("provider3")))
                .andExpect(jsonPath("$.result[2].isValid", is(true)));
    }

    @Test
    void whenValidAccountNumberWithProvider_thenReturnsGivenProvidersValidStatus() throws Exception {
        List<String> providers = new ArrayList<>(Arrays.asList("provider1", "provider2"));
        Account account = new Account(Optional.of("12345678"), Optional.of(providers));

        mockMvc.perform(post("/account/validate")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(2)))
                .andExpect(jsonPath("$.result[0].provider", is("provider1")))
                .andExpect(jsonPath("$.result[0].isValid", is(true)))
                .andExpect(jsonPath("$.result[1].provider", is("provider2")))
                .andExpect(jsonPath("$.result[1].isValid", is(true)));
    }

    @Test
    void whenValidAccountNumberWithInvalidProvider_thenReturnsGivenProvidersValidStatus() throws Exception {
        List<String> providers = new ArrayList<>(Arrays.asList("provider1", "provider4"));
        Account account = new Account(Optional.of("12345678"), Optional.of(providers));

        mockMvc.perform(post("/account/validate")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(1)))
                .andExpect(jsonPath("$.result[0].provider", is("provider1")))
                .andExpect(jsonPath("$.result[0].isValid", is(true)));
    }

    @Test
    void whenValidAccountNumberWithInvalidProvider_thenReturnsEmptyResult() throws Exception {
        List<String> providers = new ArrayList<>(Arrays.asList("provider4"));
        Account account = new Account(Optional.of("12345678"), Optional.of(providers));

        mockMvc.perform(post("/account/validate")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(0)));
    }
}