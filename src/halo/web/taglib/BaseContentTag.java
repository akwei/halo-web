package halo.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

public abstract class BaseContentTag extends BaseTag {

    private static final long serialVersionUID = -4669121910331334124L;

    @Override
    protected void adapter(JspWriter writer) throws IOException {
        renderBodyContent(writer);
    }
}