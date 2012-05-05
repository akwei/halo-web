package halo.web.action;

import halo.web.util.PageSupport;
import halo.web.util.SimplePage;

import java.io.File;
import java.text.DateFormat;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public interface HkRequest extends HttpServletRequest {

    Object getSessionValue(String name);

    Number getNumber(String key);

    Number getNumber(String key, Number num);

    String getString(String key);

    String getStringRow(String key);

    String getStringRow(String key, String def);

    String getStringAndSetAttr(String key);

    String getEncodeString(String key);

    String getString(String key, String str);

    String[] getStrings(String key);

    Number[] getNumbers(String key);

    int[] getInts(String key);

    long[] getLongs(String key);

    int getInt(String key);

    int getIntAndSetAttr(String key);

    boolean getBoolean(String key);

    int getInt(String key, int num);

    long getLong(String key);

    long getLongAndSetAttr(String key);

    long getLong(String key, long num);

    double getDouble(String key);

    double getDoubleAndSetAttr(String key);

    double getDouble(String key, double num);

    float getFloat(String key);

    float getFloat(String key, float num);

    short getShort(String key, short num);

    short getShort(String key);

    byte getByte(String key);

    byte getByteAndSetAttr(String key);

    byte getByteAndSetAttr(String key, byte num);

    byte getByte(String key, byte num);

    File getFile(String name);

    File[] getFiles();

    UploadFile[] getUploadFiles();

    UploadFile getUploadFile(String name);

    String getOriginalFileName(String name);

    Cookie getCookie(String name);

    PageSupport getPageSupport(int page, int size);

    PageSupport getPageSupport(int size);

    int getPage();

    void setMessage(String msg);

    void setSessionMessage(String msg);

    void setSessionAttr(String name, Object value);

    void removeSessionAttr(String name);

    void invalidateSession();

    SimplePage getSimplePage(int size);

    void reSetAttribute(String name);

    void reSetEncodeAttribute(String name);

    void setEncodeAttribute(String name, String value);

    void setEncodeAttribute(String name, String value, String charset);

    void setUploadFiles(UploadFile[] uploadFiles);

    /**
     * 仅在有文件上传时才能正确判断。上传文件是否超出限制，当content-length>0时可用。<0时，文件会截取到最大限制部分
     * 
     * @return
     */
    boolean isUploadExceedSize();

    /**
     * 对给定对象属性通过request赋值，不存在的parameter将不会赋值
     * 
     * @param t
     */
    <T> void buildBean(T t);

    /**
     * 对给定对象属性通过request赋值，不存在的parameter将不会赋值
     * 
     * @param t
     * @param exceptParameters
     *            不通过patameter进行赋值的属性名称
     */
    <T> void buildBean(T t, String[] exceptParameters);

    /**
     * 设置获得日期的格式化类
     * 
     * @param dateFormat
     */
    void setDateFormat(DateFormat dateFormat);
}