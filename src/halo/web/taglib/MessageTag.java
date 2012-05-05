package halo.web.taglib;

import halo.web.util.MessageUtil;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

public class MessageTag extends BaseTag {

    private static final long serialVersionUID = -6623958898546113511L;

    @Override
    protected void adapter(JspWriter writer) throws IOException {
        String msg = (String) this.getRequest().getAttribute(
                MessageUtil.MESSAGE_NAME);
        if (msg == null) {
            msg = (String) this.getRequest().getSession()
                    .getAttribute(MessageUtil.MESSAGE_NAME);
            this.getRequest().getSession()
                    .removeAttribute(MessageUtil.MESSAGE_NAME);
        }
        if (msg != null) {
            writer.append(msg);
        }
    }
}