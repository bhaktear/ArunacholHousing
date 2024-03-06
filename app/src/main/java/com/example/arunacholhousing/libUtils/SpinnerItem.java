package com.example.arunacholhousing.libUtils;

public class SpinnerItem {
    private String name;
    private String value;

    public SpinnerItem(String displayText, String value) {
        this.name = displayText;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString(){
        return name;
    }
}

