package com.baojun.jpm.controller;

import com.baojun.jpm.config.AppConfig;
import com.baojun.jpm.config.ProviderConfig;
import com.baojun.jpm.model.Account;
import com.baojun.jpm.model.Provider;
import com.baojun.jpm.model.ProviderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AccountController {

    @Autowired
    private AppConfig appConfig;

    @PostMapping(value = "/account/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity validateAccount(@Valid @RequestBody Optional<Account> account) {
        if(!account.isPresent() || !account.get().getAccountNumber().isPresent() || account.get().getAccountNumber().get().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide a valid Account Number");

        HashMap<String, ProviderConfig> existingProviders = appConfig.getProvidersMap();
        List<String> accountProviders = account.get().getProviders().orElse(new ArrayList<>());
        final List<ProviderConfig> validProviders = new ArrayList<>();

        if (accountProviders.isEmpty()) validProviders.addAll(appConfig.getProviders());
        else accountProviders.forEach(provider -> { if(existingProviders.containsKey(provider)) validProviders.add(existingProviders.get(provider)); });
        // TODO: Hit each url to get isValid status below
        List<Provider> result = validProviders.stream().map(providerConfig -> new Provider(providerConfig.getName(), true)).collect(Collectors.toList());

        return ResponseEntity.ok(new ProviderResponse(result));
    }
}
