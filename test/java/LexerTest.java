import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class LexerTest {

    @Test
    public void test(){
        Lexer lexer = new Lexer("test.txt");
        ArrayList<Token> tokens = lexer.getAllTokens();
        String actual = stringifyListOfToken(tokens);
        System.out.println("actual: "+System.lineSeparator()+ actual);

        String expected ="ID xyz" + System.lineSeparator() +
                "ASSMT =" + System.lineSeparator()+
                "INT 33"+ System.lineSeparator()+
                "ID zz12"+System.lineSeparator()+
                "ASSMT ="+System.lineSeparator()+
                "INT 99"+ System.lineSeparator()+
                "PLUS +"+ System.lineSeparator()+
                "INT 88"+ System.lineSeparator()+
                "PLUS +"+ System.lineSeparator()+
                "ID xyz"+ System.lineSeparator()+
                "EOF -";
        System.out.println("expect: "+System.lineSeparator()+ expected);
        if (!expected.equals(actual)  && !expected.equals(actual.trim()))
            Assert.fail("The string returned by your program did not match any of strings that are considered correct representation of the list of token.");
    }


    @Test
    public void testExpectingAssignOp(){
        Lexer lexer = new Lexer("testExpectingAssignOp.txt");
        ArrayList<Token> tokens = lexer.getAllTokens();
        String actual = stringifyListOfToken(tokens);
        System.out.println("actual: "+System.lineSeparator()+ actual);

        String expected ="ID x32" + System.lineSeparator() +
                "INT 54" + System.lineSeparator()+
                "ASSMT ="+System.lineSeparator()+
                "INT 87"+System.lineSeparator()+
                "EOF -";
        System.out.println("expect: "+System.lineSeparator()+ expected);
        if (!expected.equals(actual)  && !expected.equals(actual.trim()))
            Assert.fail("The string returned by your program did not match any of strings that are considered correct representation of the list of token.");
    }


    @Test
    public void testExpectingIdOrInt2(){
        Lexer lexer = new Lexer("testExpectingIdOrInt2.txt");
        ArrayList<Token> tokens = lexer.getAllTokens();
        String actual = stringifyListOfToken(tokens);
        System.out.println("actual: "+System.lineSeparator()+ actual);

        String expected ="ID x" + System.lineSeparator() +
                "ASSMT =" + System.lineSeparator()+
                "INT 32"+System.lineSeparator()+
                "INT 32"+System.lineSeparator()+
                "EOF -";
        System.out.println("expect: "+System.lineSeparator()+ expected);
        if (!expected.equals(actual)  && !expected.equals(actual.trim()))
            Assert.fail("The string returned by your program did not match any of strings that are considered correct representation of the list of token.");
    }


    @Test
    public void testMultiplePlus(){
        Lexer lexer = new Lexer("testMultiplePlus.txt");
        ArrayList<Token> tokens = lexer.getAllTokens();
        String actual = stringifyListOfToken(tokens);
        System.out.println("actual: "+System.lineSeparator()+ actual);

        String expected ="ID x32" + System.lineSeparator() +
                "ASSMT =" + System.lineSeparator()+
                "INT 77"+ System.lineSeparator()+
                "ID yyy9"+System.lineSeparator()+
                "ASSMT ="+System.lineSeparator()+
                "ID x32"+ System.lineSeparator()+
                "PLUS +"+ System.lineSeparator()+
                "INT 5"+ System.lineSeparator()+
                "PLUS +"+ System.lineSeparator()+
                "INT 4"+ System.lineSeparator()+
                "PLUS +"+ System.lineSeparator()+
                "ID x32"+ System.lineSeparator()+
                "EOF -";
        System.out.println("expect: "+System.lineSeparator()+ expected);
        if (!expected.equals(actual)  && !expected.equals(actual.trim()))
            Assert.fail("The string returned by your program did not match any of strings that are considered correct representation of the list of token.");
    }


    @Test
    public void testWhitespace(){
        Lexer lexer = new Lexer("testWhitespace.txt");
        ArrayList<Token> tokens = lexer.getAllTokens();
        String actual = stringifyListOfToken(tokens);
        System.out.println("actual: "+System.lineSeparator()+ actual);

        String expected ="ID x32" + System.lineSeparator() +
                "ASSMT =" + System.lineSeparator()+
                "INT 83"+ System.lineSeparator()+
                "PLUS +"+System.lineSeparator()+
                "ID yzu"+ System.lineSeparator()+
                "ID rt"+ System.lineSeparator()+
                "ASSMT ="+ System.lineSeparator()+
                "INT 2"+ System.lineSeparator()+
                "EOF -";
        System.out.println("expect: "+System.lineSeparator()+ expected);
        if (!expected.equals(actual)  && !expected.equals(actual.trim()))
            Assert.fail("The string returned by your program did not match any of strings that are considered correct representation of the list of token.");
    }


    @Test
    public void testExpectingId2(){
        Lexer lexer = new Lexer("testExpectingId2.txt");
        ArrayList<Token> tokens = lexer.getAllTokens();
        String actual = stringifyListOfToken(tokens);
        System.out.println("actual: "+System.lineSeparator()+ actual);

        String expected ="INT 32" + System.lineSeparator() +
                "ASSMT =" + System.lineSeparator()+
                "INT 54"+ System.lineSeparator()+
                "EOF -";
        System.out.println("expect: "+System.lineSeparator()+ expected);
        if (!expected.equals(actual)  && !expected.equals(actual.trim()))
            Assert.fail("The string returned by your program did not match any of strings that are considered correct representation of the list of token.");
    }

    /**
     * Stringify the tokens into String to pass Lexer Test
     * @param tokens ArrayList<Token>
     * @return String
     */
    public String stringifyListOfToken(ArrayList<Token> tokens){
        StringBuilder sb = new StringBuilder();
        for(Token token: tokens){
            sb.append(token.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}