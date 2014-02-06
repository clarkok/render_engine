package html;

import java.io.IOException;
import html.HTMLToken;
import html.HTMLTokener;
import html.dom.DOMNode;

public class HTMLNoder {
    private HTMLTokener it;
    private DOMNode previous;

    public HTMLNoder (HTMLTokener it) {
        this.it = it;
        this.previous = null;
    }

    public DOMNode nextNode() throws IOException {
        if (this.previous != null) {
            DOMNode ret = this.previous;
            this.previous = null;
            return ret;
        }

        HTMLToken t = this.it.peekToken();

        switch (t.getType()) {
            case HT_DEFINE:
                return this.parseDefine();
            case HT_TEXT:
                return this.parseText();
            case HT_LEFT_ANGLE_BRACKET:
                return this.parseTag();
            case HT_COMMENT:
                return this.parseComment();
            case HT_EOF:
                return null;
            default:
                return null;
        }
    }

    public DOMNode backNode(DOMNode t) throws IOException {
        if (this.previous != null) {
            DOMNode ret = this.previous;
            this.previous = t;
            return ret;
        }
        return this.previous = t;
    }

    public DOMNode peekNode() throws IOException {
        DOMNode ret = this.nextNode();
        return this.backNode(ret);
    }

    private DOMNode parseTag() throws IOException {
        DOMNode ret = new DOMNode();
        HTMLToken t = this.it.nextToken();  // the '<'
        
        t = this.it.nextToken();

        if (t.getType() == HTMLToken.HTMLTokenType.HT_SLASH) {
            ret.type = DOMNode.DOMNodeType.DNT_CLOSE;
            t = this.it.nextToken();
        }
        else {
            ret.type = DOMNode.DOMNodeType.DNT_TAG;
        }

        if (t.getType() == HTMLToken.HTMLTokenType.HT_NAME) {
            ret.tag_name = t.getValue();
        }
        else {
            // TODO report parse error
        }

        for (t = this.it.nextToken(); 
                t.getType() != HTMLToken.HTMLTokenType.HT_RIGHT_ANGLE_BRACKET;
                t = this.it.nextToken()) {
            if (t.getType() == HTMLToken.HTMLTokenType.HT_NAME) {
                String key = t.getValue();
                String value = new String();
                t = this.it.peekToken();
                if (t.getType() == HTMLToken.HTMLTokenType.HT_EQUAL) {
                    this.it.nextToken();

                    t = this.it.nextToken();
                    if (t.getType() == HTMLToken.HTMLTokenType.HT_NAME) {
                        value = t.getValue();
                    }
                    else if (t.getType() == HTMLToken.HTMLTokenType.HT_STRING) {
                        value = t.getValue();
                        value = value.substring(1, value.length()-1);
                    }
                    else {
                        // TODO report parse error
                    }
                }
                ret.attributes.put(key, value);
            }
            else {
                // TODO report parse error
            }
        }

        return ret;
    }

    private DOMNode parseText() throws IOException {
        DOMNode ret = new DOMNode();
        ret.type = DOMNode.DOMNodeType.DNT_TEXT;

        HTMLToken t = this.it.nextToken();
        ret.text = t.getValue();
        return ret;
    }

    private DOMNode parseDefine() throws IOException {
        // TODO parse the define tag
        DOMNode ret = new DOMNode();
        String value = new String();
        
        HTMLToken t;
        do {
            t = this.it.nextToken();
            value += t.getValue() + ' ';
        } while (t.getType() != HTMLToken.HTMLTokenType.HT_RIGHT_ANGLE_BRACKET);
        ret.type = DOMNode.DOMNodeType.DNT_DEFINE;
        ret.text = value;

        return ret;
    }

    private DOMNode parseComment() throws IOException {
        DOMNode ret = new DOMNode();
        ret.type = DOMNode.DOMNodeType.DNT_COMMENT;

        HTMLToken t = this.it.nextToken();
        ret.text = t.getValue();
        return ret;
    }
}
