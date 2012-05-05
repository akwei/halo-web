package halo.util.validator;

import halo.util.DataUtil;
import halo.util.JsonObj;

/**
 * string 表达式写法
 * 
 * <pre>
 * 
 * string{min=4;max=20;empty=true}msg
 * string{min=4;max=20}msg
 * 
 * <pre>
 * @author akwei
 */
public class StringValidator implements Validator {

    @Override
    public boolean exec(JsonObj jsonObj, Object obj) {
        int minlen = 0;
        int maxlen = -1;
        boolean canEmpty = false;
        String s_minlen = jsonObj.getString("minlen");
        String s_maxlen = jsonObj.getString("maxlen");
        String s_empty = jsonObj.getString("empty");
        if (DataUtil.isNotEmpty(s_minlen)) {
            minlen = Integer.valueOf(s_minlen);
        }
        if (DataUtil.isNotEmpty(s_maxlen)) {
            maxlen = Integer.valueOf(s_maxlen);
        }
        if (DataUtil.isNotEmpty(s_empty) && s_empty.equals("1")) {
            canEmpty = true;
        }
        // 数据验证
        if (!canEmpty) {
            String v = (String) obj;
            if (DataUtil.isEmpty(v)) {
                return false;
            }
        }
        if (obj == null) {
            return true;
        }
        String v = obj.toString();
        if (DataUtil.isEmpty(v)) {
            return true;
        }
        if (v.length() < minlen) {
            return false;
        }
        if (maxlen > -1 && v.length() > maxlen) {
            return false;
        }
        return true;
    }
}