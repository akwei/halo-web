package halo.web.action;

import halo.web.util.WebCnf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web运行的入口
 * 
 * @author akwei
 */
public class ActionFilter implements Filter {

    private static final String REQUESTCONTEXTPATH_KEY = "appctx_path";

    WebCnf webCnf;

    @Override
    public void init(FilterConfig config) throws ServletException {
        webCnf = WebCnf.getInstance();
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
            FilterChain arg2) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) arg0;
        HttpServletResponse resp = (HttpServletResponse) arg1;
        req.setAttribute(REQUESTCONTEXTPATH_KEY, req.getContextPath());
        String mappingUri = MappingUriCreater.findMappingUri(
                req.getRequestURI(), req.getContextPath());
        try {
            PathProcessor.processResult(
                    ActionExe.invoke(mappingUri, req, resp), req, resp);
        }
        catch (Exception e) {
            e.printStackTrace();
            PathProcessor.doExceptionForward(e, req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}