package util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author Legion
 * @Date 2020/12/16 20:39
 * @Description 线程池
 */
public class ThreadPool {
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 20, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(3));

    /**
     * 获取线程池
     * @return 线程池
     */
    public static ThreadPoolExecutor getPoolExecutor() {
        return poolExecutor;
    }
}
