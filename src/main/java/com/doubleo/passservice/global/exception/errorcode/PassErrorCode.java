package com.doubleo.passservice.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PassErrorCode implements BaseErrorCode {
    PATIENT_ID_REQUIRED_FOR_GUARDIAN(HttpStatus.BAD_REQUEST, "patient id가 필요합니다."),
    PASS_NOT_FOUND(HttpStatus.NOT_FOUND, "pass를 찾을 수 없습니다."),
    VISIT_CATEGORY_REQUIRED_FOR_PASS(HttpStatus.BAD_REQUEST, "visit category가 올바르지 않습니다."),
    VC_ISSUE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "VC 발급에 실패했습니다."),
    CONNECTION_ID_NOT_ASSIGNED(HttpStatus.CONFLICT, "connectionId가 아직 할당되지 않았습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
