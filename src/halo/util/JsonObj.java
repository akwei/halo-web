package halo.util;

import java.util.Map;

public class JsonObj {

    private Map<String, String> map;

    public JsonObj(Map<String, String> map) {
        this.map = map;
    }

    public String getString(String key) {
        return this.map.get(key);
    }

    public int getInt(String key) {
        String v = this.map.get(key);
        if (v == null) {
            return 0;
        }
        try {
            return Integer.valueOf(v);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    public long getLong(String key) {
        String v = this.map.get(key);
        if (v == null) {
            return 0;
        }
        try {
            return Long.valueOf(v);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    public double getDouble(String key) {
        String v = this.map.get(key);
        if (v == null) {
            return 0;
        }
        try {
            return Double.valueOf(v);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    public float getFloat(String key) {
        String v = this.map.get(key);
        if (v == null) {
            return 0;
        }
        try {
            return Float.valueOf(v);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean getBoolean(String key) {
        String v = this.map.get(key);
        if (v == null) {
            return false;
        }
        try {
            return Boolean.valueOf(v);
        }
        catch (Exception e) {
            return false;
        }
    }
}