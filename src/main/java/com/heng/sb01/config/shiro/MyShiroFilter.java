package com.heng.sb01.config.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyShiroFilter extends FormAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(MyShiroFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated()) {
            WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            WebUtils.toHttp(response).sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        return false;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) {
        Subject subject = getSubject(servletRequest, servletResponse);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            if (logger.isInfoEnabled()) {
                System.out.println("");
                System.out.println(" > " + request.getMethod() + " " + getUrl(request));
                Enumeration headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String key = (String) headerNames.nextElement();
                    String value = request.getHeader(key);
                    System.out.println(" > " + key + ":" + value);
                }
                System.out.println("");
            }
        } else {
            try {
                WebUtils.toHttp(servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
//        String uri = request.getRequestURI();
//        if (uri.equals("/") || uri.equals("/login")) return true;
//        if (uri.startsWith("/error") || uri.startsWith("/assets") || uri.startsWith("/upload") || uri.endsWith("favicon.ico"))
//            return true;
//        if (!subject.isAuthenticated() && !subject.isRemembered()) {
//            return false;
//        }
//        try {
//            return subject.isPermitted(new URIPermission(new URI(request.getRequestURI()), request.getMethod()));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    private static String getUrl(HttpServletRequest request) {
        StringBuffer uri = request.getRequestURL();
        String url = uri.toString();
        Map<String, String[]> map = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entry = map.entrySet();
        Iterator<Map.Entry<String, String[]>> iterator = entry.iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> item = iterator.next();
            String key = item.getKey();
            for (String value : item.getValue()) {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        String string = sb.toString();
        if (sb.lastIndexOf("&") > 0) {
            url = url + "?" + string.substring(0, string.lastIndexOf("&"));
        }
        return url;
    }
}
