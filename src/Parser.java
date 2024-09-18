import org.w3c.dom.ls.LSOutput;
import java.util.ArrayList;

public class Parser {
    //TODO implement Parser class here
    public ArrayList<Token> tokenList;
//    public ArrayList<Token> idTable;

    // Constructor initializes Parser() data members
    public Parser(ArrayList<Token> idTable) {
        Lexer lexer = new Lexer("parseTest.txt");
        this.tokenList = lexer.getAllTokens();
//        this.idTable = idTable;
    }

    public void parseProgram() {
        // drives the process and parse an entire program
        // should call parseAssignment() within a loop
    }

    public void parseAssignment() {
        // parse a single assignment statement
        // calls parseId(), parseAssignmentOp(), and parseExpression()
    }

    public void parseId() {
        // parses a single identifier
    }

    public void parseAssignmentOp() {
        // parses a single assignment operator
    }

    public void parseExpression() {
        // parses an expression, i.e. the right hand side of the assignment
        // can include an unlimited number of "+" signs, e.g., "Y+3+4+..."
    }

    public void nextToken() {
        // gets the next token in the list and increments the index
    }

    public String toString() {
        //print out the token list and id table
        return "";
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