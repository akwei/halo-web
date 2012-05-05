package halo.web.taglib;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public abstract class BaseTag extends BodyTagSupport {

    private static final long serialVersionUID = 6156831085682303775L;

    protected Object oid;

    public void setOid(Object oid) {
        this.oid = oid;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            adapter(pageContext.getOut());
        }
        catch (Exception e) {
            throw new JspException(e);
        }
        return super.doEndTag();
    }

    /**
     * @param writer
     * @throws IOException
     */
    protected abstract void adapter(JspWriter writer) throws Exception;

    /**
     * 绘制标签Body块的内容
     * 
     * @param writer
     * @throws IOException
     */
    protected void renderBodyContent(JspWriter writer) throws IOException {
        writer.append(getBodyContentAsString());
    }

    /**
     * 以字符串的形式返回标签块的内容
     * 
     * @return
     */
    protected String getBodyContentAsString() {
        if (this.bodyContent == null)
            return "";
        if (this.bodyContent.getString() == null)
            return "";
        return this.bodyContent.getString();
    }

    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) pageContext.getRequest();
    }

    protected HttpServletResponse getResponse() {
        return (HttpServletResponse) pageContext.getResponse();
    }

    protected HttpSession getSession() {
        return pageContext.getSession();
    }

    protected ServletContext getApplication() {
        return pageContext.getServletContext();
    }
}