package config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;


public class ConfigData implements IConfigData {

    private String sourceDir;
    private String targetDir;
    private String tvShowsDir;
    private boolean isMarkAsAcquired;
    private String username;
    private String password;

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
    public boolean isMarkAsAcquired() {
        return isMarkAsAcquired;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
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

            String attr = e.getAttribute("mark_as_acquired");
            if (attr != null) {
                isMarkAsAcquired = Boolean.parseBoolean(attr);
            }

            username = e.getAttribute("username");
            if (username.isEmpty())
                return false;

            password = e.getAttribute("password");
            if (password.isEmpty())
                return false;

        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
