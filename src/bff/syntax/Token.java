
package bff.syntax;

/** This is a single result from a process of syntax based lexing;
 * it is an item constructed from an input stream, basing on the structure
 * of a syntax.
 * 
 * @author pvto https://github.com/pvto
 */
public class Token {

    public String word;
    public Syntax syntax;
    public Token prev;
    
}
