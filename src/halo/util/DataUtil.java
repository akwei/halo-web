package halo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DataUtil {

    private DataUtil() {
    }

    public static final String DEFAULTCHARSET = "UTF-8";

    public static boolean isEmpty(String value) {
        if (value == null || value.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String value) {
        if (value != null && value.trim().length() > 0) {
            return true;
        }
        return false;
    }

    public static String toHtmlSimple(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("\n", "<br/>")
                .replaceAll("\r", "").replaceAll("\"", "&quot;")
                .replaceAll("'", "&#39;");
    }

    public static String toHtmlSimpleOneRow(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("\n", "")
                .replaceAll("\r", "").replaceAll("\"", "&quot;")
                .replaceAll("'", "&#39;");
    }

    public static String urlEncoder(String value) {
        if (value != null) {
            try {
                return URLEncoder.encode(value, DEFAULTCHARSET);
            }
            catch (UnsupportedEncodingException e) {
                System.out.println(e);
            }
        }
        return "";
    }

    public static String urlEncoder(String value, String charset) {
        if (value != null) {
            try {
                return URLEncoder.encode(value, charset);
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }
}