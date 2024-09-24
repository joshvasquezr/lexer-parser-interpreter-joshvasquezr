import org.w3c.dom.ls.LSOutput;
import java.util.ArrayList;

public class Parser {
    //TODO implement Parser class here
    private final ArrayList<Token> tokenList;
    private int index = 0;
    private Token currentToken;
    private String type;
    private String value;
    private int line;
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
            line = currentToken.getLine();
            symbolTable = new idTable();
            goTo = false;
            ERROR = false;
            if (currentToken.type.equals(Lexer.IDTOKEN)) {
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
        } else {
            System.out.println("Valid Program");
        }
    }

    public boolean parseAssignment() {
        // parse a single assignment statement
        // calls parseId(), parseAssignmentOp(), and parseExpression()

        if (type.equals(Lexer.IDTOKEN)) {
            parseId();
            nextToken();
            if (type.equals(Lexer.ASSMTTOKEN)) {
                parseAssignmentOp();
                nextToken();
                while(!type.equals(Lexer.EOFTOKEN)) {
                    parseExpression();
                    if (goTo) {
                        return ERROR;
                    }
                    nextToken();
                }
            } else {
                System.out.println("Expecting assignment operator, line " + line);
                ERROR = true;
                return ERROR;
            }
        } else {
            System.out.println("Expected identifier, line " + line);
            ERROR = true;
            return ERROR;
        }
        return ERROR;
    }

    public boolean parseId() {
        // parses a single identifier
        if (symbolTable.getAddress(currentToken.getValue()) == -1) {
            symbolTable.add(currentToken.getValue());
        }
        currentID = currentToken.value;
        return ERROR;
    }

    public boolean parseAssignmentOp() {
        // parses a single assignment operator
        Token nextToken = tokenList.get(index+1);
        if (!(nextToken.type.equals(Lexer.INTTOKEN) || nextToken.type.equals(Lexer.IDTOKEN))) {
            System.out.println("Expecting identifier or integer, on line " + line);
            ERROR = true;
            return ERROR;
        }
        return ERROR;
    }

    public boolean parseExpression() {
        // parses an expression, i.e. the right hand side of the assignment
        // can include an unlimited number of "+" signs, e.g., "Y+3+4+..."
        if (type.equals(Lexer.IDTOKEN)) {
            if (symbolTable.getAddress(value) == -1) {
                Token nextToken = tokenList.get(index+1);
                if (nextToken.type.equals(Lexer.ASSMTTOKEN)) {
                    goTo = true;
                } else {
                    System.out.println("Identifier '" + value + "' not defined, line " + line);
                    ERROR = true;
                    return ERROR;
                }
            } else {
                if (currentToken.value.equals(currentID)){
                    System.out.println("Nonsensical Recursive Statement, line " + line);
                    ERROR = true;
                    return ERROR;
                }
            }
        } else if (type.equals("INT")) {
            Token nextToken = tokenList.get(index+1);
            if (nextToken.type.equals("INT")) {
                System.out.println("Expecting identifier or add operator, line " + line);
                ERROR = true;
                return ERROR;
            }
        }
        return ERROR;
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
        String fileName = "";
        fileName = args[0];
        Parser test = new Parser(fileName);
        test.parseProgram();
    }
}