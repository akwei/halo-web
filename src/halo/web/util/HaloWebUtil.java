package halo.web.util;

import halo.web.action.HkRequest;
import halo.web.action.HkRequestImpl;
import halo.web.action.HkResponse;
import halo.web.action.HkResponseImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HaloWebUtil {

    public static HkRequest getHkRequest(HttpServletRequest request) {
        if (request instanceof HkRequest) {
            return (HkRequest) request;
        }
        return new HkRequestImpl(request);
    }

    public static HkResponse getHkResponse(HttpServletResponse response) {
        if (response instanceof HkResponse) {
            return (HkResponse) response;
        }
        return new HkResponseImpl(response);
    }
}