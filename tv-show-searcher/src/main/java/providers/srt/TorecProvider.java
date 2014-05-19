package providers.srt;

import data.SearchQuery;
import data.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class TorecProvider implements SrtProvider {

    @Override
    public String getName() {
        return "Torec";
    }

    @Override
    public String getBaseUrl() {
        return "http://www.torec.net/";
    }

    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        return new ArrayList<>(); // TODO
    }
}
