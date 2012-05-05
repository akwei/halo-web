package halo.web.action;

import halo.util.FileUtil;
import halo.web.action.actionmapping.ActionMappingCreator;
import halo.web.action.actionmapping.AsmActionMapping;
import halo.web.util.HaloWebUtil;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * mvc程序处理的核心类，对匹配的url进行处理并返回处路径
 * 
 * @author akwei
 */
public class ActionExe {

    /**
     * 处理对应mappingUri的action
     * 
     * @param mappingUri
     *            去除contextPath，后缀之后剩下的部分<br>
     *            例如：[requestContextPath]/user/list.do,mappingUri=/user/list
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public static String invoke(String mappingUri, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        HkRequest hkRequest = HaloWebUtil.getHkRequest(request);
        HkResponse hkResponse = HaloWebUtil.getHkResponse(response);
        try {
            AsmActionMapping actionMapping = ActionMappingCreator
                    .getAsmActionMapping(mappingUri);
            return actionMapping.getAsmAction().execute(hkRequest, hkResponse);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            afterProcess(hkRequest);
        }
    }

    public static void afterProcess(HkRequest request) {
        deleteFiles(request);
    }

    private static boolean deleteFiles(HkRequest request) {
        File[] files = request.getFiles();
        if (files != null) {
            for (File f : files) {
                if (!FileUtil.deleteFile(f)) {
                    continue;
                }
            }
        }
        return true;
    }
}