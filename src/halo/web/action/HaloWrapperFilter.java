package halo.web.action;

import halo.util.FileUtil;
import halo.web.util.HaloWebUtil;

import java.io.File;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 包装HttpServletRequest 和 HttpServletResponse，让下面的处理过程可直接调用最新的reqeust
 * response,需要放到filter中，逻辑处理之前最好在第一个filter的位置
 * 
 * @author akwei
 */
public class HaloWrapperFilter extends HaloFilter {

    @Override
    public void doFilter(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HkRequest hkRequest = null;
        try {
            hkRequest = HaloWebUtil.getHkRequest(request);
            chain.doFilter(hkRequest, HaloWebUtil.getHkResponse(response));
        }
        finally {
            this.clean(hkRequest);
        }
    }

    private void clean(HkRequest hkRequest) {
        if (hkRequest == null) {
            return;
        }
        File[] files = hkRequest.getFiles();
        if (files != null) {
            for (File f : files) {
                FileUtil.deleteFile(f);
            }
        }
    }
}