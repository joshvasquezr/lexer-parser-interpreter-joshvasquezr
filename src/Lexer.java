import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to build an array of Tokens from an input file
 * @author wolberd
 * @see Token
 * @see Parser
 */
public class Lexer {

    String buffer;
    private int index = 0;
    private int line = 1;
    public static final String INTTOKEN="INT";
    public static final String IDTOKEN="ID";
    public static final String ASSMTTOKEN="ASSMT";
    public static final String PLUSTOKEN="PLUS";
    public static final String EOFTOKEN="EOF";
    public static final String SUBTOKEN="SUB";
    public static final String DIVTOKEN="DIV";
    public static final String MULTTOKEN="MULT";

    /**
     * call getInput to get the file data into our buffer
     * @param fileName the file we open
     */

    public Lexer(String fileName) {
        getInput(fileName);
    }

    // This method gets the next small piece (token) from the text.
    public Token getNextToken() {
        while (index < buffer.length() && Character.isWhitespace(buffer.charAt(index))) {
            if (buffer.charAt(index) == '\n') {
                line++;
            }
            index++;
        }
        if (index >= buffer.length()) {
            return new Token(EOFTOKEN, "-", line);
        }

        char currentChar = buffer.charAt(index);
        if (Character.isLetter(currentChar)){
            return getIdentifier();
        } else if (Character.isDigit(currentChar)) {
            return getInteger();
        } else if (currentChar == '=') {
            index++;
            return new Token(ASSMTTOKEN, Character.toString(currentChar),line);
        } else if (currentChar == '+') {
            index++;
            return new Token(PLUSTOKEN, Character.toString(currentChar),line);
        } else if (currentChar == '/') {
            index++;
            return new Token(DIVTOKEN, Character.toString(currentChar),line);
        } else if (currentChar == '*') {
            index++;
            return new Token(MULTTOKEN, Character.toString(currentChar),line);
        } else if (currentChar == '-') {
            index++;
            return new Token(SUBTOKEN, Character.toString(currentChar),line);
        } else {
            return new Token(EOFTOKEN, "-",line);
        }

    }

    // This method looks at the letters in the text.
    // It grabs all the letters and numbers that form a word or name (identifier).
    // Once it finds something else, it stops and gives back the word as a token.
    private Token getIdentifier() {
        if (buffer == null || buffer.isEmpty()) {
            return null;
        }

        StringBuilder sbToken = new StringBuilder();
        while (index < buffer.length()) {
            char c = buffer.charAt(index);
            if (Character.isLetter(c) || Character.isDigit(c)) {
                sbToken.append(c);
                index++;
            } else {
                break;
            }
        }
        if (sbToken.length() > 0) {
            return new Token(IDTOKEN, sbToken.toString(), line);
        }
        return null;
    }

    // This method looks for numbers in the text.
    // It grabs all the digits that form a number.
    // Once it finds something else, it stops and gives back the number as a token.
    private Token getInteger() {
        if (buffer == null || buffer.isEmpty()) {
            return null;
        }

        StringBuilder sbToken = new StringBuilder();
        while (index < buffer.length()) {
            char c = buffer.charAt(index);
            if (Character.isDigit(c)) {
                sbToken.append(c);
                index++;
            } else {
                break;
            }
        }

        if (sbToken.length() > 0) {
            return new Token(INTTOKEN, sbToken.toString(),line);
        }
        return null;
    }
    /**
     * Reads given file into the data member buffer
     * @param fileName name of file to parse
    */
    private void getInput(String fileName)  {
        try {
            Path filePath = Paths.get(fileName);
            byte[] allBytes = Files.readAllBytes(filePath);
            buffer = new String (allBytes);
        } catch (IOException e) {
            System.out.println ("You did not enter a valid file name in the run arguments.");
            System.out.println ("Please enter a string to be parsed:");
            Scanner scanner = new Scanner(System.in);
            buffer=scanner.nextLine();
        }
    }

    // This method keeps getting tokens until it finds the EOF (End of File) token.
    // It puts all the tokens in a list and returns the list.
    public ArrayList<Token> getAllTokens(){
        //TODO: place your code here for lexing file
        ArrayList<Token> tokenList = new ArrayList<>();
        boolean done = false;
        while (!done) {
            Token nextToken = getNextToken();
            tokenList.add(nextToken);
            if (nextToken.toString().equals(EOFTOKEN + " - " +line)) {
                done = true;
            }
        }

        return tokenList; // don't forget to change the return statement
    }



    /**
     * Before your run this starter code
     * Select Run | Edit Configurations from the main menu.
     * In Program arguments add the name of file you want to test (e.g., test.txt)
     * @param args args[0]
     */
    public static void main(String[] args) {
        String fileName="";
        if (args.length==0) {
            System.out.println("You can test a different file by adding as an argument");
            System.out.println("See comment above main");
            System.out.println("For this run, test.txt used");
            fileName="test.txt";
        } else {

            fileName=args[0];
        }
        Lexer lexer = new Lexer(fileName);
        // just print out the text from the file
        System.out.println(lexer.getAllTokens());
        // here is where you'll call getAllToken
    }
}
	