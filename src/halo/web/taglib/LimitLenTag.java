package halo.web.taglib;

import halo.util.DataUtil;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

public class LimitLenTag extends BaseTag {

    private static final long serialVersionUID = -8234255953771031311L;

    private String value;

    private int len;

    private boolean withchar;

    private boolean onerow;

    private boolean tohtml;

    public void setTohtml(boolean tohtml) {
        this.tohtml = tohtml;
    }

    public void setOnerow(boolean onerow) {
        this.onerow = onerow;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public void setWithchar(boolean withchar) {
        this.withchar = withchar;
    }

    @Override
    protected void adapter(JspWriter writer) throws IOException {
        String tmp = null;
        if (onerow) {
            tmp = DataUtil.toTextRow(this.value);
        }
        else {
            tmp = DataUtil.toText(this.value);
        }
        if (withchar) {
            tmp = DataUtil.subSringEx(tmp, this.len);
        }
        else {
            tmp = DataUtil.subString(tmp, this.len);
        }
        if (tohtml) {
            writer.append(DataUtil.toHtml(tmp));
        }
        else {
            writer.append(tmp);
        }
    }
}