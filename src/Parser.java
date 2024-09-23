import org.w3c.dom.ls.LSOutput;
import java.util.ArrayList;

public class Parser {
    //TODO implement Parser class here
    private ArrayList<Token> tokenList;
    private int index = 0;
    private Token currentToken;
    private String type;
    private String value;
    private idTable symbolTable;

    // Constructor initializes Parser() data members
    public Parser(String fileName) {
        Lexer lexer = new Lexer(fileName);
        this.tokenList = lexer.getAllTokens();
        if (!tokenList.isEmpty()) {
            currentToken = tokenList.get(index);
            type = currentToken.getType();
            value = currentToken.getValue();
            symbolTable = new idTable();
        } else {
            currentToken = null;
        }
    }

    public void parseProgram() {
        // drives the process and parse an entire program
        // should call parseAssignment() within a loop
        while (index < tokenList.size()-1) {
            parseAssignment();
        }
    }

    public void parseAssignment() {
        // parse a single assignment statement
        // calls parseId(), parseAssignmentOp(), and parseExpression()

        if (type.equals("ID")) {
            parseId();
            nextToken();
            if (type.equals("ASSMT")) {
                parseAssignmentOp();
                nextToken();
                while(!type.equals("EOF")) {
                    parseExpression();
                    nextToken();
                }
            }
        } else {
            System.out.println("Expected an ID on line");
            return;
        }
        System.out.println("Valid Program");
    }

    public void parseId() {
        // parses a single identifier
        if (symbolTable.getAddress(currentToken.getValue()) == -1) {
            symbolTable.add(currentToken.getValue());
        }
    }

    public void parseAssignmentOp() {
        // parses a single assignment operator
        System.out.println("I'm in parseAssignmentOp(): " + currentToken);
    }

    public void parseExpression() {
        // parses an expression, i.e. the right hand side of the assignment
        // can include an unlimited number of "+" signs, e.g., "Y+3+4+..."
        System.out.println("I'm in parseExpression(): " + currentToken);
        if (type.equals("ID")) {

        }
    }

    public Token nextToken() {
        // gets the next token in the list and increments the index
        index++;
        currentToken = tokenList.get(index);
        value = currentToken.getValue();
        type = currentToken.getType();
        return currentToken;
    }

    public String toString() {
        //print out the token list and id table
        return "toString() :)";
    }

    public static void main(String[] args) {
        Parser test = new Parser("test.txt");
        test.parseProgram();
    }
}

/*PSEUDO-CODE TIME
* Call a Lexer with a specific file
* access the contents of the ArrayList<Tokens?
* Parse through the ListArray
* Check the contents of each element
* Then, check the contents of the element that was right before it
*
* Valid SIMPLE Statement
* ID = INT / knownID
*
* Make program recognize the START to a new SIMPLE statement / expression
* You Know a new expression STARTS when you have an unidentified IDTOKEN followed by an ASSMNT
*/