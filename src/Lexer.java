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
    int index = 0;
    public static final String INTTOKEN="INT";
    public static final String IDTOKEN="ID";
    public static final String ASSMTTOKEN="ASSMT";
    public static final String PLUSTOKEN="PLUS";
    public static final String EOFTOKEN="EOF";

    /**
     * call getInput to get the file data into our buffer
     * @param fileName the file we open
     */
    public Lexer(String fileName) {
        getInput(fileName);
    }
    public Token getNextToken() {
        while (index < buffer.length() && Character.isWhitespace(buffer.charAt(index))) {
            index++;
        }
        if (index >= buffer.length()) {
            return new Token(EOFTOKEN, "-");
        }

        char currentChar = buffer.charAt(index);
        if (Character.isLetter(currentChar)){
            return getIdentifier();
        } else if (Character.isDigit(currentChar)) {
            return getInteger();
        } else if (currentChar == '=') {
            index++;
            return new Token(ASSMTTOKEN, Character.toString(currentChar));
        } else if (currentChar == '+') {
            index++;
            return new Token(PLUSTOKEN, Character.toString(currentChar));
        } else {
            return new Token(EOFTOKEN, "-");
        }

    }

    public Token getIdentifier() {
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
            return new Token(IDTOKEN, sbToken.toString());
        }
        return null;
    }

    public Token getInteger() {
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
            return new Token(INTTOKEN, sbToken.toString());
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

    /**
     * Return all the token in the file
     * @return ArrayList of Token
     */

    public ArrayList<Token> getAllTokens(){
        //TODO: place your code here for lexing file
        ArrayList<Token> tokenList = new ArrayList<>();
        boolean done = false;
        while (!done) {
            Token nextToken = getNextToken();
            tokenList.add(nextToken);
            if (nextToken.toString().equals(EOFTOKEN + " -")) {
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
        Lexer lexer = new Lexer("testWhitespace.txt");
        // just print out the text from the file
        System.out.println(lexer.getAllTokens());
        // here is where you'll call getAllToken
    }
}
	