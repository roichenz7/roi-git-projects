package xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class NodeListFactory {

    /**
     * @param e source element
     * @param tagName tag name
     * @return node list
     */
    public static List<Node> elementsByName(Element e, String tagName) {
        List<Node> nodeList = new ArrayList<>();
        NodeList elements = e.getElementsByTagName(tagName);
        for (int i = 0; i < elements.getLength(); i++) {
            nodeList.add(elements.item(i));
        }
        return nodeList;
    }
}
