package Sum;

import java.util.List;
import java.util.concurrent.Callable;

public class SumReducer implements Callable<List<Integer>> {
    List<Integer> list1;
    List<Integer> list2;

    public SumReducer(List<Integer> list1, List<Integer> list2) {
        this.list1 = list1;
        this.list2 = list2;
    }

    @Override()
    public List<Integer> call() {
        return SumTask.sumNumbers(list1, list2);
    }
}
