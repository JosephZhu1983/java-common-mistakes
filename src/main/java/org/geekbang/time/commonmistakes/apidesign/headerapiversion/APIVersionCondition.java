package org.geekbang.time.commonmistakes.apidesign.headerapiversion;

import lombok.Getter;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

public class APIVersionCondition implements RequestCondition<APIVersionCondition> {

    @Getter
    private String apiVersion;
    @Getter
    private String headerKey;

    public APIVersionCondition(String apiVersion, String headerKey) {
        this.apiVersion = apiVersion;
        this.headerKey = headerKey;
    }

    @Override
    public APIVersionCondition combine(APIVersionCondition other) {
        return new APIVersionCondition(other.getApiVersion(), other.getHeaderKey());
    }

    @Override
    public APIVersionCondition getMatchingCondition(HttpServletRequest request) {
        String version = request.getHeader(headerKey);
        return apiVersion.equals(version) ? this : null;
    }

    @Override
    public int compareTo(APIVersionCondition other, HttpServletRequest request) {
        return 0;
    }
}
