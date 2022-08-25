import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Mult.Mult;
import Pool.ThreadPool;
import Sum.Sum;

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

        Sum sum = new Sum();

        sum.splitData(input);
        Integer result = sum.parallelReduce();

        System.out.println(result);
        System.out.println("Finish!!");
    }

    private static void multProblem() {
        Mult mult = new Mult(200, 200);

        List<List<Integer>> input1 = mult.parallelMatrix();
        List<List<Integer>> input2 = mult.parallelMatrix();

        mult.setLists(input1, input2);
        mult.parallelReduce();

        System.out.println("Finish!!");
    }
}
