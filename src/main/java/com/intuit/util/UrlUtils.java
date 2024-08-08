package com.intuit.util;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class UrlUtils {
    public static String constructUrlWithParams(String url, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }

        StringBuilder urlWithParams = new StringBuilder(url);
        urlWithParams.append("?");
        params.forEach((key, value) -> urlWithParams.append(key).append("=").append(value).append("&"));

        // Remove trailing '&'
        if (urlWithParams.length() > 0 && urlWithParams.charAt(urlWithParams.length() - 1) == '&') {
            urlWithParams.deleteCharAt(urlWithParams.length() - 1);
        }

        return urlWithParams.toString();
    }
}
