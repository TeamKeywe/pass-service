package com.doubleo.passservice.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PassSearchErrorCode implements BaseErrorCode {
    INVALID_PERIOD(HttpStatus.BAD_REQUEST, "period는 7, 14, 28 중 하나여야 합니다."),
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "startDate는 endDate보다 이전이어야 합니다."),
    MISSING_DATE_IN_MANUAL_MODE(HttpStatus.BAD_REQUEST, "수동 입력일 경우 startDate와 endDate는 필수입니다."),
    INVALID_VISIT_CATEGORY(HttpStatus.BAD_REQUEST, "방문자 유형(visit category)이 올바르지 않습니다."),

    INTERNAL_PASS_QUERY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "출입증 정보 조회 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
