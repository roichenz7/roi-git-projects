package data;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Collection;

public class TvShowData {

    private int id;
    private String name;

    private Collection<EpisodeData> acquiredEpisodes = new ArrayList<>();
    private Collection<EpisodeData> seenEpisodes = new ArrayList<>();

    private Collection<EpisodeData> unacquiredEpisodes = new ArrayList<>();
    private Collection<EpisodeData> unseenEpisodes = new ArrayList<>();

    private boolean isOngoing; // TODO: status enum?

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

    public Collection<EpisodeData> getAcquiredEpisodes() {
        return acquiredEpisodes;
    }

    public Collection<EpisodeData> getSeenEpisodes() {
        return seenEpisodes;
    }

    public Collection<EpisodeData> getUnacquiredEpisodes() {
        return unacquiredEpisodes;
    }

    public Collection<EpisodeData> getUnseenEpisodes() {
        return unseenEpisodes;
    }

    public boolean isOngoing() {
        return isOngoing;
    }

    @Override
    public String toString() {
        return "TvShowData: " + id + " <-> " + name;
    }
}
