package halo.web.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public interface HkResponse extends HttpServletResponse {

    void sendXML(String value) throws IOException;

    void sendHtml(Object value) throws IOException;

    void sendJson(String json) throws IOException;
}