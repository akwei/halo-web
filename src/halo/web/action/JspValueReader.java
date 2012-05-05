package halo.web.action;

import java.io.IOException;

import halo.web.action.HttpStringResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JspValueReader {

    public static String getJspValue(HttpServletRequest request,
            HttpServletResponse response, String path) throws ServletException,
            IOException {
        HttpStringResponse httpStringResponse = new HttpStringResponse(response);
        request.getRequestDispatcher(path).include(request, httpStringResponse);
        return httpStringResponse.getString();
    }
}