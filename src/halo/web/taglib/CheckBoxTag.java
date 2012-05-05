package halo.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

public class CheckBoxTag extends BaseTag {

    private static final long serialVersionUID = 4901343569123173841L;

    private String name;

    private String clazz;

    private Object value;

    private Object checkedvalue;

    private String checkedvalues;

    public void setName(String name) {
        this.name = name;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setCheckedvalue(Object checkedvalue) {
        this.checkedvalue = checkedvalue;
    }

    public void setCheckedvalues(String checkedvalues) {
        this.checkedvalues = checkedvalues;
    }

    @Override
    protected void adapter(JspWriter writer) throws IOException {
        writer.append("<input type=\"checkbox\"");
        if (this.oid != null) {
            writer.append(" id=\"").append(this.oid.toString()).append("\"");
        }
        if (this.name != null) {
            writer.append(" name=\"").append(this.name.toString()).append("\"");
        }
        if (this.clazz != null) {
            writer.append(" clazz=\"").append(this.clazz.toString())
                    .append("\"");
        }
        if (value != null) {
            writer.append(" value=\"").append(value.toString()).append("\"");
        }
        if (this.value != null) {
            if (this.checkedvalue != null) {
                String s_v = this.value.toString();
                String s_ch = this.checkedvalue.toString();
                if (s_v.equals(s_ch)) {
                    writer.append(" checked=\"checked\"");
                }
            }
            else if (this.checkedvalues != null) {
                String[] ts = this.checkedvalues.split(",");
                for (String s : ts) {
                    if (this.value.toString().equals(s)) {
                        writer.append(" checked=\"checked\"");
                        break;
                    }
                }
            }
        }
        writer.append("/>");
    }
}