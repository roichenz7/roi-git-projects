package data;

import org.jsoup.nodes.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Collection;

public class TvShowData {

    private int id;
    private String name;

    private Collection<EpisodeData> acquiredEpisodes = new ArrayList<>();
    private Collection<EpisodeData> seenEpisodes = new ArrayList<>();

    private Collection<EpisodeData> unAcquiredEpisodes = new ArrayList<>();
    private Collection<EpisodeData> unseenEpisodes = new ArrayList<>();

    private boolean isOngoing; // TODO: status enum?

    public TvShowData() {
    }

    public TvShowData(Element element) {
        // TODO...
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

    public Collection<EpisodeData> getAcquiredEpisodes() {
        return acquiredEpisodes;
    }

    public Collection<EpisodeData> getSeenEpisodes() {
        return seenEpisodes;
    }

    public Collection<EpisodeData> getUnAcquiredEpisodes() {
        return unAcquiredEpisodes;
    }

    public Collection<EpisodeData> getUnseenEpisodes() {
        return unseenEpisodes;
    }

    public boolean isOngoing() {
        return isOngoing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TvShowData that = (TvShowData) o;
        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(name)
                .append(" (")
                .append(id)
                .append(")");

        sb.append("\nun-acquired: ");
        unAcquiredEpisodes.forEach(e -> sb.append(e).append("; "));
        sb.append("\nun-seen: ");
        unseenEpisodes.forEach(e -> sb.append(e).append("; "));
        sb.append("\nacquired: ");
        acquiredEpisodes.forEach(e -> sb.append(e).append("; "));
        sb.append("\nseen: ");
        seenEpisodes.forEach(e -> sb.append(e).append("; "));

        return sb.toString();
    }
}
