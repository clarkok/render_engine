#include <string>
#include "def.h"
#include "css_lex.h"

CSSLex::CSSLex(istream &input):BaseLex(input) {
}

Token *CSSLex::parseToken() {
    string buf;
    char next;
    if (l_is >> next) {
        if (next == '@') {
            buf += next;
            return new Token(CT_AT_SIGN, new Value(V_STRING, new string(buf)));
        }
        else if (next == '/') {
            char sub_buf = next;
            next = l_is.get();
            if (next == '/') {
                getline(l_is, buf);
                buf = "//" + buf;
                return new Token(CT_LINE_COMMENT, new Value(V_STRING, new string(buf)));
            }
            else if (next == '*') {
                buf += sub_buf;
                buf += next;
                return new Token(CT_COMMENT_BEGIN, new Value(V_STRING, new string(buf)));
            }
            else {
                l_is.putback(next);
                buf += sub_buf;
                return new Token(CT_UNDEFINED, new Value(V_STRING, new string(buf)));
            }
        }
        else if (next == '*') {
            char sub_buf = next;
            next = l_is.get();
            if (next == '/') {
                buf += sub_buf;
                buf += next;
                return new Token(CT_COMMENT_END, new Value(V_STRING, new string(buf)));
            }
            else {
                l_is.putback(next);
                buf += sub_buf;
                return new Token(CT_UNDEFINED, new Value(V_STRING, new string(buf)));
            }
        }
        else if (next == '{') {
            buf += next;
            return new Token(CT_BRACE_BEGIN, new Value(V_STRING, new string(buf)));
        }
        else if (next == '}') {
            buf += next;
            return new Token(CT_BRACE_END, new Value(V_STRING, new string(buf)));
        }
        else if (next == ':') {
            buf += next;
            return new Token(CT_COLON, new Value(V_STRING, new string(buf)));
        }
        else if (next == ';') {
            buf += next;
            return new Token(CT_SEMICOLON, new Value(V_STRING, new string(buf)));
        }
        else if (next == '(') {
            buf += next;
            while ((next = l_is.get()) != -1) {
                if (next == '\\')
                    next = l_is.get();
                buf += next;
                if (next == ')') {
                    return new Token(CT_BRACKETS, new Value(V_STRING, new string(buf)));
                }
            }
            return new Token(CT_BRACKETS, new Value(V_STRING, new string(buf)));
        }
        else if (next == '"' || next == '\'') {
            char quote = next;
            buf += next;
            while ((next = l_is.get()) != -1) {
                if (next == '\\')
                    next = l_is.get();
                buf += next;
                if (next == quote) {
                    return new Token(CT_STRING, new Value(V_STRING, new string(buf)));
                }
            }
            return new Token(CT_STRING, new Value(V_STRING, new string(buf)));
        }
        else if (next == ',') {
            buf += next;
            return new Token(CT_COMMA, new Value(V_STRING, new string(buf)));
        }
        else if (next == '#') {
            buf += next;
            while ((next = l_is.get()) != -1) {
                if ((next >= '0' && next <='9') ||
                        (next >= 'a' && next <= 'f') ||
                        (next >= 'A' && next <= 'F')){
                    buf += next;
                }
                else {
                    u32 l = buf.length();
                    if (l == 4 || l == 7) {
                        l_is.putback(next);
                        return new Token(CT_COLOR, new Value(V_STRING, new string(buf)));
                    }
                    else {
                        for (int i=l-1; i; i--) {
                            l_is.putback(buf[i]);
                        }
                        buf = buf.substr(0, 1);
                        return new Token(CT_UNDEFINED, new Value(V_STRING, new string(buf)));
                    }
                }
            }
            u32 l = buf.length();
            if (l == 4 || l == 7) {
                l_is.putback(next);
                return new Token(CT_COLOR, new Value(V_STRING, new string(buf)));
            }
            else {
                for (int i=l-1; i; i--) {
                    l_is.putback(buf[i]);
                }
                buf = buf.substr(0, 1);
                return new Token(CT_UNDEFINED, new Value(V_STRING, new string(buf)));
            }
        }
        else if (next == '-' || next == '.' ||
                (next >= 'a' && next <= 'z') ||
                (next >= 'A' && next <= 'Z') ||
                (next >= '0' && next <= '9')){
            buf += next;
            while ((next = l_is.get()) != -1) {
                if (next == '-' || next =='%' || next == '.' ||
                    (next >= 'a' && next <= 'z') ||
                    (next >= 'A' && next <= 'Z') ||
                    (next >= '0' && next <= '9')) {
                    buf += next;
                } 
                else {
                    l_is.putback(next);
                    return new Token(CT_ID, new Value(V_STRING, new string(buf)));
                }
            }
        }
        else {
            buf += next;
            return new Token(CT_UNDEFINED, new Value(V_STRING, new string(buf)));
        }
    }
    else {
        return new Token(CT_EOF, new Value(V_STRING, new string("")));
    }
    return new Token(CT_UNDEFINED, new Value(V_STRING, new string("")));
}
