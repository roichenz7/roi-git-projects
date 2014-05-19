package providers.srt;

import data.SearchQuery;
import data.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class SubsCenterProvider implements SrtProvider {

    @Override
    public String getName() {
        return "SubsCenter";
    }

    @Override
    public String getBaseUrl() {
        return "http://subscenter.cinemast.com";
    }

    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        return new ArrayList<>(); // TODO
    }
}
