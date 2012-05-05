package halo.web.action;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

/**
 * 异常处理配置
 * 
 * @author akwei
 */
public class ExceptionConfig implements InitializingBean {

    private static Map<String, String> exceptionMap = null;

    private static boolean freeze;

    public void setExceptionMap(Map<String, String> exceptionMap) {
        if (freeze) {
            throw new RuntimeException("exceptionMap freeze");
        }
        ExceptionConfig.exceptionMap = exceptionMap;
    }

    public static String getExceptionForward(String exceptionClassName) {
        if (exceptionMap == null) {
            return null;
        }
        return exceptionMap.get(exceptionClassName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        freeze = true;
    }
}