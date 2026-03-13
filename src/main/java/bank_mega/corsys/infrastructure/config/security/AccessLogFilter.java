package bank_mega.corsys.infrastructure.config.security;

import bank_mega.corsys.application.accesslog.command.CreateAccessLogCommand;
import bank_mega.corsys.application.accesslog.usecase.CreateAccessLogUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;


@Component
@RequiredArgsConstructor
public class AccessLogFilter extends OncePerRequestFilter {

    private static final int REQUEST_BODY_CACHE_LIMIT = 4096; // 4 KB
    private final CreateAccessLogUseCase createAccessLogUseCase;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        boolean shouldCacheBody = shouldCacheRequestBody(request);

        HttpServletRequest wrappedRequest = request;
        if (shouldCacheBody) {
            wrappedRequest = new ContentCachingRequestWrapper(
                    request,
                    REQUEST_BODY_CACHE_LIMIT
            );
        }

        long startTime = System.currentTimeMillis();
        Exception exception = null;
//
//        try {
            filterChain.doFilter(wrappedRequest, response);
//        } catch (Exception ex) {
//            exception = ex;
//            throw ex;
//        } finally {
//            long responseTimeMs = System.currentTimeMillis() - startTime;
//
//            // =========================
//            // REQUEST INFO
//            // =========================
//            String httpMethod = request.getMethod();
//            String uri = request.getRequestURI();
//            String queryParams = request.getQueryString();
//            String ipAddress = resolveClientIp(request);
//            String userAgent = request.getHeader("User-Agent");
//
//            // =========================
//            // REQUEST BODY
//            // =========================
//            String requestBody = null;
//            if (wrappedRequest instanceof ContentCachingRequestWrapper cachingRequest) {
//                requestBody = extractRequestBody(cachingRequest);
//            }
//
//            // =========================
//            // RESPONSE INFO
//            // =========================
//            int statusCode = response.getStatus();
//            String errorMessage = extractErrorMessage(exception, statusCode);
//
//            // =========================
//            // USER Principle (JWT)
//            // =========================
//            Authentication authentication =
//                    SecurityContextHolder.getContext().getAuthentication();

            // =========================
            // SAVE LOG
            // =========================
//            if (authentication instanceof UserTokenAuthentication userAuth) {
//                this.createAccessLogUseCase.execute(
//                        CreateAccessLogCommand.builder()
//                                .user(userAuth.getPrincipal())
//                                .httpMethod(httpMethod)
//                                .uri(uri)
//                                .queryParams(queryParams)
//                                .ipAddress(ipAddress)
//                                .requestBody(requestBody)
//                                .responseTimeMs(responseTimeMs)
//                                .statusCode(statusCode)
//                                .errorMessage(errorMessage)
//                                .userAgent(userAgent)
//                                .createdAt(Instant.now())
//                                .build()
//                );
//            }
//        }
    }

    // =====================================================
    // Helper Methods
    // =====================================================

    private boolean shouldCacheRequestBody(HttpServletRequest request) {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            return false;
        }

        String contentType = request.getContentType();
        return contentType != null
                && contentType.contains("application/json");
    }

    private String extractRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        if (content.length == 0) {
            return null;
        }
        return new String(content, StandardCharsets.UTF_8);
    }

    private String extractErrorMessage(Exception exception, int statusCode) {
        if (exception != null) {
            return exception.getClass().getSimpleName() + ": " + exception.getMessage();
        }

        if (statusCode >= 400) {
            return "HTTP " + statusCode;
        }

        return null;
    }

    private String resolveClientIp(HttpServletRequest request) {
        String[] headers = {
                "X-Forwarded-For",
                "X-Real-IP",
                "CF-Connecting-IP",
                "True-Client-IP"
        };

        for (String header : headers) {
            String value = request.getHeader(header);
            if (value != null && !value.isBlank()) {
                return value.split(",")[0].trim();
            }
        }

        String ip = request.getRemoteAddr();

        if ("::1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            return "127.0.0.1";
        }

        return ip;
    }

}