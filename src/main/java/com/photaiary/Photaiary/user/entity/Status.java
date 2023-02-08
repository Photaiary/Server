package com.photaiary.Photaiary.user.entity;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("활성"),
    UNACTIVE("비활성");

    private final String value;

    Status(String value) {
        this.value = value;
    }
}
