package config;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ConfigData implements IConfigData {

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
            File f = new File(filename);
            if (!f.exists())
                return false;

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(f);
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
            e.printStackTrace();
        }

        return true;
    }


    private String sourceDir;
    private String targetDir;
    private String tvShowsDir;
}
