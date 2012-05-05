package halo.web.util;

import javax.servlet.http.HttpServletRequest;

public class MessageUtil {

    public static final String MESSAGE_NAME = "com_hk_message_name_request_session";

    private MessageUtil() {//
    }

    public static String getMessage(HttpServletRequest request) {
        String msg = (String) request.getAttribute(MESSAGE_NAME);
        if (msg == null) {
            msg = (String) request.getSession().getAttribute(MESSAGE_NAME);
        }
        request.getSession().removeAttribute(MESSAGE_NAME);
        return msg;
    }
}