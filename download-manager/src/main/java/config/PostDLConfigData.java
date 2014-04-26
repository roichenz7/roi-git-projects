package config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class PostDLConfigData implements IPostDLConfigData {

    private String sourceDir;
    private String targetDir;
    private String tvShowsDir;

    @Override
    public String sourceDir() {
        return sourceDir;
    }

    @Override
    public String targetDir() {
        return targetDir;
    }

    @Override
    public String tvShowsDir() {
        return tvShowsDir;
    }

    @Override
    public boolean parse(String filename) {
        try {
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            Element e = doc.getDocumentElement();
            e.normalize();

            sourceDir = e.getAttribute("source_dir");
            if (sourceDir.isEmpty())
                return false;

            targetDir = e.getAttribute("target_dir");
            if (targetDir.isEmpty())
                return false;

            tvShowsDir = e.getAttribute("tv_shows_dir");
            if (tvShowsDir.isEmpty())
                return false;

        } catch (Exception e) {
            return false;
        }

        return true;
    }
}