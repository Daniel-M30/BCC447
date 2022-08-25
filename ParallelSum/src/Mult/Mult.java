package Mult;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import Pool.ThreadPool;
import Sum.Sum;

public class Mult {
    private ThreadPool threadPool;

    private List<List<Integer>> list1;
    private List<List<Integer>> list2;

    private Map<String, MapMatrix> mapMatrix;
    private List<List<List<Integer>>> listMatrix;

    private int row;
    private int column;

    public Mult(int row, int column) {
        threadPool = ThreadPool.getInstance();
        mapMatrix = new HashMap<String, MapMatrix>();

        listMatrix = new LinkedList<List<List<Integer>>>();

        List<List<Integer>> auxRow;
        List<Integer> auxColumn;
        for(int i = 0; i < row; i++) {
            auxRow = new LinkedList<List<Integer>>();

            for(int j = 0; j < column; j++) {
                auxColumn = new LinkedList<Integer>();
                auxRow.add(auxColumn);
            }

            listMatrix.add(auxRow);
        }

        this.row = row;
        this.column = column;
    }

    public List<List<Integer>> parallelMatrix() {
        List<List<Integer>> auxList = new LinkedList<List<Integer>>();

        List<Future<List<Integer>>> results = new LinkedList<Future<List<Integer>>>();
        Future<List<Integer>> result = null;

        for(int i = 0; i < row; i++) {
            result = threadPool.submitTask(new DoReducer(column));      
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

    public void setLists(List<List<Integer>> list1, List<List<Integer>> list2) {
        this.list1 = list1;
        this.list2 = list2;
    }

    public void parallelReduce() {
        List<Future<MapMatrix>> results = new LinkedList<Future<MapMatrix>>();
        Future<MapMatrix> result = null;
        List<Integer> auxList1;
        List<Integer> auxList2;

        for(int count = 0; count < row; count++) {
            auxList1 = list1.get(count);
            for(int i = 0; i < row; i++) {
                for(int j = 0; j < column; j++) {
                    auxList2 = list2.get(j);
                    result = threadPool.submitTask(new MultReducer(auxList1.get(j), auxList2.get(i), count, i, j));      
                    results.add(result);
                }
            }
        }
        
        try {
            for(Future<MapMatrix> r: results) {
                MapMatrix resFuture = r.get();
                mapMatrix.put(resFuture.row + "_" + resFuture.col + "_" + resFuture.pos, resFuture);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        results.clear();
        
        mapMatrix.forEach((key, value) -> {
            List<List<Integer>> rowList = listMatrix.get(value.row);
            List<Integer> colList = rowList.get(value.col);
            colList.add(value.number);
            
            rowList.set(value.col, colList);
            listMatrix.set(value.row, rowList);
        });
        
        list1.clear();
        list2.clear();

        List<List<Integer>> auxRow;
        List<Integer> auxColumn;
        List<Integer> listAux;

        List<List<Integer>> list = new LinkedList<List<Integer>>();
        for(int i = 0; i < row; i++) {
            listAux = new LinkedList<Integer>();
            auxRow = listMatrix.get(i);
            
            for(int j = 0; j < column; j++) {
                auxColumn = auxRow.get(j);

                Sum sum = new Sum();
                sum.splitData(auxColumn);
                Integer resultInteger = sum.parallelReduce();

                listAux.add(resultInteger);
            }
            
            System.out.println(listAux);
            list.add(listAux);
        }

        // for(List<Integer> n : list) {
        //     System.out.println(n);
        // }
    }
}
