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

    public static String getText(String resource, String key, Object... args) {
        return HaloResource.getText(null, resource, key, args);
    }

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
        ResourceBundle rb = null;
        if (locale == null) {
            rb = ResourceBundle.getBundle(resource);
        }
        else {
            rb = ResourceBundle.getBundle(resource, locale);
        }
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
}