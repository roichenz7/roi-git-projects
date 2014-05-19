package providers.srt;

import data.SearchQuery;
import data.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class PodnapisiProvider implements SrtProvider {

    @Override
    public String getName() {
        return "Podnapisi";
    }

    @Override
    public String getBaseUrl() {
        return "http://www.podnapisi.net/";
    }

    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        return new ArrayList<>(); // TODO
    }
}
