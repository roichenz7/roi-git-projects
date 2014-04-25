package config;

import data.ShowData;
import data.TvShowData;
import enums.Quality;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import xml.NodeListFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class PreDLConfigData implements IPreDLConfigData {

    private String downloadDir;
    private Quality defaultQuality;
    private List<TvShowData> ignoredShows;
    private List<ShowData> specialShows;

    @Override
    public String downloadDir() {
        return downloadDir;
    }

    @Override
    public Quality defaultQuality() {
        return defaultQuality;
    }

    @Override
    public List<TvShowData> ignoredShows() {
        return ignoredShows;
    }

    @Override
    public List<ShowData> specialShows() {
        return specialShows;
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

            String str = e.getAttribute("default_quality");
            if (str.isEmpty())
                return false;
            else
                defaultQuality = Quality.fromString(str);

            ignoredShows = NodeListFactory.elementsByName(e, "ignored_tv_show")
                    .stream()
                    .map(TvShowData::new)
                    .collect(Collectors.toList());

            specialShows = NodeListFactory.elementsByName(e, "special_tv_show")
                    .stream()
                    .map(ShowData::new)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("pre-dl-config:\n");
        sb.append("download-dir: ").append(downloadDir);
        sb.append("; default-quality: ").append(defaultQuality).append("\n");

        sb.append("ignored-shows: ");
        ignoredShows.forEach(x -> sb.append(x.getName())
                .append(" (")
                .append(x.getId())
                .append("); "));

        sb.append("\nspecial-shows: ");
        specialShows.forEach(x -> sb.append(x.getTitle())
                .append(" (")
                .append(x.getId())
                .append("); "));

        return sb.toString();
    }
}
