package html;

public class HTMLToken {
    public enum HTMLTokenType {
        HT_UNDEFINED,
        HT_NAME,
        HT_STRING,
        HT_COMMENT,
        HT_TEXT,
        HT_SYMBLE,
        HT_EOF
    }

    private HTMLTokenType type;
    private String value;

    public HTMLToken(HTMLTokenType t, String v) {
        this.type = t;
        this.value = new String(v);
    }

    public HTMLToken(HTMLTokenType t) {
        this.type = t;
        this.value = new String();
    }

    public HTMLTokenType getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String v) {
        this.value = new String(v);
    }
}
