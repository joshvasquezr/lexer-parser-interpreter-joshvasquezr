import java.util.HashMap;

public class idTable {
    // This is where we store names (keys) and their addresses (numbers)
    private HashMap<String, Integer> table;
    private int address;

    // Constructor: Sets up the table and starts address counting at 0
    public idTable() {
        this.table = new HashMap<>();
        this.address = 0;
    }

    // Adds a new name with its current address, then moves to the next address
    public void add(String str) {
        table.put(str, address);
        address++;
    }

    // Looks up the address for a name; if not found, returns -1
    public int getAddress(String str) {
        if (this.table.containsKey(str)) {
            return this.table.get(str);
        } else {
            return -1;
        }
    }

    // Prints the table of names and addresses
    @Override
    public String toString() {
        return "Symbol Table: " + table;
    }
}