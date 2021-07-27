package com.baojun.jpm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

public class Account {

    private Optional<String> accountNumber = Optional.empty();
    private Optional<List<String>> providers = Optional.empty();

    public Account() {}

    public Account(Optional<String> accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Account(Optional<String> accountNumber, Optional<List<String>> providers) {
        this.accountNumber = accountNumber;
        this.providers = providers;
    }

    public Optional<String> getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Optional<String> accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Optional<List<String>> getProviders() {
        return providers;
    }

    public void setProviders(Optional<List<String>> providers) {
        this.providers = providers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(this.accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.accountNumber);
    }

    @Override
    public String toString() {
        return "Account{accountNumber='" + this.accountNumber + '\'' + '}';
    }
}
