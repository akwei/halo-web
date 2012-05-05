package halo.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class HaloTaskServiceFactory {

    private ExecutorService executorService;

    private int maxServiceSize;

    public void setMaxServiceSize(int maxServiceSize) {
        this.maxServiceSize = maxServiceSize;
    }

    public synchronized ExecutorService getExecutorService() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(maxServiceSize,
                    new ThreadFactory() {

                        @Override
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread(r);
                            t.setName("Service Thread " + t.getId());
                            t.setDaemon(false);
                            return t;
                        }
                    });
        }
        return executorService;
    }
}
