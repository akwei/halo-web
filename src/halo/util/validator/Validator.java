package halo.util.validator;

import halo.util.JsonObj;

public interface Validator {

    /**
     * 对数据进行验证，并返回验证结果
     * 
     * <pre>
     * 表达式写法，其中{}中的数据为expr
     * string{minlen:4,maxlen:20,empty:1,msg:\"hello string0\"}
     * string{minlen:4,maxlen:20,msg:\"hello string1\"}
     * email{minlen:4,maxlen:20,empty:0,msg:\"hello email\"}
     * number{min:2.6,max:3.9,msg:\"hello num\"}
     * <br/>
     * @param jsonObj 验证规则表达式的json表现形式
     * @param obj 待验证的数据
     * @return 返回不为空的数据表示验证失败，返回值表示失败信息 返回值为null表示验证成功
     */
    boolean exec(JsonObj jsonObj, Object obj);
}
