package providers.srt;

import data.SearchQuery;
import data.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class SubtitleProvider implements SrtProvider {

    public SubtitleProvider(final String email, final String password) {
        login(email, password);
    }

    @Override
    public String getName() {
        return "Subtitle";
    }

    @Override
    public String getBaseUrl() {
        return "http://www.subtitle.co.il";
    }

    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        return new ArrayList<>(); // TODO
    }

    /**
     * Performs login to subtitle
     *
     * @param email email
     * @param password password
     */
    private void login(final String email, final String password) {
        // TODO
    }
}
