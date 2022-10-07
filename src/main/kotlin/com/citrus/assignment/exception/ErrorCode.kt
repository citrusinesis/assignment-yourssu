package com.citrus.assignment.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String,
) {
    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다"),
    FIELD_CANNOT_BE_NULL(HttpStatus.UNAUTHORIZED, "내용이 비어있을 수 없습니다."),
    USER_NOT_MATCH(HttpStatus.UNAUTHORIZED, "접근한 정보의 소유자가 아닙니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 정보를 찾을 수 없습니다"),
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글 정보를 찾을 수 없습니다"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글 정보를 찾을 수 없습니다"),
    DUPLICATED_EMAIL_ADDRESS(HttpStatus.CONFLICT, "이미 가입된 이메일 주소입니다"),
    DUPLICATED_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 별명입니다"),
}