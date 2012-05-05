package halo.web.action;

/**
 * 对上传文件进行检查，某个url是否可以上传，并且最大上传限制为多少
 * 
 * @author akwei
 */
public class UploadFileCheckCnf {

    private int maxSize;

    private String uri;

    /**
     * @param maxSize
     *            接受文件的最大限制(单位M)
     * @param uri
     *            去除contextPath之后的uri
     */
    public UploadFileCheckCnf(int maxSize, String uri) {
        this.maxSize = maxSize;
        this.uri = uri;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}