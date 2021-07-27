package com.baojun.jpm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
@EnableConfigurationProperties
@ConfigurationProperties
public class AppConfig {

    private List<ProviderConfig> providers;

    public HashMap<String, ProviderConfig> getProvidersMap() {
        HashMap<String, ProviderConfig> providersMap = new HashMap<>();
        providers.forEach(providerConfig -> providersMap.put(providerConfig.getName(), providerConfig));
        return providersMap;
    }

    public List<ProviderConfig> getProviders() {
        return providers;
    }

    public void setProviders(List<ProviderConfig> providers) {
        this.providers = providers;
    }
}
