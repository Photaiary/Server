package com.photaiary.Photaiary.friend.entity;

public enum StatusType {
    active("ACTIVE"),
    unactive("UNACTIVE");
    private final String value;

    StatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

