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
    private boolean ERROR;
    private boolean goTo;
    private String currentID;

    // Constructor initializes Parser() data members
    public Parser(String fileName) {
        Lexer lexer = new Lexer(fileName);
        this.tokenList = lexer.getAllTokens();
        if (!tokenList.isEmpty()) {
            currentToken = tokenList.get(index);
            type = currentToken.getType();
            value = currentToken.getValue();
            symbolTable = new idTable();
            goTo = false;
            ERROR = false;
            if (currentToken.type.equals("ID")) {
                currentID = currentToken.value;
            }
        } else {
            currentToken = null;
        }
    }

    public void parseProgram() {
        // drives the process and parse an entire program
        // should call parseAssignment() within a loop
        while (index < tokenList.size()-1 && !ERROR) {
            goTo = false;
            parseAssignment();
        }
        if (ERROR) {
            System.out.println("Invalid Program");
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
                    if (goTo) {
                        return;
                    }
                    nextToken();
                }
            } else {
                System.out.println("Expecting assignment operator");
                ERROR = true;
                return;
            }
        } else {
            System.out.println("Expected identifier");
            ERROR = true;
            return;
        }

        if (!ERROR) {
            System.out.println("Valid Program");
        }
    }

    public void parseId() {
        // parses a single identifier
        if (symbolTable.getAddress(currentToken.getValue()) == -1) {
            symbolTable.add(currentToken.getValue());
        }
        currentID = currentToken.value;
    }

    public void parseAssignmentOp() {
        // parses a single assignment operator
        Token nextToken = tokenList.get(index+1);
        if (!(nextToken.type.equals("INT") || nextToken.type.equals("ID"))) {
            System.out.println("Expecting identifier or integer");
            ERROR = true;
        }
    }

    public void parseExpression() {
        // parses an expression, i.e. the right hand side of the assignment
        // can include an unlimited number of "+" signs, e.g., "Y+3+4+..."
        if (type.equals("ID")) {
            if (symbolTable.getAddress(value) == -1) {
                Token nextToken = tokenList.get(index+1);
                if (nextToken.type.equals("ASSMT")) {
                    goTo = true;
                } else {
                    System.out.println("Identifier not defined");
                    ERROR = true;
                }
            } else {
                if (currentToken.value.equals(currentID)){
                    System.out.println("Nonsensical Recursive Statement");
                    ERROR = true;
                }
            }
        } else if (type.equals("INT")) {
            Token nextToken = tokenList.get(index+1);
            if (nextToken.type.equals("INT")) {
                System.out.println("Expecting identifier or add operator");
                ERROR = true;
            }
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
        Parser test = new Parser("parseTest.txt");
        test.parseProgram();
    }
}