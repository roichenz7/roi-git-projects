package data;

import org.w3c.dom.Node;
import string.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeSet;

public class TvShowData {

    private int id;
    private String name;

    private Collection<EpisodeData> unAcquiredEpisodes = new TreeSet<>();
    private Collection<EpisodeData> unseenEpisodes = new TreeSet<>();

    public TvShowData(int id, String name) {
        this.id = id;
        this.name = StringUtils.removeSpecialChars(name);
    }

    public TvShowData(Map.Entry<Integer, String> entry) {
        this(entry.getKey(), entry.getValue());
    }

    public TvShowData(Node node) {
        this(Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue()),
                node.getAttributes().getNamedItem("name").getNodeValue());
    }

    /**
     * @return tv show id
     */
    public int getId() {
        return id;
    }

    /**
     * @return tv show name
     */
    public String getName() {
        return name;
    }

    /**
     * @return un-acquired episodes
     */
    public Collection<EpisodeData> getUnAcquiredEpisodes() {
        return Collections.unmodifiableCollection(unAcquiredEpisodes);
    }

    /**
     * @return un-seen episodes
     */
    public Collection<EpisodeData> getUnseenEpisodes() {
        return Collections.unmodifiableCollection(unseenEpisodes);
    }

    /**
     * Adds given episode to un-acquired episodes collection
     *
     * @param episodeData episode to add
     */
    public void addUnAcquiredEpisode(EpisodeData episodeData) {
        unAcquiredEpisodes.add(episodeData);
    }

    /**
     * Adds given episode to un-seen episodes collection
     *
     * @param episodeData episode to add
     */
    public void addUnSeenEpisode(EpisodeData episodeData) {
        unseenEpisodes.add(episodeData);
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

        return sb.toString();
    }
}
