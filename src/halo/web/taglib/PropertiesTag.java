package halo.web.taglib;

import halo.web.i18n.HaloResource;
import halo.web.util.WebCnf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.JspWriter;

public class PropertiesTag extends BaseTag {

    private static final long serialVersionUID = -2452461789404041648L;

    private final int DEF_SIZE = 4;

    private Object key;

    private String arg0;

    private String arg1;

    private String arg2;

    private String arg3;

    @Override
    protected void adapter(JspWriter writer) throws IOException {
        String result = null;
        Locale locale = this.getHkRequest().getCurrentLocale();
        if (arg0 == null && arg1 == null && arg2 == null && arg3 == null) {
            result = HaloResource.getText(locale, WebCnf.getInstance()
                    .getI18nResourceName(), key.toString());
        }
        else {
            result = HaloResource.getText(locale, WebCnf.getInstance()
                    .getI18nResourceName(), key.toString(), this.buildArg());
        }
        writer.append(result);
    }

    private Object[] buildArg() {
        List<String> argList = new ArrayList<String>(DEF_SIZE);
        if (isNotEmpty(arg0)) {
            argList.add(arg0);
        }
        if (isNotEmpty(arg1)) {
            argList.add(arg1);
        }
        if (isNotEmpty(arg2)) {
            argList.add(arg2);
        }
        if (isNotEmpty(arg3)) {
            argList.add(arg3);
        }
        String[] v = argList.toArray(new String[DEF_SIZE]);
        return v;
    }

    private boolean isNotEmpty(String value) {
        if (value == null || value.trim().length() == 0) {
            return false;
        }
        return true;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public void setArg0(String arg0) {
        this.arg0 = arg0;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }
}