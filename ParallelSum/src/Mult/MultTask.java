package Mult;

public class MultTask {
    public static MapMatrix doSomething(Integer number1, Integer number2, int row, int col, int pos) {
        Integer result = number1 * number2;

        return new MapMatrix(result, row, col, pos);
    }
}
