package com.Mitarbeiterverwaltung.HrManagement.domain;

import java.util.Objects;

public final class Address {
    private final String street;
    private final String houseNumber;
    private final String postalCode;
    private final String city;
    private final String country;

    private Address(String street, String houseNumber, String postalCode, String city, String country) {
        this.street = requireNonBlank(street, "street");
        this.houseNumber = requireNonBlank(houseNumber, "houseNumber");
        this.postalCode = requireNonBlank(postalCode, "postalCode");
        this.city = requireNonBlank(city, "city");
        this.country = requireNonBlank(country, "country");
    }

    public static Address of(String street, String houseNumber, String postalCode, String city, String country) {
        return new Address(street, houseNumber, postalCode, city, country);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        Address address = (Address) o;
        return street.equals(address.street)
                && houseNumber.equals(address.houseNumber)
                && postalCode.equals(address.postalCode)
                && city.equals(address.city)
                && country.equals(address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNumber, postalCode, city, country);
    }

    @Override
    public String toString() {
        return street + " " + houseNumber + ", " + postalCode + " " + city + ", " + country;
    }

    private static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
