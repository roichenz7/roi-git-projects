package data;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class TvShowData {

    private int id;
    private String name;

    public TvShowData() {
    }

    public TvShowData(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());
        name = attributes.getNamedItem("name").getNodeValue();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "TvShowData: " + id + " <-> " + name;
    }
}
