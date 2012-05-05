package halo.util.validator;

import halo.util.DataUtil;
import halo.util.JsonObj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidator implements Validator {

    private final static String fmt = "yyyy-MM-dd HH:mm:ss";

    @Override
    public boolean exec(JsonObj jsonObj, Object obj) {
        String s_min = jsonObj.getString("min");
        String s_max = jsonObj.getString("max");
        String s_empty = jsonObj.getString("empty");
        Date min = null;
        Date max = null;
        boolean canEmpty = false;
        if (DataUtil.isNotEmpty(s_empty) && s_empty.equals("1")) {
            canEmpty = true;
        }
        if (canEmpty && obj == null) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        Date now = new Date();
        // 解析
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        try {
            if (DataUtil.isNotEmpty(s_min)) {
                if (s_min.equals("now")) {
                    min = now;
                }
                else {
                    if (s_min.trim().length() == 10) {
                        s_min = s_min.trim() + " 00:00:00";
                    }
                    min = sdf.parse(s_min);
                }
            }
            if (DataUtil.isNotEmpty(s_max)) {
                if (s_max.equals("now")) {
                    max = now;
                }
                else {
                    if (s_max.trim().length() == 10) {
                        s_max = s_max.trim() + " 00:00:00";
                    }
                    max = sdf.parse(s_max);
                }
            }
        }
        catch (ParseException e) {
            throw new IllegalExpressionException(e);
        }
        // 解析完成
        // 开始验证数据
        Date value = (Date) obj;
        if (min != null && value.getTime() < min.getTime()) {
            return false;
        }
        if (max != null && value.getTime() > max.getTime()) {
            return false;
        }
        return true;
    }
}