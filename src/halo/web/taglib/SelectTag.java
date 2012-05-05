package halo.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

public class SelectTag extends BaseTag {

    private static final long serialVersionUID = 8272378800903804578L;

    private Object name;

    private Object clazz;

    private Object checkedvalue;

    private Object onchange;

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getClazz() {
        return clazz;
    }

    public void setClazz(Object clazz) {
        this.clazz = clazz;
    }

    public Object getCheckedvalue() {
        return checkedvalue;
    }

    public void setCheckedvalue(Object checkedvalue) {
        this.checkedvalue = checkedvalue;
    }

    public Object getOnchange() {
        return onchange;
    }

    public void setOnchange(Object onchange) {
        this.onchange = onchange;
    }

    @Override
    protected void adapter(JspWriter writer) throws IOException {
        writer.append("<select");
        if (this.oid != null) {
            writer.append(" id=\"").append(this.oid.toString()).append("\"");
        }
        if (this.name != null) {
            writer.append(" name=\"").append(this.name.toString()).append("\"");
        }
        if (this.clazz != null) {
            writer.append(" class=\"").append(this.clazz.toString())
                    .append("\"");
        }
        if (this.onchange != null) {
            writer.append(" onchange=\"").append(this.onchange.toString())
                    .append("\"");
        }
        writer.append(">");
        this.renderBodyContent(writer);
        writer.append("</select>");
    }
}