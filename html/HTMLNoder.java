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

    /**
     * Get next node
     * @return the next node
     */
    public DOMNode nextNode() throws IOException {
        // return the previous if exists
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

    /**
     * Put back a node to this.previous
     * @param t the node to put back
     * @return t if success, else the origin this.previous is replaced and returned
     */
    public DOMNode backNode(DOMNode t) throws IOException {
        if (this.previous != null) {
            DOMNode ret = this.previous;
            this.previous = t;
            return ret;
        }
        return this.previous = t;
    }

    /**
     * Peek the next node, without getting it out
     * @return the next node
     */
    public DOMNode peekNode() throws IOException {
        DOMNode ret = this.nextNode();
        return this.backNode(ret);
    }

    /**
     * Parse the following token as a tag, until HT_RIGHT_ANGLE_BRACKET met
     * @return the parsed node
     */
    private DOMNode parseTag() throws IOException {
        DOMNode ret = new DOMNode();
        HTMLToken t = this.it.nextToken();  // the '<'
        
        t = this.it.nextToken();            // '/' or tag_name

        if (t.getType() == HTMLToken.HTMLTokenType.HT_SLASH) {
            // '/' met
            ret.type = DOMNode.DOMNodeType.DNT_CLOSE;
            t = this.it.nextToken();
        }
        else {
            // tag_name met
            ret.type = DOMNode.DOMNodeType.DNT_TAG;
        }

        if (t.getType() == HTMLToken.HTMLTokenType.HT_NAME) {
            ret.tag_name = t.getValue();
        }
        else {
            // TODO report parse error
        }

        // loop to parse attributes
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

    /**
     * Parse the next token as text
     * @return the next node
     */
    private DOMNode parseText() throws IOException {
        DOMNode ret = new DOMNode();
        ret.type = DOMNode.DOMNodeType.DNT_TEXT;

        HTMLToken t = this.it.nextToken();
        ret.text = t.getValue();
        return ret;
    }

    /**
     * Currently skip the next <!> tag
     * @return the next node
     */
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

    /**
     * Parse the next token as Comment
     * @return the next node
     */
    private DOMNode parseComment() throws IOException {
        DOMNode ret = new DOMNode();
        ret.type = DOMNode.DOMNodeType.DNT_COMMENT;

        HTMLToken t = this.it.nextToken();
        ret.text = t.getValue();
        return ret;
    }
}
