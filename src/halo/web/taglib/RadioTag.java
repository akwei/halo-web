package halo.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

public class RadioTag extends BaseTag {

    private static final long serialVersionUID = 2292675869286958062L;

    protected Object value;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    protected RadioAreaTag getRadioAreaTag(Tag tag) {
        if (tag instanceof RadioAreaTag) {
            return (RadioAreaTag) tag;
        }
        return getRadioAreaTag(tag.getParent());
    }

    @Override
    protected void adapter(JspWriter writer) throws IOException {
        RadioAreaTag parent = this.getRadioAreaTag(this.getParent());
        writer.append("<input type=\"radio\"");
        if (this.oid != null) {
            writer.append(" id=\"").append(this.oid.toString()).append("\"");
        }
        if (parent.getName() != null) {
            writer.append(" name=\"").append(parent.getName().toString())
                    .append("\"");
        }
        if (parent.getClazz() != null) {
            writer.append(" clazz=\"").append(parent.getClazz().toString())
                    .append("\"");
        }
        if (value != null) {
            writer.append(" value=\"").append(value.toString()).append("\"");
        }
        if (this.value != null
                && parent.getCheckedvalue() != null
                && this.value.toString().equals(
                        parent.getCheckedvalue().toString())) {
            writer.append(" checked=\"checked\"");
        }
        writer.append("/>");
    }
}