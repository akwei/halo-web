package halo.util.http.httpclient4_1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HttpParameter {

    private final List<BasicParameter> parameters = new ArrayList<BasicParameter>();

    private final List<FileParameter> fileParameters = new ArrayList<FileParameter>(
            0);

    public void add(String name, String value) {
        this.parameters.add(new BasicParameter(name, value));
    }

    public void addFile(String name, File file) {
        fileParameters.add(new FileParameter(name, file));
    }

    public List<FileParameter> getFileParameters() {
        return fileParameters;
    }

    public List<BasicParameter> getBasicParameters() {
        return parameters;
    }

    public boolean isBasicParameterEmpty() {
        return this.parameters.isEmpty();
    }

    public boolean isFileParameterEmpty() {
        return this.fileParameters.isEmpty();
    }

    public boolean isAllParameterEmpty() {
        return parameters.isEmpty() && fileParameters.isEmpty();
    }
}