package Mult;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class DoReducer implements Callable<List<Integer>> {
    private int length;

    public DoReducer(int length) {
        this.length = length;
    }

    @Override
    public List<Integer> call() throws Exception {
        List<Integer> list = new LinkedList<Integer>();
        Random r = new Random();

        for(int i = 0; i < length; i++) {
            Integer number = r.nextInt(10);
            list.add(number);
        }

        return list;
    }
}
