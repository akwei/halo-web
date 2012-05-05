package halo.web.taglib;

import halo.util.DataUtil;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

public class HtmlValueTag extends BaseTag {

    private static final long serialVersionUID = 2444660484543529830L;

    private String value;

    private boolean onerow;

    private boolean textarea;

    private boolean encode;

    public void setEncode(boolean encode) {
        this.encode = encode;
    }

    public void setTextarea(boolean textarea) {
        this.textarea = textarea;
    }

    public void setOnerow(boolean onerow) {
        this.onerow = onerow;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected void adapter(JspWriter writer) throws IOException {
        if (DataUtil.isEmpty(this.value)) {
            return;
        }
        if (onerow) {
            String v = DataUtil.toHtmlSimpleOneRow(this.value);
            if (this.encode) {
                v = DataUtil.urlEncoder(v);
            }
            writer.append(v);
            return;
        }
        if (this.textarea) {
            writer.append(DataUtil.toHtmlSimple(this.value).replaceAll("<br/>",
                    "\n"));
            return;
        }
        writer.append(DataUtil.toHtmlSimple(this.value));
    }
}