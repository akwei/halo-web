package halo.web.action;

public class MappingUriCreater {

    private MappingUriCreater() {
    }

    private static final String endpfix = "/";

    private static final String seperator = "/";

    private static String dot = ".";

    /**
     * 解析uri,"_"作为action与方法名的分隔符。例如：/user_list。可以对应UserAction中list的方法
     * 
     * @param uri
     *            request.getRequestURI
     * @param contextPath
     * @return
     */
    public static String findMappingUri(String uri, String contextPath) {
        String localuri = uri.substring(contextPath.length(), uri.length());
        String postfix = getPostfix(localuri);
        if (postfix == null) {
            // 如果uri为"/"结尾，则需要去掉""。例如/user/list/，需要获得有用的部分为/user/list
            // 这种情况还没遇到，不知是否要去掉
            if (localuri.endsWith(endpfix)) {
                return localuri.substring(0, localuri.lastIndexOf(endpfix));
            }
            return localuri;
        }
        // 如果uri有后缀则去掉后缀。例如/user/list.do，需要获得有用的部分为/user/list
        return localuri.substring(0, localuri.lastIndexOf(postfix));
    }

    // /**
    // * 解析uri,"_"作为action与方法名的分隔符。例如：/user_list。可以对应UserAction中list的方法
    // *
    // * @param request
    // * @return
    // */
    // public static String findMappingUri(HttpServletRequest request) {
    // String uri = request.getRequestURI();
    // String localuri = uri.substring(request.getContextPath().length(),
    // uri.length());
    // String postfix = getPostfix(localuri);
    // if (postfix == null) {
    // // 如果uri为"/"结尾，则需要去掉""。例如/user/list/，需要获得有用的部分为/user/list
    // // 这种情况还没遇到，不知是否要去掉
    // if (localuri.endsWith(endpfix)) {
    // return localuri.substring(0, localuri.lastIndexOf(endpfix));
    // }
    // return localuri;
    // }
    // // 如果uri有后缀则去掉后缀。例如/user/list.do，需要获得有用的部分为/user/list
    // return localuri.substring(0, localuri.lastIndexOf(postfix));
    // }
    public static String findPostfix(String uri, String contextPath) {
        String localuri = uri.substring(contextPath.length(), uri.length());
        return getPostfix(localuri);
    }

    /**
     * 获得uri后缀
     * 
     * @param localuri
     * @return
     */
    private static String getPostfix(String localuri) {
        // 获得最后一个"/"的位置
        int lastidx_dot = localuri.lastIndexOf(dot);
        // 例：/user/set_method
        if (lastidx_dot == -1) {
            return null;
        }
        int lastidx_sep = localuri.lastIndexOf(seperator);
        // 例： user.do，postfix=.do
        if (lastidx_sep == -1) {
            return localuri.substring(lastidx_dot);
        }
        // 例：/user.su/set_method
        if (lastidx_sep > lastidx_dot) {
            return null;
        }
        // 例：/user/set_method.do,/user/set.do,/user.do
        return localuri.substring(lastidx_dot);
    }
}
