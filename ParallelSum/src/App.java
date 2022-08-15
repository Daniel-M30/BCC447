import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Mult.MultReducer;
import Pool.ThreadPool;
import Sum.Reducer;

public class App {
    public static void main(String[] args) throws Exception {
        // App.sumProblem();
        App.multProblem();

        ThreadPool.poolShutdown();
    }

    private static void sumProblem() {
        List<Integer> input = new LinkedList<Integer>();
		Random r = new Random();

		for(int i = 0; i < 10000000; i++) {
            Integer number = r.nextInt(10);
            input.add(number);
        }

        Reducer reducer = new Reducer();

        reducer.splitData(input);
        reducer.parallelReduce();
    }

    private static void multProblem() {
        MultReducer multReducer = new MultReducer();

        List<List<Integer>> input1 = multReducer.parallelMatrix(1000, 1000);
        List<List<Integer>> input2 = multReducer.parallelMatrix(1000, 1000);

        System.out.println("Finish!!");
    }
}
