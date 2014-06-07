package preDL.handlers;

import data.EpisodeData;
import data.SearchQuery;
import data.SearchResult;
import enums.Quality;
import providers.Provider;

import java.util.List;

public class EpisodeHandlerAdapter extends EpisodeHandlerBase {

    private Provider provider;

    public EpisodeHandlerAdapter(Provider provider) {
        this.provider = provider;
    }

    @Override
    protected boolean doHandle(EpisodeData episode, Quality quality, String downloadPath) {
        try {
            System.out.println(this + ": searching for: " + episode + " [" + quality + "]");
            SearchQuery query = new SearchQuery(episode.getSanitizedTvShowName(),
                    episode.getSeason(),
                    episode.getEpisode(),
                    quality);

            List<SearchResult> results = provider.search(query);
            if (results.isEmpty()) {
                System.out.println(this + ": no results found");
                return false;
            }

            System.out.println(this + ": got " + results.size() + " results");

            System.out.println(this + ": getting best result");
            SearchResult result = provider.getBestResult(results, query);

            System.out.println(this + ": downloading file: " + result);
            String filename = downloadPath + "/" + result.toString() + ".[" + provider.getName() + "]";
            provider.download(result, downloadPath);
            System.out.println(this + ": file downloaded: " + filename);
        } catch (Exception e) {
            System.out.println(this + ": failed processing of: " + episode + ". reason: " + e);
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "episode-handler-" + provider.getName();
    }
}
