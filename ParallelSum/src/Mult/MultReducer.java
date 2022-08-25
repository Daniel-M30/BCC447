package Mult;
import java.util.concurrent.Callable;

public class MultReducer implements Callable<MapMatrix> {
    private int row;
    private int col;
    private int pos;
    private Integer number1;
    private Integer number2;

    public MultReducer(Integer number1, Integer number2, int row, int col, int pos) {
        this.number1 = number1;
        this.number2 = number2;
        this.row = row;
        this.col = col;
        this.pos = pos;
    }

    @Override
    public MapMatrix call() throws Exception {
        return MultTask.doSomething(number1, number2, row, col, pos);
    }
}
