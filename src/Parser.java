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
    private ArrayList<Integer> byteCode = new ArrayList<Integer>();
    private int currentIdAddress = 0;

    // Constructor: This is where we set things up to start reading the program
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

    // This reads the entire program and checks if it’s good or bad
    public void parseProgram() {
        while (index < tokenList.size()-1 && !ERROR) {
            goTo = false;
            parseAssignment();  // Keeps reading assignments until the end
            byteCode.add(2);
            byteCode.add(currentIdAddress);
            currentIdAddress++;
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

    // This reads the math part, like '3 + 4'
    public boolean parseExpression() {
        if (type.equals(Lexer.IDTOKEN)) {
            if (symbolTable.getAddress(value) == -1) {
                Token nextToken = tokenList.get(index + 1);
                if (nextToken.type.equals(Lexer.ASSMTTOKEN)) {
                    goTo = true;
                } else {
                    System.out.println("Identifier '" + value + "' not defined, line " + line);
                    ERROR = true;
                    return ERROR;
                }
            } else {
                if (currentToken.value.equals(currentID)) {
                    System.out.println("Nonsensical Recursive Statement, line " + line);
                    ERROR = true;
                    return ERROR;
                }

                // Generate ByteCode
                byteCode.add(0);
                byteCode.add(symbolTable.getAddress(value));
            }
        } else if (type.equals(Lexer.INTTOKEN)) {
            Token nextToken = tokenList.get(index + 1);
            if (nextToken.type.equals(Lexer.INTTOKEN)) {
                System.out.println("Expecting identifier or add operator, line " + line);
                ERROR = true;
                return ERROR;
            }
            byteCode.add(1);
            byteCode.add(Integer.parseInt(value));
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

    // This is the main function that starts the program
    public static void main(String[] args) {
        String fileName = "";  // Get the file name with the program code
        if (args.length == 0) {
            System.out.println("For this run, test.txt is used");
            fileName = "test.txt";
        } else {
            fileName = args[0];  // Take the first argument as the file name
        }
        Parser test = new Parser(fileName);  // Make a new parser to read the file
        test.parseProgram();  // Start reading and checking the whole program
    }
}