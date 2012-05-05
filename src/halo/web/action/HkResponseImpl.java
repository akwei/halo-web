package halo.web.action;

import java.io.IOException;

import halo.web.util.ServletUtil;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class HkResponseImpl extends HttpServletResponseWrapper implements
        HkResponse {

    public HkResponseImpl(HttpServletResponse response) {
        super(response);
    }

    private HttpServletResponse getHttpServletResponse() {
        return (HttpServletResponse) this.getResponse();
    }

    @Override
    public void sendHtml(Object value) throws IOException {
        ServletUtil.sendHtml(this.getHttpServletResponse(), value);
    }

    @Override
    public void sendXML(String value) throws IOException {
        ServletUtil.sendXml(this.getHttpServletResponse(), value);
    }

    @Override
    public void sendJson(String json) throws IOException {
        ServletUtil.sendJson(this.getHttpServletResponse(), json);
    }
}