package com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto;

public class BankAccountResponse {
    private String iban;
    private String bic;
    private String accountHolder;

    public BankAccountResponse(String iban, String bic, String accountHolder) {
        this.iban = iban;
        this.bic = bic;
        this.accountHolder = accountHolder;
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
}
