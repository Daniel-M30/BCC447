package Sum;

import java.util.LinkedList;
import java.util.List;

public class SumTask {
    public static List<Integer> sumNumbers(List<Integer> list1, List<Integer> list2) {
        Integer sum1 = sum(list1);
        Integer sum2 = sum(list2);

        List<Integer> result = new LinkedList<Integer>();
        result.add(sum1 + sum2);

        return result;
    }

    private static Integer sum(List<Integer> list) {
        Integer sum = 0;
        for(Integer i : list)
            sum += i;

        return sum;
    }
}
