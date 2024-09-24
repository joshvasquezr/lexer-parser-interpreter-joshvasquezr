public class Token {
    public String type;
    public String value;
    public int line;

    public Token(String type, String value, int line) {
        this.type=type;
        this.value=value;
        this.line = line;
    }

    public String getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public int getLine() {
        return this.line;
    }

    public String toString(){

        return type+" "+value+" "+line;
    }

}
