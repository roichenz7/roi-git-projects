package config;

import data.ShowData;
import data.TvShowData;
import enums.Quality;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import xml.DocumentFactory;
import xml.NodeListFactory;

import java.util.List;
import java.util.stream.Collectors;

public class PreDLConfigData implements IPreDLConfigData {

    private String downloadDir;
    private Quality defaultQuality;
    private String defaultProvider;

    private List<TvShowData> ignoredShows;
    private List<ShowData> specialShows;
    private List<String> acceptedOrigins;

    @Override
    public String downloadDir() {
        return downloadDir;
    }

    @Override
    public Quality defaultQuality() {
        return defaultQuality;
    }

    @Override
    public String defaultProvider() {
        return defaultProvider;
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
    public List<String> acceptedOrigins() {
        return acceptedOrigins;
    }

    @Override
    public boolean parse(String filename) {
        try {
            Document doc = DocumentFactory.createFromFilename(filename);
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

            defaultProvider = e.getAttribute("default_provider");
            if (defaultProvider.isEmpty())
                return false;

            ignoredShows = NodeListFactory.elementsByName(e, "ignored_tv_show")
                    .stream()
                    .map(TvShowData::new)
                    .collect(Collectors.toList());

            specialShows = NodeListFactory.elementsByName(e, "special_tv_show")
                    .stream()
                    .map(ShowData::new)
                    .collect(Collectors.toList());

            acceptedOrigins = NodeListFactory.elementsByName(e, "accepted_origin")
                    .stream()
                    .map(Node::getTextContent)
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
        sb.append("; default-quality: ").append(defaultQuality);
        sb.append("; default-provider: ").append(defaultProvider).append("\n");

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

        sb.append("\naccepted-origins: ");
        acceptedOrigins.forEach(x -> sb.append(x)
                .append("; "));

        return sb.toString();
    }
}
