package halo.web.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 资源文件信息输出
 * 
 * @author akwei
 */
public class HaloResource {

    private static String resource = "resource";

    /**
     * 获取指定地域的配置文件中，对应key的内容输出
     * 
     * @param locale
     *            地域
     * @param resource
     *            资源文件的名称。资源文件强制为classes根目录下的文件
     * @param key
     *            指定的输出内容对应的key
     * @param args
     *            对内容中的占位符进行相应替换。没有占位符时，可以不传递此参数
     * @return 输出的字符内容
     */
    public static String getText(Locale locale, String resource, String key,
            Object... args) {
        ResourceBundle rb = ResourceBundle.getBundle(resource, locale);
        String value;
        try {
            if (args == null) {
                return rb.getString(key);
            }
            value = rb.getString(key);
        }
        catch (Exception e) {
            return key;
        }
        if (value == null) {
            return key;
        }
        return MessageFormat.format(value, args);
    }

    /**
     * 获取指定地域的配置文件中，对应key的内容输出。资源文件名称默认为"resource"
     * 
     * @param locale
     * @param key
     * @param args
     * @return
     */
    public static String getDefText(Locale locale, String key, Object... args) {
        return getText(locale, resource, key, args);
    }

    /**
     * 获取指定地域的配置文件中，对应key的内容输出。资源文件名称默认为resource，地域默认为
     * {@link Locale#SIMPLIFIED_CHINESE}
     * 
     * @param key
     * @param args
     * @return
     */
    public static String getDefText(String key, Object... args) {
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        return getDefText(locale, key, args);
    }
}