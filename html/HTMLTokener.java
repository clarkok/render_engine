package html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Stack;
import html.HTMLToken;

public class HTMLTokener {
    private Reader is;
    private String back_stack;
    private boolean next_is_text;

    public HTMLTokener (Reader input) {
        this.is = input.markSupported() ? input : new BufferedReader(input);
        this.back_stack = new String();
        this.next_is_text = true;
    } 

    /**
     * Get next token of the stream
     * @return the next token
     */
    public HTMLToken nextToken() throws IOException {
        char c = this.peek();

        //HT_TEXT
        if (this.next_is_text) {
            this.next_is_text = false;
            return new HTMLToken(HTMLToken.HTMLTokenType.HT_TEXT,
                    this.getUntil('<'));
        }

        // HT_NAME
        if ((c >= 'a' && c <= 'z') 
                || (c >= 'A' && c <= 'Z')) {
            return new HTMLToken(HTMLToken.HTMLTokenType.HT_NAME,
                    this.getName());
        }
        // HT_STRING
        else if ((c == '"') || (c == '\'')) {
            return new HTMLToken(HTMLToken.HTMLTokenType.HT_STRING,
                    this.getString());
        }
        else if (c == '<') {
            if (this.peek(4).equals("<!--")) {
                return new HTMLToken(HTMLToken.HTMLTokenType.HT_COMMENT,
                        this.getComment());
            }
            else if (this.peek(2).equals("<!")) {
                return new HTMLToken(HTMLToken.HTMLTokenType.HT_DEFINE,
                        this.get(2));
            }
            else {
                return new HTMLToken(HTMLToken.HTMLTokenType.HT_LEFT_ANGLE_BRACKET,
                        this.get(1));
            }
        }
        else if (c == '>') {
            this.next_is_text = true;
            return new HTMLToken(HTMLToken.HTMLTokenType.HT_RIGHT_ANGLE_BRACKET,
                    this.get(1));
        }
        else if (c == '/') {
            return new HTMLToken(HTMLToken.HTMLTokenType.HT_SLASH,
                    this.get(1));
        }
        else if (c == '=') {
            return new HTMLToken(HTMLToken.HTMLTokenType.HT_EQUAL,
                    this.get(1));
        }
        else if (c == 0) {
            return new HTMLToken(HTMLToken.HTMLTokenType.HT_EOF,
                    this.get(1));
        }
        else
            return new HTMLToken(HTMLToken.HTMLTokenType.HT_UNDEFINED,
                    this.get(1));
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
     * Peek the next n chars
     * @return the next n chars
     */
    private String peek(int n) throws IOException {
        String s = this.get(n);
        this.back(s);
        return s;
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
        if (!this.back_stack.isEmpty()) {
            int l = this.back_stack.length();
            c = this.back_stack.charAt(l-1);
            this.back_stack = this.back_stack.substring(0, l-1);
            return (char)c;
        }
        c = this.is.read();
        if (c == -1) {
            return 0;
        } 
        return (char)c;
    }

    /**
     * Put back a character to this.back_stack, so it can be got next time
     * @param c the character to be put back
     * @return c
     */
    private char back(char c) {
        this.back_stack += c;
        return c;
    }

    /**
     * Put back a string to this.back_stack, so it can be got next time
     * @param s the string to be put back
     * @return s
     */
    private String back(String s) {
        int l = s.length();
        for (int i = l-1; i >= 0; i--) {
            this.back_stack += s.charAt(i);
        }
        return s;
    }

    /**
     * Get next string of name
     * @return the word
     */
    private String getName() throws IOException {
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
     * Get next string matches ".*?"
     * @return the string
     */
    private String getString() throws IOException {
        String s = new String();
        char c;
        if (this.peek() != '\'' && this.peek() != '"') {
            return s;
        }
        s += this.next();
        s += this.getUntil('"');
        s += this.next();
        return s;
    }

    /**
     * Get next string matches "<!--.*?-->"
     * @return the string
     */
    private String getComment() throws IOException {
        String s = new String();
        if (this.peek(4) != "<!--")
            return s;
        s += this.get(4);
        s += this.getUntil('-');
        while (this.peek() != 0 && this.peek(3) != "-->") {
            s += this.get(3);
            s += this.getUntil('-');
        }
        s += this.get(3);
        return s;
    }

    /**
     * Get next n chars
     * @param n the number of chars to get
     * @return the string
     */
    private String get(int n) throws IOException {
        String s = new String();
        s += this.next();
        while (--n != 0) {
            s += this.nextSpace();
        }
        return s;
    }

    /**
     * Get the string *before* ct or EOF appears
     * @param ct the ct before
     * @return the string
     */
    private String getUntil(char ct) throws IOException {
        String s = new String();
        char c;
        while ((c = this.nextSpace()) != 0) {
            if (c != ct && (s.length() == 0 || s.charAt(s.length()-1) != '\\')) {
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
