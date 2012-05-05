package halo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataUtil {

    private DataUtil() {
    }

    public static final String DEFAULTCHARSET = "UTF-8";

    public static boolean isOutOfLength(String value, int minLen, int maxLen) {
        if (value == null) {
            return true;
        }
        if (value.length() > maxLen) {
            return true;
        }
        if (value.length() < minLen) {
            return true;
        }
        return false;
    }

    public static boolean isOutOfLength(String value, int maxLen) {
        if (value == null) {
            return false;
        }
        if (value.length() > maxLen) {
            return true;
        }
        return false;
    }

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

    public static String subString(String str, int len) {
        if (str == null) {
            return null;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    /**
     * 识别英文的获取特定长度的字符串
     * 
     * @param s
     * @param len
     * @return 2010-8-5
     */
    public static String subSringEx(String s, int len) {
        // 逻辑为转换为字符数组遇到英文字符，长度+1，然后判断是否超出总长度，如果超出长度，就取到当前判断长度的位置的字符串
        if (isEmpty(s)) {
            return null;
        }
        if (s.length() <= len) {
            return s;
        }
        double _len = len;
        char[] ch = s.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (isEnChar(ch[i])) {
                _len = _len + 0.5;
                if (_len >= ch.length) {
                    break;
                }
            }
        }
        if (_len >= ch.length) {
            return s;
        }
        return s.substring(0, (int) _len);
    }

    private static boolean isEnChar(char ch) {
        if (ch >= 'A' && ch <= 'z') {
            return true;
        }
        return false;
    }

    /**
     * 过滤换行符
     * 
     * @param str
     * @return
     */
    public static String toRow(String str) {
        if (str != null) {
            return str.replaceAll("\n", "").replaceAll("\r", "");
        }
        return null;
    }

    /**
     * 把html数据转为转义字符，并消除换行
     * 
     * @param str
     * @return
     */
    public static String toHtmlRow(String str) {
        if (str == null) {
            return null;
        }
        String v = str.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("\"", "&quot;")
                .replaceAll("'", "&#39;");
        return v;
    }

    /**
     * 把html数据转为转义字符
     * 
     * @param str
     * @return
     */
    public static String toHtml(String str) {
        return toHtmlSimple(str);
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

    /**
     * 把html转义字符转为普通文本
     * 
     * @param str
     * @return
     */
    public static String toText(String str) {
        String v = null;
        if (str != null) {
            v = str.replaceAll("\r", "").replaceAll("\n", "")
                    .replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                    .replaceAll("<br/>", "\n").replaceAll("&amp;", "&")
                    .replaceAll("&nbsp;", " ").replaceAll("&quot;", "\"")
                    .replaceAll("&#39;", "'");
        }
        return v;
    }

    /**
     * 把html转义字符转为普通文本
     * 
     * @param str
     * @return
     */
    public static String toTextRow(String str) {
        String v = null;
        if (str != null) {
            v = str.replaceAll("\r", "").replaceAll("\n", "")
                    .replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                    .replaceAll("<br/>", "").replaceAll("&amp;", "&")
                    .replaceAll("&nbsp;", " ").replaceAll("&quot;", "\"")
                    .replaceAll("&#39;", "'");
        }
        return v;
    }

    public static String urlDecoder(String value) {
        if (value != null) {
            try {
                return URLDecoder.decode(value, DEFAULTCHARSET);
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }

    public static String urlDecoder(String value, String charset) {
        if (value != null) {
            try {
                return URLDecoder.decode(value, charset);
            }
            catch (UnsupportedEncodingException e) {
                System.out.println(e);
            }
        }
        return "";
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

    public static String getRandom(int len) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }

    public static int getRandomPageBegin(int count, int size) {
        int ncount = count;
        Random r = new Random();
        int begin = 0;
        if (ncount > size) {
            ncount = ncount - size + 1;
            while ((begin = r.nextInt(ncount)) < 0) {
            }
        }
        return begin;
    }

    public static <T> List<T> subList(List<T> list, int begin, int size) {
        if (list.size() - 1 < begin) {
            return new ArrayList<T>();
        }
        if (list.size() <= size) {
            return list.subList(begin, begin + list.size());
        }
        return list.subList(begin, begin + size);
    }
}