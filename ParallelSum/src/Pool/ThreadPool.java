package Pool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import Mult.DoReducer;
import Mult.MultReducer;
import Mult.MapMatrix;
import Sum.SumReducer;

public class ThreadPool {
    public static ThreadPool threadPool;

    private int nThreads;
    private ExecutorService pool;

    private ThreadPool() {
        nThreads = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(nThreads);
    }

    public static ThreadPool getInstance() {
        if(threadPool != null)
            return threadPool;

        threadPool = new ThreadPool();
        return threadPool;
    }

    public Future<List<Integer>> submitTask(SumReducer sumReducer) {
        return pool.submit(sumReducer);
    }

    public Future<List<Integer>> submitTask(DoReducer doReducer) {
        return pool.submit(doReducer);
    }

    public Future<MapMatrix> submitTask(MultReducer mapReducer) {
        return pool.submit(mapReducer);
    }

    public static void poolShutdown() {
        threadPool.pool.shutdown();
    }
}
