package halo.web.action;

import halo.web.action.upload.cos.MultipartRequest;

import java.util.Enumeration;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

class HkMultiRequest extends HttpServletRequestWrapper {

    public static boolean isMultipart(HttpServletRequest req) {
        String type = null;
        String type1 = req.getHeader("Content-Type");
        String type2 = req.getContentType();
        if (type1 == null && type2 != null) {
            type = type2;
        }
        else if (type2 == null && type1 != null) {
            type = type1;
        }
        else if (type1 != null && type2 != null) {
            type = (type1.length() > type2.length() ? type1 : type2);
        }
        if (type == null
                || !type.toLowerCase().startsWith("multipart/form-data")) {
            return false;
        }
        return true;
    }

    private MultipartRequest request;

    public HkMultiRequest(HttpServletRequest request,
            MultipartRequest multipartRequest) {
        super(request);
        this.request = multipartRequest;
    }

    @Override
    public String getParameter(String name) {
        return this.request.getParameter(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Set<String> set = this.request.getParameterNames();
        Vector<String> v = new Vector<String>();
        v.addAll(set);
        return v.elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.request.getParameterValues(name);
    }
}