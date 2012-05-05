package halo.web.action.actionmapping;

/**
 * @author akwei
 */
public class ActionURIMappingCreater {

    /**
     * 解析mappingUri获取 actionName methodName
     * 
     * @param mappingUri
     *            去除 contextPath后的uri
     * @return
     */
    public static ActionURIMapping parse(String mappingUri) {
        int idx = mappingUri.lastIndexOf('/');
        ActionURIMapping actionURIMapping = new ActionURIMapping(
                mappingUri.substring(0, idx), mappingUri.substring(idx + 1,
                        mappingUri.length()));
        return actionURIMapping;
    }
}
