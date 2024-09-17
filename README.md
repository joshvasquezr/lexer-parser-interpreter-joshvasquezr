# Lexer Project (2a)
## Project Overview
The Lexer Project (2a) aims to tokenize the different parts of SIMPLE code in a  `.txt` file.
When `Lexer.java` is passed a `.txt` file, it should return tokens:
* `IDTOKEN` - a continues String of letters and integers. An ID must have a letter at the beginning of the String.
* `INTTOKEN` - a continuous String of exclusively integers.
* `ASSNMTTOKEN` - an assignment operator, `=`.
* `PLUSTOKEN` - an addition operator, `+`.
* `EOFTOKEN` - a `-`, signalling the End Of File.

`Lexer.java` should be able to output the above as Tokens, extrapolated from the `.txt` file, with an 
`EOFTOKEN` `-` 
at the end of every tokenized `.txt` file.

## How to Deploy `Lexer.java`

### Prerequisites
- Ensure that you have Java installed on your machine. You can check if Java is installed by running:
```bash
 java -version
 ```
To run the `Lexer.java` program on your local machine, follow these steps:
1. **Clone the Repository:**
   - Open your terminal and run the following command to clone the repository:
   ```bash 
   git clone https://github.com/usf-cs514/lexer-parser-interpreter-joshvasquezr.git
   - ```
   - Navigate into the project repository
   ```bash
   cd lexer-parser-interpreter-joshvasquezr 
   ```
2. **Compile the Code**
   * Use the following command to compile the Java files:
   ```bash
   javac src/Token.java src/Lexer.java 
   ```
   
3. **Run `Lexer.java`**
   * To run the program use the following command:
   ```bash
   java -cp src Lexer test.txt 
   ```
4. **Change `.txt` file**
* If you want to use `Lexer.java` to parse another file, simply substitute `your file name` for `test` in 
`test.txt`.

### Development Process
This project was a fun challenge. Adding in new elements such as ArrayList<> data types, continuing to practice
Object-Oriented Programming principles, and figuring out how to parse and tokenize text from a `.txt` files. This
project required an understanding of each method: `getIdentifier()`, `getInteger()`, `getNextToken()`, and
`getAllTokens()`. I will briefly describe my thought process behind each method...
* **`getAllTokens()`** - returns a `ListArray<Tokens>`. It retrieves each Token from the file by calling 
the `getNextToken()` method.
* `getNextToken()` - based on what character the global variable `index` is at, it will determine whether the Next
Token will be an `ID`, `INT`, `=`, `+`, or `-` and return that Token.
  * the logical implementation was simple. If the current character is a letter, call `getIdentifier()`. If the current
  character is an integer, call `getInteger()`. If the current character is an `=` or `+`, return the appropriate Token
  albeit `ASSMNTTOKEN` or `PLUSTOKEN`. Or finally, if all Tokens have been identified, return the `EOFTOKEN`.
* `getInteger()` - this will return a `String` of integers. In SIMPLE, an integer can ONLY be a continuous String of 
integers. No spaces, no special symbols, no letters.
  * since we know our current index holds an integer, thanks to the `getNextToken()` logic, all I need to do is check
  that as long as the current character is an integer, I can keep appending it onto the `sbToken` `StringBuilder` I 
   initialized. However, once my current character is no longer an integer I will return the contents of `sbToken` in 
  `String` form.
* `getIdentifier()` - not quite as exclusive as `getInteger()`, this will return a String that can be a mix of letters
and integers. However, its only requirement is that the ID (or Identifier) MUST start with a letter. After the first
character, it must be a continuous String of letters or integers. Again, no spaces, no special symbols.
  * Again, thanks to `getNextToken()`'s logic, I know the current character is a letter, so all I need to check for is
  if the current character is either a letter or an integer. If it is, I will simply append the current character to
   `sbToken`. Otherwise, I will return the contents of `sbToken` back to `getNextToken()`.

Two occurrences made this project slightly harder than I had anticipated. 
1. The first, was my mistake. I started coding 
the logic to `getIdentifier()` and `getInteger()` before I fully understood the function of `Lexer.java`, and more 
specifically, before I understood the **roles** of `getNextToken()` and `getAllTokens()`. This caused me to write the
code for `getIdentifier()` and `getInteger()` as returning ALL POSSIBLE ID's and int's with one call of `getIdentifier()`
or `getInteger()`. The nice part is that the logic for both methods only needed to be simplified. 
2. The second, was my difficulty with adding the `EOFTOKEN` as the last Token of the list of Tokens `getAllTokens()` 
would return. The difficulty was two-fold. I would either get the correct `EOFTOKEN` for the `testExpectingIdOrInt2.txt`
and `testWhitespace.txt` or all the rest of the files except those previous two. If I was able to get an `EOFTOKEN` at
the end of the rest of the files, I would have **two** `EOFTOKENS` at the end of `testWhitespace` and 
`testExpectingIdOrInt2.txt`. I

**Solution to `EOFTOKEN` problem:**
I simply added an `if` statement within the `while` loop in `getAllTokens()`. This `if` statement checked to see if the 
`ListArray<Tokens>` already had an `EOFTOKEN` in it. If it did, it would simply break the `while` loop and return the
`ListArray<Tokens>`. However, if `EOFTOKEN` was not in the ListArray, then it would append a `new Token(EOFTOKEN, " -")`
to the `ListArray<Tokens>`.