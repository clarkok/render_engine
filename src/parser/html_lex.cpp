#include <string>
#include "def.h"
#include "html_lex.h"

HTMLLex::HTMLLex(istream &input):BaseLex(input) {
}

Token *HTMLLex::parseToken() {
    string buf;
    char next;
    if (l_is >> next) {
        if (next == '<') {              // <
            buf += next;
            next = l_is.get();
            if (next == '!') {          // <!
                buf += next;
                next = l_is.get();
                if (next == '-') {      // <!-
                    char sub_buf = next;
                    next = l_is.get();
                    if (next == '-') {  // <!--
                        buf += sub_buf;
                        buf += next;
                        return new Token(HT_COMMENT_BEGIN, new Value(V_STRING, new string(buf)));
                    }
                    l_is.putback(next);
                    l_is.putback(sub_buf);
                }
                l_is.putback(next);
                return new Token(HT_DECLARATION, new Value(V_STRING, new string(buf)));
            }
            l_is.putback(next);
            return new Token(HT_ANGLE_BRACKET_BEGIN, new Value(V_STRING, new string(buf)));
        }
        else if (next == '-') {         // -
            next = l_is.get();
            if (next == '-') {          // --
                char sub_buf = next;
                next = l_is.get();
                if (next == '>') {      // -->
                    buf += '-';
                    buf += sub_buf;
                    buf += next;
                    return new Token(HT_COMMENT_END, new Value(V_STRING, new string(buf)));
                }
                l_is.putback(next);
                l_is.putback(sub_buf);
            }
            l_is.putback(next);
            return new Token(HT_UNDEFINED, new Value(V_STRING, new string(buf)));
        }
        else if (next == '=') {
            buf += next;
            return new Token(HT_EQUAL_SIGN, new Value(V_STRING, new string(buf)));
        }
        else if (next == '/') {
            buf += next;
            return new Token(HT_SLASH, new Value(V_STRING, new string(buf)));
        }
        else if (next == '"' || next == '\'') {
            char quote = next;
            buf += next;
            while ((next = l_is.get()) != quote) {
                if (next == '\\') {
                    next = l_is.get();
                }
                buf += next;
            }
            buf += next;
            return new Token(HT_STRING, new Value(V_STRING, new string(buf)));
        }
        else if (next == '>') {
            buf += next;
            while ((next = l_is.get()) != -1) {
                if (next == '<') {
                    l_is.putback(next);
                    return new Token(HT_ANGLE_BRACKET_END, new Value(V_STRING, new string(buf)));
                }
                buf += next;
            }
            return new Token(HT_ANGLE_BRACKET_END, new Value(V_STRING, new string(buf)));
        }
        else if ((next >= 'a' && next <= 'z') || 
                    (next >= 'A' && next <= 'Z')) {
            buf += next;
            while ((next = l_is.get())) {
                if ((next >= 'a' && next <= 'z') || 
                        (next >= 'A' && next <= 'Z') ||
                        (next >= '0' && next <= '9')) {
                    buf += next;
                }
                else {
                    l_is.putback(next);
                    return new Token(HT_ID, new Value(V_STRING, new string(buf)));
                }
            }
        }
        else {
            buf += next;
            return new Token(HT_UNDEFINED, new Value(V_STRING, new string(buf)));
        }
    }
    else {
        return new Token(HT_EOF, new Value(V_STRING, new string("")));
    }
    return new Token();
}
