package com.example.kopringstudy.advice

import com.example.kopringstudy.exception.BoardNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentExceptionAdvice(e: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity<String>(e.message.toString(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(BoardNotFoundException::class)
    fun boardNotFoundExceptionAdvice(): ResponseEntity<String> {
        return ResponseEntity("게시글을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
    }

}
