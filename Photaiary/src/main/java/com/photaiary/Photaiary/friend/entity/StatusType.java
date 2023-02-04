package com.photaiary.Photaiary.friend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

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

