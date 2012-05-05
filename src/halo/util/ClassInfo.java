package halo.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author akwei
 */
public class ClassInfo<T> {

    private Class<T> clazz;

    private Field[] fields;

    private final Map<String, Field> map = new HashMap<String, Field>();

    public ClassInfo(Class<T> clazz) {
        this.clazz = clazz;
        this.fields = clazz.getDeclaredFields();
        for (Field field : this.fields) {
            field.setAccessible(true);
            map.put(field.getName(), field);
        }
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public Field[] getFields() {
        return fields;
    }

    public Field getField(String name) {
        return map.get(name);
    }
}
