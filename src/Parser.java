import org.w3c.dom.ls.LSOutput;
import java.util.ArrayList;

public class Parser {
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
    private ByteCodeInterpreter interpreter;

    // Constructor: This is where we set things up to start reading the program
    public Parser(String fileName, ByteCodeInterpreter interpreter) {
        Lexer lexer = new Lexer(fileName);
        this.tokenList = lexer.getAllTokens();
        if (!tokenList.isEmpty()) {
            this.currentToken = tokenList.get(index);
            this.type = currentToken.getType();
            this.value = currentToken.getValue();
            this.line = currentToken.getLine();
            this.symbolTable = new idTable();
            this.interpreter = interpreter;
            this.goTo = false;
            this.ERROR = false;
            if (currentToken.type.equals(Lexer.IDTOKEN)) {
                this.currentID = currentToken.value;
            }
        } else {
            currentToken = null;
        }
    }

    // This reads the entire program and checks if it’s good or bad
    public void parseProgram() {
        while (index < tokenList.size()-1 && !ERROR) {
            goTo = false;
            parseAssignment();  // Keeps reading assignments until the end
        }
        if (ERROR) {
            System.out.println("Invalid Program");  // If something's wrong, say it's a bad program
        } else {
            System.out.println("Valid Program");  // If everything’s good, say it's a good program
        }
    }

    // This checks if we have a correct assignment, like 'X = 3;'
    public boolean parseAssignment() {
        if (type.equals(Lexer.IDTOKEN)) {
            parseId();  // First, we check the name (identifier)
            nextToken();  // Move to the next word (token)
            if (type.equals(Lexer.ASSMTTOKEN)) {
                parseAssignmentOp();  // Then, check the '='
                nextToken();
                while (!type.equals(Lexer.EOFTOKEN) && !ERROR) {
                    parseExpression();  // Then, we read the math part, like '3 + 4'
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
        if (type.equals(Lexer.EOFTOKEN)) {
            interpreter.generate(2,symbolTable.getAddress(currentID));
        }
        return ERROR;
    }

    // This looks at a name (identifier) and adds it to our memory
    public boolean parseId() {
        if (symbolTable.getAddress(currentToken.getValue()) == -1) {
            symbolTable.add(currentToken.getValue());  // If we don't know the name, remember it
        }
        currentID = currentToken.value;  // Save the current name
        return ERROR;
    }

    // This checks if we are using '=' correctly
    public boolean parseAssignmentOp() {
        Token nextToken = tokenList.get(index + 1);
        if (!(nextToken.type.equals(Lexer.INTTOKEN) || nextToken.type.equals(Lexer.IDTOKEN))) {
            System.out.println("Expecting identifier or integer, on line " + line);
            ERROR = true;
            return ERROR;
        }
        return ERROR;
    }

    // This reads the math part, like '3 + 4', 'x + 1', etc...
    public boolean parseExpression() {
        if (type.equals(Lexer.IDTOKEN)) {
            if (symbolTable.getAddress(value) == -1) {
                Token nextToken = tokenList.get(index + 1);
                if (nextToken.type.equals(Lexer.ASSMTTOKEN)) {
                    goTo = true;
                    interpreter.generate(2, symbolTable.getAddress(currentID));
                } else {
                    System.out.println("Identifier '" + value + "' not defined, line " + line);
                    ERROR = true;
                    return ERROR;
                }
            } else {
                Token nextToken = tokenList.get(index + 1);
                if (currentToken.value.equals(currentID)) {
                    System.out.println("Nonsensical Recursive Statement, line " + line);
                    ERROR = true;
                    return ERROR;
                } else if (nextToken.type.equals(Lexer.ASSMTTOKEN)) {
                    goTo = true;
                    interpreter.generate(2, symbolTable.getAddress(currentID));
                } else {
                    interpreter.generate(0, symbolTable.getAddress(value));
                }

                // Generate ByteCode

            }
        } else if (type.equals(Lexer.INTTOKEN)) {
            Token nextToken = tokenList.get(index + 1);
            if (nextToken.type.equals(Lexer.INTTOKEN)) {
                System.out.println("Expecting identifier or add operator, line " + line);
                ERROR = true;
                return ERROR;
            }
            interpreter.generate(1, Integer.parseInt(value));
        } else if (type.equals(Lexer.PLUSTOKEN) || type.equals(Lexer.SUBTOKEN) || type.equals(Lexer.DIVTOKEN) || type.equals(Lexer.MULTTOKEN)) {
            Token nextToken = tokenList.get(index + 1);
            if (!(nextToken.type.equals(Lexer.INTTOKEN) || nextToken.type.equals(Lexer.IDTOKEN))) {
                System.out.println("Expecting identifier or integer, line " + line);
                ERROR = true;
                return ERROR;
            }
        }
        return ERROR;
    }

    // This moves us to the next word (token) in the program
    public Token nextToken() {
        index++;  // Move to the next word
        currentToken = tokenList.get(index);  // Get the next word (token)
        value = currentToken.getValue();  // What’s the word?
        type = currentToken.getType();  // What kind of word is it?
        line = currentToken.getLine();  // What line is it on?
        return currentToken;
    }

    // method to get the SymbolTable to print in ByteCodeInterpreter
    public idTable getSymbolTable() {
        return symbolTable;
    }

    // method to get the boolean value of ERROR to print correct output if valid / invalid
    public boolean getError() {
        return ERROR;
    }

    // This is the main function that starts the program
    public static void main(String[] args) {
        String fileName = "";  // Get the file name with the program code
        if (args.length == 0) {
            System.out.println("For this run, test.txt is used");
            fileName = "test.txt";
        } else {
            fileName = args[0];  // Take the first argument as the file name
        }
        ByteCodeInterpreter interpreter = new ByteCodeInterpreter(10, fileName);
        Parser test = new Parser(fileName, interpreter);  // Make a new parser to read the file
        test.parseProgram();  // Start reading and checking the whole program
    }
}