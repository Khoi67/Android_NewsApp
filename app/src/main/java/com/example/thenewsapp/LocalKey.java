package com.example.thenewsapp;

public enum LocalKey {
    LAST_SIGNED_IN_USER("LAST_SIGNED_IN_USER"),
    THEME ("THEME"),
    GOOGLE_ACCOUNT_INFO("GOOGLE ACCOUNT_INFO");

    private String value;

    LocalKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
