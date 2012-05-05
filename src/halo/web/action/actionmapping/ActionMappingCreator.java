package halo.web.action.actionmapping;

import halo.web.action.NoActionException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ActionMappingCreator {

    /**
     * ActionMapping缓存
     */
    private static final Map<String, AsmActionMapping> mappingMap = new HashMap<String, AsmActionMapping>();

    /**
     * 寻找与uri对应的ActionMapper. uri与{@link ActionMapping} 一对一匹配.
     * 
     * @param mappingUri
     * @return
     * @throws NoActionException
     * @throws NoSuchMethodException
     */
    public static synchronized AsmActionMapping getAsmActionMapping(
            String mappingUri) throws NoActionException, NoSuchMethodException {
        AsmActionMapping mapping = mappingMap.get(mappingUri);
        if (mapping == null) {
            try {
                mapping = parseAsmActionMapping(mappingUri);
                mappingMap.put(mappingUri, mapping);
            }
            catch (NoActionException e) {
                throw e;
            }
            catch (NoSuchMethodException e) {
                throw e;
            }
        }
        return mapping;
    }

    /**
     * 解析uri,获得AsmActionMapping，"/"作为Action与方法名的分割.例如/user/list,
     * 对应的action为UserAction，list是action中的一个方法。
     * 
     * @param mappingUri
     * @return
     * @throws NoActionException
     * @throws NoSuchMethodException
     */
    private static AsmActionMapping parseAsmActionMapping(String mappingUri)
            throws NoActionException, NoSuchMethodException {
        ActionURIMapping actionURIMapping = ActionURIMappingCreater
                .parse(mappingUri);
        Object action = DefActionFinder.findAction(actionURIMapping
                .getActionName());
        Method actionMethod = ActionMethodFinder.find(action,
                actionURIMapping.getMethodName());
        ActionMapping actionMapping = new ActionMapping();
        actionMapping.setActionName(actionURIMapping.getActionName());
        actionMapping.setAction(action);
        actionMapping.setMethodName(actionURIMapping.getMethodName());
        actionMapping.setActionMethod(actionMethod);
        return new AsmActionMapping(actionMapping);
    }
}
