public class Token {
    public String type;
    public String value;
    public String line;

    public Token(String type, String value) {
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

    public String toString(){

        return type+" "+value;
    }

}
