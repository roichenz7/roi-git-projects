package providers.srt;

import data.SearchQuery;
import data.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class OpenSubtitlesProvider implements SrtProvider {

    @Override
    public String getName() {
        return "OpenSubtitles";
    }

    @Override
    public String getBaseUrl() {
        return "http://www.opensubtitles.org";
    }

    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        return new ArrayList<>(); // TODO
    }
}
