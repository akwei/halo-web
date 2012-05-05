package halo.web.action.actionmapping;

/**
 * 保存了uri中对应的Action与调用的method名称
 * 
 * @author akwei
 */
public class ActionURIMapping {

    private String actionName;

    private String methodName;

    public ActionURIMapping() {
    }

    public ActionURIMapping(String actionName, String methodName) {
        this.actionName = actionName;
        this.methodName = methodName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
