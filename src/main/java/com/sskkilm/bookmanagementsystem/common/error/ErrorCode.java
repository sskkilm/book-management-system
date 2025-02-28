package com.sskkilm.bookmanagementsystem.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    UNKNOWN(INTERNAL_SERVER_ERROR, "알 수 없는 에러입니다."),
    INVALID_PARAMETER(BAD_REQUEST, "잘못된 요청 값입니다."),
    AUTHOR_NOT_FOUND(NOT_FOUND, "해당 저자가 존재하지 않습니다."),
    BOOK_NOT_FOUND(NOT_FOUND, "해당 도서가 존재하지 않습니다."),
    ALREADY_EXIST_EMAIL(CONFLICT, "이미 사용 중인 이메일입니다."),
    ALREADY_EXIST_ISBN(CONFLICT, "이미 사용 중인 isbn입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
