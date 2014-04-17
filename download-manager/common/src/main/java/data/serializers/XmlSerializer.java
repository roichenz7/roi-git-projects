package data.serializers;

import data.parser.IDataParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;

public abstract class XmlSerializer<T> implements ISerializer<T> {

    @Override
    public final void deserialize(String filename, IDataParser<T> dataParser) {
        InputStream inputStream = null;
        try {
            inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            Element root = doc.getDocumentElement();
            root.normalize();
            deserialize(root, dataParser);
        } catch (Exception e) {
            throw new RuntimeException("Failed de-serializing from configuration file: " + filename, e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                // Empty
            }
        }
    }

    /**
     * Performs de-serialization using root element
     *
     * @param root root element
     * @param dataParser data parser
     */
    protected void deserialize(Element root, IDataParser<T> dataParser) {
        NodeList elements = root.getElementsByTagName(getElementTagName());
        for (int i = 0; i < elements.getLength(); i++) {
            T element = createElement(elements.item(i));
            dataParser.add(element);
        }
    }

    /**
     * @return element tag name
     */
    protected abstract String getElementTagName();

    /**
     * Creates single data element from xml node
     *
     * @param node source xml node
     * @return created element
     */
    protected abstract T createElement(Node node);
}
