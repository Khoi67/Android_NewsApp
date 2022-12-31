package com.example.thenewsapp.objects.enums;

public enum Role {
    BASIC_USER("BASIC_USER"),
    ADMIN("ADMIN"),
    WRITER("WRITER"),
    ;
    private String value;
    Role(String value) {
        this.value =value;
    }

    public String getValue() {
        return value;
    }
}