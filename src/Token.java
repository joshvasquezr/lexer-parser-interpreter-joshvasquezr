public class Token {
    public String type;
    public String value;
    public int line;

    public Token(String type, String value, int line) {
        this.type=type;
        this.value=value;
        this.line = line;
    }
    // getter method for a token's type
    public String getType() {
        return this.type;
    }

    // getter method for a token's value
    public String getValue() {
        return this.value;
    }

    // getter method for a token's line
    public int getLine() {
        return this.line;
    }

    // prints token 
    public String toString(){
        return type+" "+value+" "+line;
    }

}
