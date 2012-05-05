package halo.web.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HaloI18n {

    public static final String I18N_KEY = "com_halo_sys_i18n_key";

    public static final int SIMPLIFIED_CHINESE = 0;

    public static final int TRADITIONAL_CHINESE = 1;

    public static final int EN = 2;

    public static final int FRANCE = 3;

    public static final int GERMANY = 4;

    public static final int ITALY = 5;

    public static final int JAPAN = 6;

    public static final int KOREA = 7;

    private static final Map<Integer, Locale> local_i18n_map = new HashMap<Integer, Locale>();
    static {
        local_i18n_map.put(SIMPLIFIED_CHINESE, Locale.SIMPLIFIED_CHINESE);
        local_i18n_map.put(EN, Locale.US);
        local_i18n_map.put(FRANCE, Locale.FRANCE);
        local_i18n_map.put(GERMANY, Locale.GERMANY);
        local_i18n_map.put(ITALY, Locale.ITALY);
        local_i18n_map.put(JAPAN, Locale.JAPAN);
        local_i18n_map.put(KOREA, Locale.KOREA);
    }

    /**
     * 根据参数值获得语言数据
     * 
     * @param arg
     * @param showDefault
     *            如果没有找到相对应的语言设置，就返回默认
     * @return 2010-6-3
     */
    public static Locale getLocale(int arg, boolean showDefault) {
        Locale locale = local_i18n_map.get(arg);
        if (locale == null) {
            locale = Locale.SIMPLIFIED_CHINESE;
        }
        return locale;
    }
}