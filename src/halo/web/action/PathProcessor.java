package halo.web.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PathProcessor {

    protected static final String HTTP_BEGIN = "http://";

    protected static final String HTTPS_BEGIN = "https://";

    protected static final String TEST_BEGIN = "t:";

    protected static final String PATH_SEPARATOR = "/";

    protected static final String CMD_REDIRECT_INAPP = "r:";

    protected static final String CMD_REDIRECT_OUTOFAPP = "rr:";

    /**
     * 获得配置的异常对应路径，并执行垓路径
     * 
     * @param exception
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void doExceptionForward(Exception exception,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 根据名称找到对应的forward
        String result = getExceptionResult(exception.getClass(), request,
                response);
        // 如果没有找到，就处理对应的servlet相应异常
        if (result == null) {
            throw new RuntimeException(exception);
        }
        processResult(result, request, response);
    }

    public static <T> String getExceptionResult(Class<T> exceptionClass,
            HttpServletRequest request, HttpServletResponse response) {
        String type = exceptionClass.getName();
        // 根据名称找到对应的forward
        String result = ExceptionConfig.getExceptionForward(type);
        if (result == null) {
            return getExceptionResult(exceptionClass.getSuperclass(), request,
                    response);
        }
        return result;
    }

    /**
     * 处理返回页面。<br/>
     * 例如r:/login则会redirect到{contextPath}/login。<br/>
     * rr:/login则会redirect到/login。<br/>
     * /login则会forward到{contextPath}/login<br/>
     * 
     * @param result
     *            页面路径。如果以r:开头，则为redirect，如果以rr:开头，则不需要在返回的路径中加入contextPath
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void processResult(String result, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (result == null) {
            return;
        }
        if (result.startsWith(TEST_BEGIN)) {
            return;
        }
        String path = null;
        boolean redirect = false;
        boolean containCtxPath = true;
        if (result.startsWith(CMD_REDIRECT_INAPP)) {
            path = result.substring(2);
            redirect = true;
        }
        else if (result.startsWith(CMD_REDIRECT_OUTOFAPP)) {
            path = result.substring(3);
            redirect = true;
            containCtxPath = false;
        }
        else {
            path = result;
        }
        if (path.startsWith(HTTP_BEGIN) || path.startsWith(HTTPS_BEGIN)) {
            doStartWithHTTPForward(path, response);
            return;
        }
        doForward(redirect, path, containCtxPath, request, response);
    }

    private static void doStartWithHTTPForward(String path,
            HttpServletResponse response) throws IOException {
        response.sendRedirect(path);
    }

    /**
     * 跳转页面。
     * 
     * @param isRedirect
     *            是否重定向
     * @param path
     *            跳转路径
     * @param containCtxPath
     *            是否添加contextPath
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private static void doForward(boolean isRedirect, String path,
            boolean containCtxPath, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (path.startsWith(PATH_SEPARATOR)) {
            if (isRedirect) {
                if (containCtxPath) {
                    response.sendRedirect(request.getContextPath() + path);
                }
                else {
                    response.sendRedirect(path);
                }
                return;
            }
            request.getRequestDispatcher(path).forward(request, response);
            return;
        }
        if (isRedirect) {
            response.sendRedirect(path);
            return;
        }
        request.getRequestDispatcher(path).forward(request, response);
    }
}