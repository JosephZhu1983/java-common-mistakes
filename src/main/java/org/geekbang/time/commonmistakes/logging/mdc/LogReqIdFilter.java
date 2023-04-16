package org.geekbang.time.commonmistakes.logging.mdc;

import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

public class LogReqIdFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put("reqId", getRequestId(request));
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove("reqId");
        }
    }

    private String getRequestId(ServletRequest request) {
        String id = null;
        if (request instanceof HttpServletRequest)
            id = ((HttpServletRequest) request).getHeader("x-request-id");
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }
}
