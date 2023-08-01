package com.hehehe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultType {

    SUCCESS("200", "success"),
    //file
    NOT_EXIST("501", "system error"),
    SYSTEM_ERROR("502", "system error");

    private final String code;
    private final String desc;
}
