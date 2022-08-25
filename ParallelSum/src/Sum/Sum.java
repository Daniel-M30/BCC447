package Sum;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import Pool.ThreadPool;

public class Sum {
    private List<List<Integer>> listNumbers, auxList; 
	private int nThreads;
    private int partitions;
    
    private ThreadPool threadPool;

    public Sum() {
        nThreads = Runtime.getRuntime().availableProcessors();
        partitions = nThreads * 2;

        listNumbers = new LinkedList<List<Integer>>();
        auxList = new LinkedList<List<Integer>>();

        threadPool = ThreadPool.getInstance();
    }

    public void splitData(List<Integer> data) {
        int sizeData = data.size();
        if(sizeData < partitions) {
            partitions = sizeData;
        }

        int splitSize = sizeData / partitions;
        int count = 0;
        
        for(int i = 0; i < partitions; i++) {
            listNumbers.add(data.subList(splitSize * i, splitSize * (i + 1)));
            count += splitSize;
        }
        
        if(count < sizeData) {
            listNumbers.add(data.subList(count, sizeData));
        }
    }

    public Integer parallelReduce() {
        while(listNumbers.size() > 1 || auxList.size() > 1) {
            List<Future<List<Integer>>> results = new LinkedList<Future<List<Integer>>>();
            Future<List<Integer>> result = null;

            if(!listNumbers.isEmpty()) {
                do {
                    result = listSplit();

                    if(result != null) {
                        results.add(result);
                    }
                } while(!listNumbers.isEmpty());

                try {
                    for(Future<List<Integer>> r: results)
                        auxList.add(r.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            else {
                do {
                    result = auxSplit();

                    if(result != null) {
                        results.add(result);
                    }
                } while(!auxList.isEmpty());

                try {
                    for(Future<List<Integer>> r: results)
                        listNumbers.add(r.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            results.clear();
        }
		
        Integer resultInteger;
		if(!listNumbers.isEmpty()) {
            List<Integer> finalList = listNumbers.get(0);
            resultInteger = finalList.get(0);
        }

		else {
            List<Integer> finalList = auxList.get(0);
            resultInteger = finalList.get(0);
        }
		
		listNumbers.clear();
		auxList.clear();

        return resultInteger;
    }

    private Future<List<Integer>> listSplit() {
        try {
            List<Integer> args1 = null;
            List<Integer> args2 = null;
            if(!listNumbers.isEmpty()) {
                args1 = listNumbers.remove(0);
            }

            if(!listNumbers.isEmpty()) {
                args2 = listNumbers.remove(0);
            }
    
            return threadPool.submitTask(new SumReducer(args1, args2));
        } catch(Exception e) {
            return null;
        }
    }

    private Future<List<Integer>> auxSplit() {
        try {
            List<Integer> args1 = null;
            List<Integer> args2 = null;
            if(!auxList.isEmpty()) {
                args1 = auxList.remove(0);
            }

            if(!auxList.isEmpty()) {
                args2 = auxList.remove(0);
            }
    
            return threadPool.submitTask(new SumReducer(args1, args2));
        } catch(Exception e) {
            return null;
        }
    }
 }
