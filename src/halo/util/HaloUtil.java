package halo.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class HaloUtil implements ApplicationContextAware {

    private ApplicationContext applicationContext = null;

    private static HaloUtil haloUtil;

    public final Map<String, Object> objMap = new HashMap<String, Object>();

    public static HaloUtil getInstance() {
        return haloUtil;
    }

    public HaloUtil() {
        haloUtil = this;
    }

    public Object getBean(String name) {
        Object obj = objMap.get(name);
        if (obj == null) {
            obj = getBeanFromSpring(name);
            if (obj != null) {
                objMap.put(name, obj);
            }
        }
        return obj;
    }

    private synchronized Object getBeanFromSpring(String name) {
        Object obj = objMap.get(name);
        if (obj != null) {
            return obj;
        }
        try {
            return applicationContext.getBean(name);
        }
        catch (BeansException e) {
            return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
}