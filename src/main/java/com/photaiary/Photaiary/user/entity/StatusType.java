package com.photaiary.Photaiary.user.entity;

import lombok.Getter;

@Getter
public enum StatusType {
    ACTIVE("활성"),
    UNACTIVE("비활성");

    private final String value;

    StatusType(String value) {
        this.value = value;
    }
}
