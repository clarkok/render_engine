package html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Stack;
import html.HTMLToken;

public class HTMLTokener {
    private Reader is;
    private Stack<HTMLToken> back_stack;
    private char previous;

    public HTMLTokener (Reader input) {
        this.is = input.markSupported() ? input : new BufferedReader(input);
        this.back_stack = new Stack<HTMLToken>();
    } 

    /**
     * Put back a token
     * @param t the token to be put back
     * @return the token be put back
     */
    public HTMLToken putBack(HTMLToken t) {
        return this.back_stack.push(t);
    }

    /**
     * Get next token of the stream
     * @return the next token
     */
    public HTMLToken nextToken() {
        if (!this.back_stack.empty()) {
            return this.back_stack.pop();
        }
        // TODO
        return new HTMLToken(HTMLToken.HTMLTokenType.HT_UNDEFINED);
    }

    /**
     * Peek the next non-space character from the stream
     * @return the next character
     */
    private char peek() throws IOException {
        char c = this.next();
        this.back(c);
        return c;
    }

    /**
     * Get the next non-space character in the stream
     * @return the next non-space character, when EOF reached, 0 is returned
     */
    private char next() throws IOException {
        int c;
        while ((c = this.nextSpace()) != 0) {
            if (
                (c != ' ') &&
                (c != '\t') &&
                (c != '\r') &&
                (c != '\n'))
                break;
        }
        if (c == -1) c = 0;
        return (char)c;
    }

    /**
     * Get the next character in the stream, no matter it is space or not
     * @return the next character, when EOF reached, 0 is returned
     */
    private char nextSpace() throws IOException {
        int c;
        if (this.previous != 0) {
            c = this.previous;
            this.previous = 0;
            return (char)c;
        }
        return (c = this.is.read()) == -1 ? 0 : (char)c;
    }

    /**
     * Put back a character to this.previous, so it can be got next time
     * @param c the character to be put back
     * @return c if succeed, 0 if failed
     */
    private char back(char c) {
        if (this.previous != 0) {
            return 0;
        }
        else {
            return this.previous = c;
        }
    }

    /**
     * Get next string of word
     * @return the word
     */
    private String get() throws IOException {
        String s = new String();
        char c;
        while ((c = this.next()) != 0) {
            if (
                    (c >= 'a' && c <= 'z') 
                    || (c >= 'A' && c <= 'Z')) 
                break;
        }
        s += c;
        while ((c = this.nextSpace()) != 0) {
            if (
                    (c >= 'a' && c <= 'z') 
                    || (c >= 'A' && c <= 'Z')
                    || (c >= '0' && c <= '9')
                    || (c == '-')
                    || (c == '_')
                    || (c == ':')
                    || (c == '.')) 
                s += c;
            else {
                this.back(c);
                break;
            }
        }
        return s;
    }

    /**
     * Get the string before ct or EOF appears
     * @param ct the ct before
     * @return the string
     */
    private String getUntil(char ct) throws IOException {
        String s = new String();
        char c;
        while ((c = this.next()) != 0) {
            if (c != ct) {
                s += c;
            }
            else {
                this.back(c);
                break;
            }
        }
        return s;
    }

}
