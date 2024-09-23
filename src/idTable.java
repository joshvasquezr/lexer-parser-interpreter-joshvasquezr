import java.util.HashMap;

public class idTable {
    private HashMap<String, Integer> table;
    private String ID;
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
        System.out.println(this.table.get(str));
        return this.table.get(str);
    }

    public static void main(String[] args) {
        idTable test = new idTable();
        test.add("abc");
        test.add("cab");
        test.add("bca");
        System.out.println(test.table);
        test.getAddress("cab");
    }
}
