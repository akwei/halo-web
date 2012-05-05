package halo.util.thread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 多线程执行任务，统一返回执行结果
 * 
 * @author akwei
 */
public class HaloTaskService {

    private final List<Callable<Boolean>> taskList = new ArrayList<Callable<Boolean>>();

    public void addTask(Callable<Boolean> task) {
        taskList.add(task);
    }

    public void addTasks(Collection<Callable<Boolean>> c) {
        taskList.addAll(c);
    }

    public List<Future<Boolean>> invokeAll(ExecutorService executorService)
            throws Exception {
        return executorService.invokeAll(taskList);
    }

    public List<Future<Boolean>> invokeAll(ExecutorService executorService,
            long timeout, TimeUnit unit) throws InterruptedException {
        return executorService.invokeAll(taskList, timeout, unit);
    }
}
