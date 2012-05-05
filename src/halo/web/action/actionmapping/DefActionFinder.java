package halo.web.action.actionmapping;

import halo.util.HaloUtil;
import halo.web.action.NoActionException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;

/**
 * 查找URI中解析出的Action对象<br/>
 * 除去request.getContextPath部分与除去后缀部分剩下的mappingUri<br/>
 * 例如：app/user/set/action/list.do <br/>
 * actionUrl为/user/set/action/list action就是需要查找的对象的key,list为action的方法
 * 
 * @author akwei
 */
public class DefActionFinder {

    private static final Map<String, Object> actionMap = new HashMap<String, Object>();

    /**
     * 查找Action 对象，从spring bean容器中获取
     * 
     * @param actionName
     * @return
     * @throws NoActionException
     *             没有对应的action对象时抛出此异常
     */
    public static Object findAction(String actionName) throws NoActionException {
        Object obj = actionMap.get(actionName);
        if (obj == null) {
            obj = getBeanFromSpring(actionName);
            if (obj != null) {
                actionMap.put(actionName, obj);
            }
            else {
                throw new NoActionException("no action [ " + actionName
                        + " ] is exist");
            }
        }
        return obj;
    }

    /**
     * 从spring中获取action对象
     * 
     * @param name
     * @return
     */
    private static synchronized Object getBeanFromSpring(String name) {
        Object obj = actionMap.get(name);
        if (obj == null) {
            try {
                obj = HaloUtil.getInstance().getBean(name);
            }
            catch (BeansException e) {
                return null;
            }
            actionMap.put(name, obj);
        }
        return obj;
    }
}
