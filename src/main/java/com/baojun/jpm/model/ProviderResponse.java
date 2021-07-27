package com.baojun.jpm.model;

import java.util.List;

public class ProviderResponse {

    private List<Provider> result;

    public ProviderResponse() {}

    public ProviderResponse(List<Provider> result) {
        this.result = result;
    }

    public List<Provider> getResult() {
        return result;
    }

    public void setResult(List<Provider> result) {
        this.result = result;
    }
}
