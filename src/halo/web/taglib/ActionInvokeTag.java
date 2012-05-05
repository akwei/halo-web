package halo.web.taglib;

import halo.web.action.ActionExe;

import javax.servlet.jsp.JspWriter;

public class ActionInvokeTag extends BaseTag {

    private static final long serialVersionUID = -1283364414996755943L;

    private String mappinguri;

    private boolean flush;

    public void setFlush(boolean flush) {
        this.flush = flush;
    }

    public void setMappinguri(String mappinguri) {
        this.mappinguri = mappinguri;
    }

    public String getMappinguri() {
        return mappinguri;
    }

    @Override
    protected void adapter(JspWriter writer) throws Exception {
        if (flush) {
            writer.flush();
        }
        ActionExe.invoke(mappinguri, this.getRequest(), this.getResponse());
    }
}