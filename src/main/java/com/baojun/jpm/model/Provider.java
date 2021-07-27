package com.baojun.jpm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Provider {

    private String provider;
    private boolean isValid;

    public Provider() {}

    public Provider(String provider, boolean isValid) {
        this.provider = provider;
        this.isValid = isValid;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @JsonProperty(value="isValid")
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        isValid = isValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Provider)) return false;
        Provider provider = (Provider) o;
        return Objects.equals(this.provider, provider.provider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.provider);
    }

    @Override
    public String toString() {
        return "Provider{provider='" + this.provider + '\'' + '}';
    }
}
