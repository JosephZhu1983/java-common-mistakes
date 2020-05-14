package org.geekbang.time.commonmistakes.dataandcode.xss;

import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Arrays;

public class XssRequestWrapper extends HttpServletRequestWrapper {

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        return Arrays.stream(super.getParameterValues(parameter)).map(this::clean).toArray(String[]::new);
    }

    @Override
    public String getHeader(String name) {
        return clean(super.getHeader(name));
    }

    @Override
    public String getParameter(String parameter) {
        return clean(super.getParameter(parameter));
    }

    private String clean(String value) {
        return StringUtils.isEmpty(value) ? "" : HtmlUtils.htmlEscape(value);
    }
}
