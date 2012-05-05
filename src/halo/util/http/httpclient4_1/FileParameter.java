package halo.util.http.httpclient4_1;

import java.io.File;

public class FileParameter {

    private String name;

    private File file;

    public FileParameter(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }
}