package halo.web.bigpipe;

import halo.web.action.JspValueReader;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BigPipe {

    private final List<Callable<Boolean>> taskList = new ArrayList<Callable<Boolean>>();

    public List<Callable<Boolean>> getTaskList() {
        return taskList;
    }

    public void addView(final HttpServletRequest request,
            final HttpServletResponse response, final String key,
            final String path) {
        taskList.add(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                try {
                    String value = JspValueReader.getJspValue(request,
                            response, path).trim();
                    writeValue(response.getWriter(), key, value);
                    return true;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });
    }

    public void addValue(final PrintWriter printWriter, final String key,
            final String value) {
        taskList.add(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                if (printWriter.checkError()) {
                    return false;
                }
                writeValue(printWriter, key, value);
                return true;
            }
        });
    }

    protected synchronized void writeValue(PrintWriter printWriter, String key,
            String value) {
        printWriter.write("<script>halo_bp_write(\"" + key + "\",\"" + value
                + "\")</script>");
        printWriter.flush();
    }
}
