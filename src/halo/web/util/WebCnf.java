package halo.web.util;

import halo.web.action.UploadFileCheckCnf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

/**
 * 当使用默认配置时，会使用此选项
 * 
 * @author akwei
 */
public class WebCnf implements InitializingBean {

    public static final String UPLOAD_LIMIT_SIZE_KEY = "COM.HALO.CACTUS_HTTPREQUEST_UPLOAD_LIMIT_SIZE_KEY";

    public static final String UPLOAD_EXCEEDEDSIZE_KEY = "COM.HALO.CACTUS_UPLOAD_EXCEEDEDSIZE_KEY";

    public static final String UPLOAD_ERROR_KEY = "COM.HALO.CACTUS_UPLOAD_ERROR_KEY";

    public static final String MULTI_HTTPSERVLETREQUEST_KEY = "COM.HALO.CACTUS_MULTI_HTTPSERVLETREQUEST";

    public static final String SIMPLEPAGE_ATTRIBUTE = "COM.HALO.CACTUS.SIMPLEPAGE.ATTRIBUTE";

    public static final String PAGESUPPORT_ATTRIBUTE = "COM.HALO.CACTUS.PAGESUPPORT.ATTRIBUTE";

    private static WebCnf webCnfObj;

    public static WebCnf getInstance() {
        return webCnfObj;
    }

    private String uploadFileTempPath = "/halotemp/";

    private List<String> fileUploadCheckUriCnfList;

    private final List<UploadFileCheckCnf> uploadFileCheckCnfs = new ArrayList<UploadFileCheckCnf>();

    private final Map<String, UploadFileCheckCnf> map = new HashMap<String, UploadFileCheckCnf>();

    private boolean mustCheckUpload;

    /**
     * 设置是否需要进行字符转码
     */
    public void setNeedCharsetEncode(boolean needCharsetEncode) {
        ServletUtil.NEED_CHARSET_ENCODE = needCharsetEncode;
    }

    /**
     * 源数据编码，如果不设置编码，默认将会对get方式的url进行iso-8859-1到utf-8编码的转换
     * 
     * @param sourceCharset
     */
    public void setSourceCharset(String sourceCharset) {
        if (sourceCharset == null || sourceCharset.length() == 0) {
            return;
        }
        ServletUtil.CHARSET_SOURCE = sourceCharset;
    }

    /**
     * 目标数据编码，如果不设置编码，默认将会对get方式的url进行iso-8859-1到utf-8编码的转换
     * 
     * @param targetCharset
     */
    public void setTargetCharset(String targetCharset) {
        if (targetCharset == null || targetCharset.length() == 0) {
            return;
        }
        ServletUtil.CHARSET_TARGET = targetCharset;
    }

    /**
     * 设置是否强制检查允许上传的uri，只有配置过的uri才能接受文件上传，否则不允许文件上传，并且传递的参数也无法获取
     * 
     * @param mustCheckUpload
     */
    public void setMustCheckUpload(boolean mustCheckUpload) {
        this.mustCheckUpload = mustCheckUpload;
    }

    /**
     * 设置文件上传的临时目录
     * 
     * @param uploadFileTempPath
     */
    public void setUploadFileTempPath(String uploadFileTempPath) {
        if (!uploadFileTempPath.endsWith("/")) {
            this.uploadFileTempPath = uploadFileTempPath + "/";
        }
        else {
            this.uploadFileTempPath = uploadFileTempPath;
        }
    }

    public String getUploadFileTempPath() {
        return uploadFileTempPath;
    }

    public boolean isMustCheckUpload() {
        return mustCheckUpload;
    }

    /**
     * 设置文件上传所允许通过的urlmapping以及允许上传文件的大小以MB来计算
     * 
     * <pre>
     * 配置示例:
     *  /user/head/upload:2 , uri最后忽略.do 等后缀
     * </pre>
     * 
     * @param fileUploadCheckUriList
     */
    public void setFileUploadCheckUriCnfList(
            List<String> fileUploadCheckUriCnfList) {
        this.fileUploadCheckUriCnfList = fileUploadCheckUriCnfList;
    }

    public List<String> getFileUploadCheckUriCnfList() {
        return fileUploadCheckUriCnfList;
    }

    private void initUploadFileCheckCnf() {
        if (this.fileUploadCheckUriCnfList == null) {
            return;
        }
        for (String cnf : this.fileUploadCheckUriCnfList) {
            String[] s = cnf.split(":");
            if (s.length != 2) {
                continue;
            }
            int maxSize = Integer.valueOf(s[1]);
            if (maxSize > 0) {
                UploadFileCheckCnf o = new UploadFileCheckCnf(maxSize, s[0]);
                this.uploadFileCheckCnfs.add(o);
                map.put(s[0], o);
            }
        }
    }

    public UploadFileCheckCnf getUploadFileCheckCnf(String mappingUri) {
        return map.get(mappingUri);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        WebCnf.webCnfObj = this;
        this.initUploadFileCheckCnf();
    }
}