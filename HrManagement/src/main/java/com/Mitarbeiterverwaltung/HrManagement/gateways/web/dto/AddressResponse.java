package com.Mitarbeiterverwaltung.HrManagement.gateways.web.dto;

public class AddressResponse {
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;

    public AddressResponse(String street, String houseNumber, String postalCode, String city, String country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
