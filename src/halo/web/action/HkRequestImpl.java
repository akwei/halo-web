package halo.web.action;

import halo.util.ClassInfo;
import halo.util.ClassInfoFactory;
import halo.util.DataUtil;
import halo.web.action.upload.cos.ExceededSizeException;
import halo.web.util.MessageUtil;
import halo.web.util.PageSupport;
import halo.web.util.ServletUtil;
import halo.web.util.SimplePage;
import halo.web.util.WebCnf;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HkRequestImpl extends HttpServletRequestWrapper implements
        HkRequest {

    private final Log log = LogFactory.getLog(HkRequestImpl.class);

    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String TYPE_LONG = "long";

    private static final String TYPE_INT = "int";

    private static final String TYPE_BYTE = "byte";

    private static final String TYPE_SHORT = "short";

    private static final String TYPE_FLOAT = "float";

    private static final String TYPE_DOUBLE = "double";

    private static final String TYPE_STRING = String.class.getName();

    private static final String TYPE_DATE = Date.class.getName();

    private HttpServletRequest httpServletRequest;

    private UploadFile[] uploadFiles;

    private File[] files;

    private Map<String, UploadFile> uploadFileMap = null;

    public HkRequestImpl(HttpServletRequest request) {
        super(request);
        this.initHttpRequest((HttpServletRequest) this.getRequest());
    }

    private void processNoCheckUpload(HttpServletRequest request, WebCnf webCnf) {
        try {
            FileUploader fileUpload = new FileUploader(request,
                    webCnf.getUploadFileTempPath(), 0);
            this.httpServletRequest = fileUpload.getHkMultiRequest();
            this.setUploadFiles(fileUpload.getUploadFiles());
        }
        catch (ExceededSizeException e) {
            this.httpServletRequest = request;
            request.setAttribute(WebCnf.UPLOAD_EXCEEDEDSIZE_KEY, true);
        }
        catch (IOException e) {
            this.httpServletRequest = request;
            request.setAttribute(WebCnf.UPLOAD_ERROR_KEY, true);
        }
    }

    private void processCheckUpload(HttpServletRequest request, WebCnf webCnf) {
        UploadFileCheckCnf checkCnf = webCnf
                .getUploadFileCheckCnf(MappingUriCreater.findMappingUri(
                        request.getRequestURI(), request.getContextPath()));
        // 严格检查upload mappinguri,只有经过配置后的mappinguri才允许上传文件
        // mappinguri, 例如 uri: {appctx}/user/set_head.do
        // mappinguri: /user/set_head
        if (checkCnf == null) {
            // 如果没有对应配置的uri，忽略文件上传
            this.httpServletRequest = request;
            return;
        }
        try {
            FileUploader fileUpload = new FileUploader(request,
                    webCnf.getUploadFileTempPath(), checkCnf.getMaxSize());
            this.httpServletRequest = fileUpload.getHkMultiRequest();
            this.setUploadFiles(fileUpload.getUploadFiles());
        }
        catch (ExceededSizeException e) {
            this.httpServletRequest = request;
            request.setAttribute(WebCnf.UPLOAD_EXCEEDEDSIZE_KEY, true);
        }
        catch (IOException e) {
            this.httpServletRequest = request;
            request.setAttribute(WebCnf.UPLOAD_ERROR_KEY, true);
        }
    }

    private void initHttpRequest(HttpServletRequest request) {
        if (this.httpServletRequest == null) {
            if (ServletUtil.isMultipart(request)) {
                WebCnf webCnf = WebCnf.getInstance();
                if (webCnf.isMustCheckUpload()) {
                    this.processCheckUpload(request, webCnf);
                }
                else {
                    this.processNoCheckUpload(request, webCnf);
                }
            }
            else {
                this.httpServletRequest = (HttpServletRequest) this
                        .getRequest();
            }
        }
    }

    public HttpServletRequest getHttpServletRequest() {
        return this.httpServletRequest;
    }

    @Override
    public void setUploadFiles(UploadFile[] uploadFiles) {
        if (uploadFiles == null) {
            return;
        }
        this.uploadFiles = uploadFiles;
        if (this.uploadFileMap == null) {
            this.uploadFileMap = new HashMap<String, UploadFile>();
        }
        if (this.files == null) {
            this.files = new File[this.uploadFiles.length];
        }
        for (int i = 0; i < this.uploadFiles.length; i++) {
            this.files[i] = this.uploadFiles[i].getFile();
            this.uploadFileMap.put(this.uploadFiles[i].getName(),
                    this.uploadFiles[i]);
        }
    }

    @Override
    public boolean getBoolean(String key) {
        String s = this.getString(key);
        if (s == null) {
            return false;
        }
        return Boolean.parseBoolean(s);
    }

    @Override
    public byte getByte(String key) {
        return this.getNumber(key).byteValue();
    }

    @Override
    public byte getByte(String key, byte num) {
        return this.getNumber(key, num).byteValue();
    }

    @Override
    public Cookie getCookie(String name) {
        return ServletUtil.getCookie(getHttpServletRequest(), name);
    }

    @Override
    public double getDouble(String key) {
        return this.getNumber(key).doubleValue();
    }

    @Override
    public double getDoubleAndSetAttr(String key) {
        double v = this.getDouble(key);
        this.setAttribute(key, v);
        return v;
    }

    @Override
    public double getDouble(String key, double num) {
        return this.getNumber(key, num).doubleValue();
    }

    @Override
    public File getFile(String name) {
        UploadFile uploadFile = this.getUploadFile(name);
        if (uploadFile == null) {
            return null;
        }
        return uploadFile.getFile();
    }

    @Override
    public File[] getFiles() {
        return this.files;
    }

    @Override
    public UploadFile getUploadFile(String name) {
        if (this.uploadFileMap != null) {
            return this.uploadFileMap.get(name);
        }
        return null;
    }

    @Override
    public UploadFile[] getUploadFiles() {
        this.getHttpServletRequest();
        return this.uploadFiles;
    }

    @Override
    public String getOriginalFileName(String name) {
        UploadFile uploadFile = this.getUploadFile(name);
        if (uploadFile != null) {
            return uploadFile.getOriginName();
        }
        return null;
    }

    @Override
    public float getFloat(String key) {
        return this.getNumber(key).floatValue();
    }

    @Override
    public float getFloat(String key, float num) {
        return this.getNumber(key, num).floatValue();
    }

    @Override
    public int getInt(String key) {
        return this.getNumber(key).intValue();
    }

    @Override
    public short getShort(String key) {
        return this.getNumber(key).shortValue();
    }

    @Override
    public short getShort(String key, short num) {
        return this.getNumber(key, num).shortValue();
    }

    @Override
    public int getInt(String key, int num) {
        return this.getNumber(key, num).intValue();
    }

    @Override
    public long getLong(String key) {
        return this.getNumber(key).longValue();
    }

    @Override
    public long getLong(String key, long num) {
        return this.getNumber(key, num).longValue();
    }

    @Override
    public Number getNumber(String key) {
        return ServletUtil.getNumber(getHttpServletRequest(), key);
    }

    @Override
    public Number getNumber(String key, Number num) {
        return ServletUtil.getNumber(getHttpServletRequest(), key, num);
    }

    @Override
    public Number[] getNumbers(String key) {
        return ServletUtil.getNumbers(getHttpServletRequest(), key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Enumeration<String> getParameterNames() {
        return this.getHttpServletRequest().getParameterNames();
    }

    @Override
    public int[] getInts(String key) {
        Number[] n = getNumbers(key);
        if (n == null) {
            return null;
        }
        int[] ii = new int[n.length];
        for (int i = 0; i < n.length; i++) {
            ii[i] = n[i].intValue();
        }
        return ii;
    }

    @Override
    public long[] getLongs(String key) {
        Number[] n = getNumbers(key);
        if (n == null) {
            return null;
        }
        long[] ii = new long[n.length];
        for (int i = 0; i < n.length; i++) {
            ii[i] = n[i].longValue();
        }
        return ii;
    }

    @Override
    public Object getSessionValue(String name) {
        return ServletUtil.getSessionValue(getHttpServletRequest(), name);
    }

    @Override
    public String getString(String key) {
        return ServletUtil.getString(getHttpServletRequest(), key);
    }

    @Override
    public String getStringRow(String key) {
        String v = this.getString(key);
        if (v != null) {
            return v.replaceAll("\n", "").replaceAll("\r", "");
        }
        return null;
    }

    @Override
    public String getStringAndSetAttr(String key) {
        String s = this.getString(key);
        if (s != null) {
            this.setAttribute(key, s);
        }
        return s;
    }

    @Override
    public String getParameter(String name) {
        return this.getString(name);
    }

    @Override
    public String getEncodeString(String key) {
        String s = this.getString(key, "");
        return DataUtil.urlEncoder(s);
    }

    @Override
    public String getString(String key, String str) {
        String t = this.getString(key);
        if (t == null) {
            return str;
        }
        return t;
    }

    @Override
    public String getStringRow(String key, String def) {
        String v = this.getString(key, def);
        if (v != null) {
            return v.replaceAll("\n", "").replaceAll("\r", "");
        }
        return null;
    }

    @Override
    public String[] getStrings(String key) {
        return this.getHttpServletRequest().getParameterValues(key);
    }

    @Override
    public void setSessionAttr(String name, Object value) {
        ServletUtil.setSessionAttr(getHttpServletRequest(), name, value);
    }

    @Override
    public int getPage() {
        return ServletUtil.getPage(getHttpServletRequest());
    }

    @Override
    public PageSupport getPageSupport(int page, int size) {
        PageSupport pageSupport = PageSupport.getInstance(this.getPage(), size);
        getHttpServletRequest().setAttribute(WebCnf.PAGESUPPORT_ATTRIBUTE,
                pageSupport);
        return pageSupport;
    }

    @Override
    public PageSupport getPageSupport(int size) {
        return this.getPageSupport(this.getPage(), size);
    }

    @Override
    public void setMessage(String msg) {
        this.setAttribute(MessageUtil.MESSAGE_NAME, msg);
    }

    @Override
    public void setSessionMessage(String msg) {
        ServletUtil.setSessionMessage(getHttpServletRequest(), msg);
    }

    @Override
    public void removeSessionAttr(String name) {
        this.getHttpServletRequest().getSession().removeAttribute(name);
    }

    @Override
    public void invalidateSession() {
        this.getHttpServletRequest().getSession().invalidate();
    }

    public int getPageBegin(int size) {
        int page = this.getPage();
        getHttpServletRequest().setAttribute("page", page);
        return (page - 1) * size;
    }

    @Override
    public SimplePage getSimplePage(int size) {
        return ServletUtil.getSimplePage(getHttpServletRequest(), size);
    }

    @Override
    public void reSetAttribute(String name) {
        if (this.getAttribute(name) != null) {
            return;
        }
        String o = this.getString(name);
        if (o != null) {
            this.setAttribute(name, o);
        }
    }

    @Override
    public void setEncodeAttribute(String name, String value) {
        this.setAttribute(name, value);
        if (value != null) {
            this.setAttribute("enc_" + name, DataUtil.urlEncoder(value));
        }
    }

    @Override
    public void setEncodeAttribute(String name, String value, String charset) {
        this.setAttribute(name, value);
        if (value != null) {
            this.setAttribute("enc_" + name,
                    DataUtil.urlEncoder(value, charset));
        }
    }

    @Override
    public void reSetEncodeAttribute(String name) {
        String value = this.getString(name, "");
        this.setAttribute(name, value);
        this.setEncodeAttribute("enc_" + name, value);
    }

    @Override
    public String getRemoteAddr() {
        return ServletUtil.getRemoteAddr(getHttpServletRequest());
    }

    @Override
    public byte getByteAndSetAttr(String key) {
        byte v = this.getByte(key);
        this.setAttribute(key, v);
        return v;
    }

    @Override
    public byte getByteAndSetAttr(String key, byte num) {
        byte v = this.getByte(key, num);
        this.setAttribute(key, v);
        return v;
    }

    @Override
    public int getIntAndSetAttr(String key) {
        int v = this.getInt(key);
        this.setAttribute(key, v);
        return v;
    }

    @Override
    public long getLongAndSetAttr(String key) {
        long v = this.getLong(key);
        this.setAttribute(key, v);
        return v;
    }

    @Override
    public boolean isUploadExceedSize() {
        Boolean b = (Boolean) this.getAttribute(WebCnf.UPLOAD_EXCEEDEDSIZE_KEY);
        if (b == null) {
            return false;
        }
        return b.booleanValue();
    }

    private DateFormat dateFormat;

    @Override
    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public <T> void buildBean(T t) {
        this.buildBean(t, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void buildBean(T t, String[] exceptParameters) {
        Set<String> set = new HashSet<String>();
        List<String> list = new ArrayList<String>();
        if (exceptParameters != null) {
            for (String s : exceptParameters) {
                set.add(s);
            }
        }
        Enumeration<String> e = this.getParameterNames();
        while (e.hasMoreElements()) {
            String v = e.nextElement();
            if (!set.contains(v)) {
                list.add(v);
            }
        }
        ClassInfo<T> classInfo = (ClassInfo<T>) ClassInfoFactory.getClassInfo(t
                .getClass());
        for (String name : list) {
            Field field = classInfo.getField(name);
            if (field != null) {
                this.setFieldValue(field, t);
            }
        }
    }

    private <T> void setFieldValue(Field field, T t) {
        String v = this.getParameter(field.getName());
        if (v == null) {
            return;
        }
        String type = field.getType().getName();
        try {
            if (type.equals(TYPE_BYTE)) {
                field.setByte(t, this.getByte(field.getName()));
            }
            else if (type.equals(TYPE_DATE)) {
                field.set(
                        t,
                        this.getDateFormat().parse(
                                this.getString(field.getName())));
            }
            else if (type.equals(TYPE_DOUBLE)) {
                field.setDouble(t, this.getDouble(field.getName()));
            }
            else if (type.equals(TYPE_FLOAT)) {
                field.setFloat(t, this.getFloat(field.getName()));
            }
            else if (type.equals(TYPE_INT)) {
                field.setInt(t, this.getInt(field.getName()));
            }
            else if (type.equals(TYPE_LONG)) {
                field.setLong(t, this.getLong(field.getName()));
            }
            else if (type.equals(TYPE_SHORT)) {
                field.setShort(t, this.getShort(field.getName()));
            }
            else if (type.equals(TYPE_STRING)) {
                field.set(t, this.getString(field.getName(), ""));
            }
        }
        catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
        }
        return dateFormat;
    }
}