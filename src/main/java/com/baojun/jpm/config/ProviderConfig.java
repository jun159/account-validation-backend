package com.baojun.jpm.config;

public class ProviderConfig {

    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Provider{" + "name='" + name + '\'' + ", url='" + url + '\'' + '}';
    }
}
