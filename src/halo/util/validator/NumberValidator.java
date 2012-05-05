package halo.util.validator;

import halo.util.DataUtil;
import halo.util.JsonObj;

import java.math.BigDecimal;

/**
 * 数字验证表达式写法
 * 
 * <pre>
 * number{min=2.6;max=3.9}message
 * 
 * <pre>
 * 
 * @author akwei
 */
public class NumberValidator implements Validator {

    @Override
    public boolean exec(JsonObj jsonObj, Object obj) {
        BigDecimal min = null;
        BigDecimal max = null;
        String s_min = jsonObj.getString("min");
        String s_max = jsonObj.getString("max");
        if (DataUtil.isNotEmpty(s_min)) {
            min = new BigDecimal(s_min);
        }
        if (DataUtil.isNotEmpty(s_max)) {
            max = new BigDecimal(s_max);
        }
        // 验证数据
        if (obj == null) {
            return false;
        }
        BigDecimal v;
        try {
            v = new BigDecimal(obj.toString());
        }
        catch (Exception e) {
            throw new IllegalExpressionException(e);
        }
        if (min != null) {
            int res = v.compareTo(min);
            if (res == -1) {
                return false;
            }
        }
        if (max != null) {
            int res = v.compareTo(max);
            if (res == 1) {
                return false;
            }
        }
        return true;
    }
}
