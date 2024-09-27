import java.util.ArrayList;

public class ByteCodeInterpreter {
    private ArrayList<Integer> bytecode;
    private ArrayList<Integer> memory;
    public static final int LOAD = 0;
    public static final int LOADI = 1;
    public static final int STORE = 2;
    private int accumulator = 0;
    private int memorySize;

    public ByteCodeInterpreter(int n) {
        this.bytecode = new ArrayList<Integer>();
        this.memory = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            memory.add(0);
        }
        this.memorySize = n;
    }

//    public
}
