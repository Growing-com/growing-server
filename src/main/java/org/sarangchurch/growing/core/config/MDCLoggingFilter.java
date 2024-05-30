package org.sarangchurch.growing.core.config;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class MDCLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        UUID uuid = UUID.randomUUID();
        MDC.put("request_id", uuid.toString());
        filterChain.doFilter(servletRequest, servletResponse);
        MDC.clear();
    }
}
