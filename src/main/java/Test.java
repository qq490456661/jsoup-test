import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;


/**
 * Created by linjunjie on 2015/11/26 (linjunjie@raycloud.com).
 */
public class Test {
    public static void main(String[]args){
        System.out.println("hello world");
        String url = "http://www.izero.tv/";
        Parser parser = null;
        try {

            parser = new Parser(url);
            parser.setEncoding("UTF-8");
            NodeIterator iterator = parser.elements();
            while(iterator.hasMoreNodes()){
                Node node = iterator.nextNode();
                System.out.println(node.toHtml());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
