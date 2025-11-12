package com.acme.finanzasbackend.housingFinance.interfaces.rest.resources;

public record UpdateHousingResource(
        String title,
        String description,
        String province,
        String district,
        String address,
        String department,
        Double area,
        Integer roomQuantity,
        Double salePrice,
        String housingState,
        String housingCategory,
        Long currencyId
) {
    public UpdateHousingResource {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("title can't be null or empty");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("description can't be null or empty");
        }
        if (province == null) {
            throw new IllegalArgumentException("province can't be null");
        }
        if (district == null || district.isEmpty()) {
            throw new IllegalArgumentException("district can't be null or empty");
        }
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("address can't be null or empty");
        }
        if (area == null || area <= 0) {
            throw new IllegalArgumentException("area can't be null or less than or equal to 0");
        }
        if (roomQuantity == null || roomQuantity <= 0) {
            throw new IllegalArgumentException("roomQuantity can't be null or less than or equal to 0");
        }
        if (salePrice == null || salePrice <= 0) {
            throw new IllegalArgumentException("salePrice can't be null or less than or equal to 0");
        }
        if (housingState == null) {
            throw new IllegalArgumentException("housingState can't be null");
        }
        if (housingCategory == null) {
            throw new IllegalArgumentException("housingCategory can't be null");
        }
        if (currencyId == null || currencyId <= 0) {
            throw new IllegalArgumentException("currencyId can't be null or less than or equal to 0");
        }
    }
}
