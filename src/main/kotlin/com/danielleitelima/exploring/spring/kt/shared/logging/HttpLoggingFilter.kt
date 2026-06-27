package com.danielleitelima.exploring.spring.kt.shared.logging

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID

@Component
class HttpLoggingFilter(private val log: Logger) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val correlationId = request.getHeader("X-Correlation-ID") ?: UUID.randomUUID().toString()
        MDC.put("correlationId", correlationId)
        response.setHeader("X-Correlation-ID", correlationId)

        val start = System.currentTimeMillis()
        try {
            log.info("Request started method={} uri={}", request.method, request.requestURI)
            filterChain.doFilter(request, response)
            val duration = System.currentTimeMillis() - start
            log.info("Request completed method={} uri={} status={} duration={}ms", request.method, request.requestURI, response.status, duration)
        } finally {
            MDC.clear()
        }
    }
}
