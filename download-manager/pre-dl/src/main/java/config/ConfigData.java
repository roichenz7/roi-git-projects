package config;

import data.ITvShowParser;
import data.TvShowData;
import data.TvShowParser;
import data.serializers.ISerializer;
import data.serializers.XmlSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.Collection;

public class ConfigData implements IConfigData {

    private String downloadDir;
    private Collection<TvShowData> ignoredShows;

    @Override
    public String downloadDir() {
        return downloadDir;
    }

    @Override
    public Collection<TvShowData> ignoredShows() {
        return ignoredShows;
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

            downloadDir = e.getAttribute("download_dir");
            if (downloadDir.isEmpty())
                return false;

            ITvShowParser tvShowParser = new TvShowParser() {
                @Override
                protected ISerializer<TvShowData> getSerializer() {
                    return new XmlSerializer<TvShowData>() {
                        @Override
                        protected String getElementTagName() {
                            return "ignored_tv_show";
                        }

                        @Override
                        protected TvShowData createElement(Node node) {
                            return new TvShowData(node);
                        }
                    };
                }
            };

            tvShowParser.deserialize(filename);
            ignoredShows = tvShowParser.getAll();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
