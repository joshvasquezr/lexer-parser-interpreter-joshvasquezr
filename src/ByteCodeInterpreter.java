import java.util.ArrayList;

public class ByteCodeInterpreter {
    private ArrayList<Integer> bytecode;
    private ArrayList<Integer> memory;
    public static final int LOAD = 0;
    public static final int LOADI = 1;
    public static final int STORE = 2;
    private int accumulator = 0;
    private int memorySize;
    private boolean runTimeError;
    private idTable symbolTable;
    private boolean parserError;

    public ByteCodeInterpreter(int n, String fileName) {
        Parser parser = new Parser(fileName);
        parser.parseProgram();
        this.parserError = parser.getError();
        if (parserError) {
            return;
        }
        this.bytecode = parser.getByteCode();
        this.symbolTable = parser.getSymbolTable();
        this.memory = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            memory.add(0);
        }
        this.memorySize = n;
        this.runTimeError = false;
    }

    public void run() {
        if (parserError) {
            return;
        }
        for (int i = 0; i < bytecode.size() - 1; i = i+2) {
            int command = bytecode.get(i);
            if (command == LOAD) {
                int address = bytecode.get(i+1);
                load(address);
            } else if (command == LOADI) {
                int value = bytecode.get(i+1);
                loadi(value);
            } else if (command == STORE) {
                int storeAddress = bytecode.get(i+1);
                store(storeAddress);
                if (runTimeError) {
                    System.out.println("Run Time Error: Address '" + storeAddress + "' out of range.");
                    return;
                }
            }
        }
    }

    public void load(int address) {
        // get value from idTable with address specified
        int value = memory.get(address);

        // add this value to accumulator
        accumulator = accumulator + value;
    }

    public void loadi(int n) {
        accumulator = accumulator + n;
    }

    public void store(int address) {
        if (address >= memorySize) {
            runTimeError = true;
            return;
        };
        memory.set(address, accumulator);
        accumulator = 0;
    }

    public String toString() {
        String result = "";
        if (!parserError) {
            result = result+ symbolTable +
                    "\nByteCode: " + bytecode +
                    "\nMemory: " + memory;
        }
        return result;
    }
    public static void main(String[] args) {
        String fileName = "";  // Get the file name with the program code
        if (args.length == 0) {
            System.out.println("For this run, test.txt is used");
            fileName = "test.txt";
        } else {
            fileName = args[0];  // Take the first argument as the file name
        }
        ByteCodeInterpreter test = new ByteCodeInterpreter(10, fileName);
        test.run();
        System.out.println(test);
    }
}