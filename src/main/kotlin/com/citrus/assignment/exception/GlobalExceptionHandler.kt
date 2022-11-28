package com.citrus.assignment.exception

import com.citrus.assignment.transfer.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(value = [CustomException::class])
    fun handlingCustomException(
        ex: CustomException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode: ErrorCode = ex.errorCode
        val errorResponse = ErrorResponse(
            time = LocalDateTime.now(),
            status = errorCode.name,
            message = errorCode.message,
            requestURI = request.requestURI
        )
        return ResponseEntity(errorResponse, errorCode.status)
    }

    @ExceptionHandler(value = [Exception::class, java.lang.Exception::class])
    fun handlingException(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                time = LocalDateTime.now(),
                status = ex.cause.toString(),
                message = ex.message ?: "Internal Server Error",
                requestURI = request.requestURI
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}
