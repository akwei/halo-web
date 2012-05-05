package halo.web.action.actionmapping;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import java.lang.reflect.Method;

public class ActionMethodFinder {

    public static Method find(Object action, String methodName)
            throws NoSuchMethodException {
        return action.getClass().getMethod(methodName,
                new Class[] { HkRequest.class, HkResponse.class });
    }
}