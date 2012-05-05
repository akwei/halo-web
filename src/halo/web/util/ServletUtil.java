package halo.web.util;

import halo.util.DataUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtil {

    private ServletUtil() {
    }

    private static final String HTTP_METHOD_POST = "post";

    public static String CHARSET_SOURCE = "ISO-8859-1";

    public static String CHARSET_TARGET = "UTF-8";

    public static final String USER_AGENT = "user-agent";

    public static final String ACCEPT = "accept";

    private static final String PAGE_ATTR_KEY = "page";

    public static boolean NEED_CHARSET_ENCODE = true;

    /**
     * 返回指定名字的header字段
     * 
     * @param request
     * @param name
     * @return
     */
    public static String getHeader(HttpServletRequest request, String name) {
        String value = request.getHeader(name);
        if (value == null) {
            return "";
        }
        return value.trim().toLowerCase();
    }

    /**
     * 返回当前请求的ua信息
     * 
     * @param request
     * @return
     */
    public static String getUA(HttpServletRequest request) {
        return getHeader(request, USER_AGENT);
    }

    public static boolean isPc(HttpServletRequest request) {
        if (getUA(request).indexOf("android") != -1) {
            return false;
        }
        if (getUA(request).indexOf("iphone") != -1) {
            return false;
        }
        String accept = getAccept(request);
        if (accept == null || accept.indexOf("wap") != -1
                || accept.indexOf("j2me") != -1
                || accept.indexOf("text/vnd") != -1
                || accept.indexOf("wml") != -1) {
            return false;
        }
        return true;
    }

    public static boolean isWap(HttpServletRequest request) {
        if (getUA(request).indexOf("android") != -1) {
            return true;
        }
        if (getUA(request).indexOf("iphone") != -1) {
            return true;
        }
        String accept = getAccept(request);
        if (accept.indexOf("wap") != -1 || accept.indexOf("j2me") != -1
                || accept.indexOf("text/vnd") != -1
                || accept.indexOf("wml") != -1) {
            return true;
        }
        return false;
    }

    /**
     * 返回当前请求的accept信息
     * 
     * @param request
     * @return
     */
    public static String getAccept(HttpServletRequest request) {
        return getHeader(request, ACCEPT);
    }

    public static void sendHtml(HttpServletResponse response, Object value)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        write(response, value.toString());
    }

    public static void write(HttpServletResponse response, String value)
            throws IOException {
        Writer writer = null;
        try {
            writer = response.getWriter();
            writer.write(value);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static Number[] getNumbers(HttpServletRequest request, String key) {
        String[] v = request.getParameterValues(key);
        if (v == null) {
            return null;
        }
        Number[] t = new Number[v.length];
        for (int i = 0; i < v.length; i++) {
            if (v[i].trim().equals("")) {
                t[i] = 0;
            }
            else {
                t[i] = new BigDecimal(v[i]);
            }
        }
        return t;
    }

    public static Number getNumber(HttpServletRequest request, String key,
            Number num) {
        String t = request.getParameter(key);
        if (t == null) {
            return num;
        }
        try {
            return new BigDecimal(t);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static Number getNumber(HttpServletRequest request, String key) {
        try {
            String t = request.getParameter(key);
            if (t == null || t.equals("")) {
                return 0;
            }
            return new BigDecimal(t);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static String getServerInfo(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme());
        sb.append("://");
        sb.append(request.getServerName());
        int port = request.getServerPort();
        if (port != 80) {
            sb.append(":").append(port);
        }
        sb.append(request.getContextPath());
        return sb.toString();
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cs = request.getCookies();
        if (cs == null) {
            return null;
        }
        for (Cookie cookie : cs) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    public static String getString(HttpServletRequest request, String key) {
        String t = request.getParameter(key);
        if (t == null) {
            return null;
        }
        t = t.trim();
        if (t.length() == 0) {
            return "";
        }
        if (NEED_CHARSET_ENCODE) {
            return t;
        }
        if (request.getMethod().equalsIgnoreCase(HTTP_METHOD_POST)) {
            return t;
        }
        try {
            return new String(t.getBytes(CHARSET_SOURCE), CHARSET_TARGET);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getPage(HttpServletRequest request) {
        int page = getInt(request, PAGE_ATTR_KEY);
        if (page < 1) {
            page = 1;
        }
        request.setAttribute(PAGE_ATTR_KEY, page);
        return page;
    }

    public static SimplePage getSimplePage(HttpServletRequest request, int size) {
        int page = getPage(request);
        SimplePage simplePage = new SimplePage(size, page);
        request.setAttribute(WebCnf.SIMPLEPAGE_ATTRIBUTE, simplePage);
        return simplePage;
    }

    public static long getLong(HttpServletRequest request, String key) {
        return getNumber(request, key).longValue();
    }

    public static long getLong(HttpServletRequest request, String key, long num) {
        return getNumber(request, key, num).longValue();
    }

    public static int getInt(HttpServletRequest request, String key) {
        return getNumber(request, key).intValue();
    }

    public static int getInt(HttpServletRequest request, String key, long num) {
        return getNumber(request, key, num).intValue();
    }

    public static byte getByte(HttpServletRequest request, String key) {
        return getNumber(request, key).byteValue();
    }

    public static byte getByte(HttpServletRequest request, String key, long num) {
        return getNumber(request, key, num).byteValue();
    }

    public static double getDouble(HttpServletRequest request, String key) {
        return getNumber(request, key).doubleValue();
    }

    public static double getDouble(HttpServletRequest request, String key,
            long num) {
        return getNumber(request, key, num).doubleValue();
    }

    public static void setSessionAttr(HttpServletRequest request, String name,
            Object value) {
        request.getSession().setAttribute(name, value);
    }

    public static Object getSessionValue(HttpServletRequest request, String name) {
        return request.getSession().getAttribute(name);
    }

    public static Object getRequestValue(HttpServletRequest request, String name) {
        return request.getAttribute(name);
    }

    public static void removeSessionAttr(HttpServletRequest request, String name) {
        request.getSession().removeAttribute(name);
    }

    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getRemoteAddr();
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    public static void setSessionMessage(HttpServletRequest request, String msg) {
        setSessionAttr(request, MessageUtil.MESSAGE_NAME, msg);
    }

    public static void sendXml(HttpServletResponse response, String value)
            throws IOException {
        response.setContentType("text/xml;charset=UTF-8");
        StringBuilder sb = new StringBuilder(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append(value);
        sendValue(response, sb.toString());
    }

    public static void sendJson(HttpServletResponse response, String value)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        sendValue(response, sb.toString());
    }

    public static void sendValue(HttpServletResponse response, String value)
            throws IOException {
        Writer writer = null;
        try {
            writer = response.getWriter();
            writer.write(value);
        }
        catch (IOException e) {
            throw new IOException(e);
        }
    }

    public static boolean isMultipart(HttpServletRequest request) {
        String type = null;
        String type1 = request.getHeader("Content-Type");
        String type2 = request.getContentType();
        if (type1 == null && type2 != null) {
            type = type2;
        }
        else if (type2 == null && type1 != null) {
            type = type1;
        }
        else if (type1 != null && type2 != null) {
            type = (type1.length() > type2.length() ? type1 : type2);
        }
        if (type == null
                || !type.toLowerCase().startsWith("multipart/form-data")) {
            return false;
        }
        return true;
    }

    public static String getReturnUrl(HttpServletRequest request) {
        String url = request.getRequestURL().append("?")
                .append(request.getQueryString()).toString();
        return url;
    }

    public static String getCallBackUrl(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (DataUtil.isEmpty(queryString)) {
            return request.getRequestURL().toString();
        }
        return request
                .getRequestURL()
                .append(queryString.replaceAll(">", "&gt;").replaceAll("<",
                        "&lt;")).toString();
    }
}