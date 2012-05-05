package halo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akwei
 */
public class ClassInfoFactory {

    private static final Map<String, ClassInfo<?>> map = new HashMap<String, ClassInfo<?>>();

    @SuppressWarnings("unchecked")
    public static synchronized <T> ClassInfo<T> getClassInfo(Class<T> clazz) {
        ClassInfo<T> o = (ClassInfo<T>) map.get(clazz.getName());
        if (o == null) {
            o = createClassInfo(clazz);
            map.put(clazz.getName(), o);
        }
        return o;
    }

    private static <T> ClassInfo<T> createClassInfo(Class<T> clazz) {
        return new ClassInfo<T>(clazz);
    }
}