package xml;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

public class DocumentFactory {

    /**
     * Creates an xml document from source string
     *
     * @param source source string
     * @return xml document
     */
    public static Document createFromString(String source) {
        Document doc;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(new InputSource(new StringReader(source)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create xml document from source", e);
        }
        return doc;
    }

    /**
     * Creates an xml document from source filename
     *
     * @param filename source filename
     * @return xml document
     */
    public static Document createFromFilename(String filename) {
        Document doc;
        InputStream inputStream = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            File file = new File(filename);
            if (file.exists()) {
                doc = dBuilder.parse(file);
            } else {
                inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
                doc = dBuilder.parse(inputStream);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create xml document from filename", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                // Empty
            }
        }
        return doc;
    }
}
