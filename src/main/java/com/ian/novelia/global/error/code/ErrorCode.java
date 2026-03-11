package com.ian.novelia.global.error.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    AUTHOR_ROLE_REQUIRED("AUTHOR_403", HttpStatus.FORBIDDEN, "작가 권한이 필요한 요청입니다."),
    ADMIN_ROLE_REQUIRED("ADMIN_403", HttpStatus.FORBIDDEN, "관리자 권한이 필요한 요청입니다."),

    EPISODE_NOT_FOUND("EPISODE_404", HttpStatus.NOT_FOUND, "일치하는 회차 정보를 찾을 수 없습니다."),
    EPISODE_FORBIDDEN("EPISODE_403", HttpStatus.FORBIDDEN, "회차 접근 권한이 없습니다."),

    NOVEL_NOT_FOUND("NOVEL_404", HttpStatus.NOT_FOUND, "일치하는 소설 정보를 찾을 수 없습니다."),
    NOVEL_FORBIDDEN("NOVEL_403", HttpStatus.FORBIDDEN, "소설 접근 권한이 없습니다."),

    PURCHASE_REQUIRED("PURCHASE_402", HttpStatus.PAYMENT_REQUIRED, "회차 열람을 위해 결제가 필요합니다."),
    PURCHASE_ALREADY_PURCHASED("PURCHASE_400", HttpStatus.BAD_REQUEST, "이미 구매한 회차입니다."),

    ROLE_REQUEST_DUPLICATE("ROLE_REQUEST_400", HttpStatus.BAD_REQUEST, "중복된 권한 요청입니다."),
    ROLE_REQUEST_ALREADY_AUTHOR("ROLE_REQUEST_400", HttpStatus.BAD_REQUEST, "이미 권한이 있는 사용자입니다."),
    ROLE_REQUEST_NOT_FOUND("ROLE_REQUEST_404", HttpStatus.NOT_FOUND, "권한 요청을 찾을 수 없습니다."),
    ROLE_REQUEST_ALREADY_PROCESSED("ROLE_REQUEST_400", HttpStatus.BAD_REQUEST, "이미 처리된 권한 요청입니다."),

    S3_UPLOAD_FAILED("S3_UPLOAD_500", HttpStatus.INTERNAL_SERVER_ERROR, "S3 이미지 등록에 실패했습니다."),
    INVALID_FILE_NAME("FILE_400", HttpStatus.BAD_REQUEST, "유효하지 않은 파일 이름입니다."),

    USER_NOT_FOUND("USER_404", HttpStatus.NOT_FOUND, "일치하는 회원 정보를 찾을 수 없습니다."),

    PASSWORD_CONFIRM_MISMATCH("AUTH_400", HttpStatus.BAD_REQUEST, "비밀번호 확인이 일치하지 않습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;
}