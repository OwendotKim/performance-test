package com.example.kopringstudy.infrastructure

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoggingInterceptor : HandlerInterceptor {
    private val logger = LoggerFactory.getLogger(LoggingInterceptor::class.java)

    // 요청 전
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info("Incoming Request: Method=${request.method}, URI=${request.requestURI}, Query=${request.queryString ?: "None"}")
        return true // 요청을 계속 진행하려면 true를 반환합니다.
    }

    // 요청 후 (컨트롤러 실행 후)
    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: org.springframework.web.servlet.ModelAndView?) {
        logger.info("Request Processed: URI=${request.requestURI}, Status=${response.status}")
    }

    // 요청 완료 후
    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        logger.info("Request Completed: URI=${request.requestURI}, Exception=${ex?.message ?: "None"}")
    }
}
