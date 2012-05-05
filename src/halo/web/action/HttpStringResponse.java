package halo.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class HttpStringResponse extends HttpServletResponseWrapper {

    private StringWriter stringWriter;

    public HttpStringResponse(HttpServletResponse response) {
        super(response);
        stringWriter = new StringWriter(response.getBufferSize());
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(stringWriter);
    }

    public String getString() {
        return stringWriter.getBuffer().toString();
    }
}
