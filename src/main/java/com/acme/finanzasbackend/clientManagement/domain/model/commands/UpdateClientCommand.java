package com.acme.finanzasbackend.clientManagement.domain.model.commands;

public record UpdateClientCommand(
        Long id,
        String firstname,
        String lastname,
        String dni,
        Integer age,
        String email,
        Boolean isWorking,
        Integer dependentsNumber,
        Double monthlyIncome,
        Boolean isDependent,
        Double workingYears,
        Long currencyId
) {
    public UpdateClientCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id can't be null or empty");
        }
        if (firstname == null || firstname.isEmpty()) {
            throw new IllegalArgumentException("firstname can't be null or empty");
        }
        if (lastname == null || lastname.isEmpty()) {
            throw new IllegalArgumentException("lastname can't be null or empty");
        }
        if (dni == null || dni.length() < 8) {
            throw new IllegalArgumentException("dni can't be null or has less than 8 characters");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("email can't be null or empty");
        }
        if (isWorking == null) {
            throw new IllegalArgumentException("isWorking can't be null or empty");
        }
        if (dependentsNumber == null || dependentsNumber < 0) {
            throw new IllegalArgumentException("dependentsNumber can't be null or less than 0");
        }
        if (monthlyIncome == null || monthlyIncome < 0) {
            throw new IllegalArgumentException("monthlyIncome can't be null or less than 0");
        }
        if (isDependent == null) {
            throw new IllegalArgumentException("isDependent can't be null or empty");
        }
        if (workingYears == null || workingYears < 0) {
            throw new IllegalArgumentException("workingYears can't be null or less than 0");
        }
        if (currencyId == null) {
            throw new IllegalArgumentException("currencyId can't be null or empty");
        }
    }
}
