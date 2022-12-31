package com.example.thenewsapp.objects.enums;

public enum Categories {
    THE_THAO("Thể thao"),
    PHAP_LUAT("Pháp luật"),
    GIAO_DUC("Giáo dục");

    private String value;

    Categories(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /// ĐỊnh nghĩa thể loại trong này !!
}
