package halo.web.action.actionmapping;

import halo.web.action.ASMAction;

import java.lang.reflect.Field;

public class AsmActionMapping {

    private ActionMapping actionMapping;

    private ASMAction asmAction;

    public AsmActionMapping(ActionMapping actionMapping) {
        this.actionMapping = actionMapping;
        this.createAsmAction();
    }

    /**
     * 动态创建action子类，子类调用父类指定的方法
     * 
     * @param actionMapping
     * @return
     */
    private void createAsmAction() {
        ASMActionCreater asmActionCreater = new ASMActionCreater();
        Class<Object> clazz = asmActionCreater.create(actionMapping);
        try {
            Object obj = clazz.getConstructor().newInstance();
            this.asmAction = (ASMAction) obj;
            Field field = asmAction.getClass().getDeclaredField("action");
            field.setAccessible(true);
            field.set(asmAction, actionMapping.getAction());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ASMAction getAsmAction() {
        return asmAction;
    }
}