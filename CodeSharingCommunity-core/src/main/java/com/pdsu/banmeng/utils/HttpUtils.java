package com.pdsu.banmeng.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 半梦
 * @create 2020-11-26 19:18
 */
public abstract class HttpUtils {

    private static final String X_FORWARDED_FOR_IP_HEADER_NAME = "x-forwarded-for";
    private static final String PROXY_CLIENT_IP_HEADER_NAME = "Proxy-Client-IP";
    private static final String WL_PROXY_CLIENT_IP_HEADER_NAME = "x-forwarded-for";
    private static final String UNKNOWN = "unknown";

    /**
     * 保持用户认证状态的请求头以及 Cookie 的名字
     */
    private static final String AUTHORIZATION = "authorization";
    private static final String REMEMBER_COOKIE_NAME = "rememberMe";
    private static final String SET_COOKIE_NAME = "Set-Cookie";

    /**
     * 本机 IP
     */
    private static final String LOCALHOST_IP = "127.0.0.1";

    /**
     * 获取用户 IP
     */
    @Nullable
    public static String getIpAddr(@NonNull HttpServletRequest request) {

        String ipAddress;
        try {
            ipAddress = request.getHeader(X_FORWARDED_FOR_IP_HEADER_NAME);
            if (StringUtils.hasText(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader(PROXY_CLIENT_IP_HEADER_NAME);
            }
            if (StringUtils.hasText(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader(WL_PROXY_CLIENT_IP_HEADER_NAME);
            }
            if (StringUtils.hasText(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals(LOCALHOST_IP)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        return "";
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    @Nullable
    public static String getSessionId(@NonNull HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION);
    }

    @Nullable
    public static String getRemember(@NonNull HttpServletRequest request) {
        return request.getHeader(REMEMBER_COOKIE_NAME);
    }

    @NonNull
    public static String getSessionHeader() {
        return AUTHORIZATION;
    }

    @NonNull
    public static String getRememberCookieName() {
        return REMEMBER_COOKIE_NAME;
    }

    @NonNull
    public static String getSetCookieName() {
        return SET_COOKIE_NAME;
    }

    public static long getSessionTimeout() {
        return DateUtils.NEWS_TIME_WEEK * 1000;
    }

}
