package com.Mitarbeiterverwaltung.HrManagement.domain;

import java.util.Objects;

public final class BankAccount {
    private final String iban;
    private final String bic;
    private final String accountHolder;

    private BankAccount(String iban, String bic, String accountHolder) {
        this.iban = requireNonBlank(iban, "iban");
        this.bic = requireNonBlank(bic, "bic");
        this.accountHolder = requireNonBlank(accountHolder, "accountHolder");
    }

    public static BankAccount of(String iban, String bic, String accountHolder) {
        return new BankAccount(iban, bic, accountHolder);
    }

    public String getIban() {
        return iban;
    }

    public String getBic() {
        return bic;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankAccount)) {
            return false;
        }
        BankAccount that = (BankAccount) o;
        return iban.equals(that.iban) && bic.equals(that.bic) && accountHolder.equals(that.accountHolder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban, bic, accountHolder);
    }

    @Override
    public String toString() {
        return "IBAN: " + iban + ", BIC: " + bic + ", Holder: " + accountHolder;
    }

    private static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
