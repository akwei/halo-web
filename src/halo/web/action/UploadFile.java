package halo.web.action;

import java.io.File;

public class UploadFile {

    private String name;

    private File file;

    private String originName;

    public UploadFile(String name, File file, String originName) {
        this.name = name;
        this.originName = originName;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }
}