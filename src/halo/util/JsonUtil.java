package halo.util;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public final class JsonUtil {

    private JsonUtil() {
    }

    private static final Gson GSON = new Gson();

    private static final Type mapType = new TypeToken<Map<String, String>>() {
    }.getType();

    private static final Type listType = new TypeToken<List<String>>() {
    }.getType();

    public static String toJson(Map<String, String> map) {
        return GSON.toJson(map);
    }

    public static String toJson(List<String> list) {
        return GSON.toJson(list);
    }

    public static List<String> getListFromJson(String json) {
        return GSON.fromJson(json, listType);
    }

    public static Map<String, String> getMapFromJson(String json) {
        if (json == null) {
            return null;
        }
        return GSON.fromJson(json, mapType);
    }

    public static JsonObj getJsonObj(String json) {
        Map<String, String> map = getMapFromJson(json);
        return new JsonObj(map);
    }
}