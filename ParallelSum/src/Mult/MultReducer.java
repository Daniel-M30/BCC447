package Mult;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import Pool.ThreadPool;

public class MultReducer {
    // private List<List<Integer>> auxList;
    private ThreadPool threadPool;

    public MultReducer() {
        // auxList = new LinkedList<List<Integer>>();
        threadPool = ThreadPool.getInstance();
    }

    public List<List<Integer>> parallelMatrix(int n, int m) {
        List<List<Integer>> auxList = new LinkedList<List<Integer>>();

        List<Future<List<Integer>>> results = new LinkedList<Future<List<Integer>>>();
        Future<List<Integer>> result = null;

        for(int i = 0; i < n; i++) {
            result = threadPool.submitTask(new DoReducer(m));      
            results.add(result);
        }
        
        try {
            for(Future<List<Integer>> r: results)
                auxList.add(r.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        results.clear();

        return auxList;
    }
}
