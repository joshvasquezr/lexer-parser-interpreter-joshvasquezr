# Lexer Project (2a)
## Project Overview
The Lexer Project (2a) aims to tokenize the different parts of SIMPLE code in a  `.txt` file.
When `Lexer.java` is passed a `.txt` file, it should return tokens:
* `IDTOKEN` - a continues String of letters and integers. An ID must have a letter at the beginning of the String.
* `INTTOKEN` - a continuous String of exclusively integers.
* `ASSNMTTOKEN` - an assignment operator, `=`.
* `PLUSTOKEN` - an addition operator, `+`.
* `EOFTOKEN` - a `-`, signalling the End Of File.

`Lexer.java` should be able to output the above as Tokens, extrapolated from the `.txt` file, with an `EOFTOKEN` `-` 
at the end of every tokenized `.txt` file.

## How to Deploy `Lexer.java`
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
   If you want to use `Lexer.java` to parse another file, simply subsitute `your file name` for `test` in `test.txt`.
4. 