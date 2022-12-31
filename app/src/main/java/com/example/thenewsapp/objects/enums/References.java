package com.example.thenewsapp.objects.enums;

public enum References {
    ACCOUNT ("account"),
    PAPER ("paper"),
    FAVORITES("favorites"),
    CATEGORIES("categories"),
    IMAGES("images");

    String value;

    References (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
