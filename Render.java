import html.HTMLToken;
import html.HTMLTokener;
import html.HTMLNoder;
import html.dom.DOMNode;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;

public class Render {
    public static void main(String[] args) {
        try {
            HTMLTokener tokener = new HTMLTokener(
                    new FileReader(new File("/tmp/dashboard")));
            HTMLNoder noder = new HTMLNoder(tokener);
            DOMNode n;
            while ((n = noder.nextNode()) != null) {
                System.out.println(n);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

    }
}
