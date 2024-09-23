import java.util.HashMap;

public class idTable {
    private HashMap<String, Integer> table;
//    private String ID; // unsure if I need this data member...
    private int address;

    public idTable() {
        this.table = new HashMap<>();
        this.address = 0;
    }

    public void add(String str) {
        table.put(str, address);
        address++;
    }


    public int getAddress(String str) {
        if (this.table.containsKey(str)) {
            return this.table.get(str);
        } else {
            return -1;
        }
    }
}
