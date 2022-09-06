package com.valko.models;

public enum Operation
{
    SHOW_OPERATIONS("Show operations (like this)"),
    VIEW_ACCOUNT_INFORMATION("View account information"),
    VIEW_LAST_TRANSACTIONS("View last transactions"),
    DEPOSIT("Deposit money"),
    WITHDRAWAL("Withdraw money"),
    TRANSFER("Transfer money to another account"),
    EXIT("Exit program");

    private final String description;

    Operation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}