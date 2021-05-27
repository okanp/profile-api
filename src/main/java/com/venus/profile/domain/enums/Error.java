package com.venus.profile.domain.enums;

public enum Error {
    INVALID_REQUEST_PARAM("0001"), ENTITY_NOT_FOUND("0002");

    public String code;

    Error(String code) {
        this.code = code;
    }
}