package com.sskkilm.bookmanagementsystem.common.error;

public record ErrorResponse<T>(
        String code,
        String message,
        T detail
) {
}
