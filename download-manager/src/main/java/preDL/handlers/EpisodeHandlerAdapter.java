package preDL.handlers;

import data.EpisodeData;
import data.SearchResult;
import enums.Quality;
import file.FileDownloader;
import file.FileType;
import providers.IProvider;

import java.util.List;

public class EpisodeHandlerAdapter extends EpisodeHandlerBase {

    private IProvider provider;

    public EpisodeHandlerAdapter(IProvider provider) {
        this.provider = provider;
    }

    @Override
    protected boolean doHandle(EpisodeData episode, Quality quality, String downloadPath) {
        try {
            System.out.println(this + ": searching for: " + episode + " [" + quality + "]");
            List<SearchResult> results = provider.search(episode.getSanitizedTvShowName(),
                    episode.getSeason(),
                    episode.getEpisode(),
                    quality);
            if (results.isEmpty()) {
                System.out.println(this + ": no results found");
                return false;
            }

            System.out.println(this + ": got " + results.size() + " results");

            System.out.println(this + ": getting best result");
            SearchResult result = provider.getBestResult(results);

            System.out.println(this + ": downloading file: " + result);
            String filename = downloadPath + "/" + result.toString() + ".[" + provider.getName() + "]";
            FileDownloader.downloadFile(result.getDownloadLink(), filename, FileType.TORRENT);
            System.out.println(this + ": file downloaded: " + filename);
        } catch (Exception e) {
            System.out.println(this + ": failed processing of: " + e + ". reason: " + e);
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "episode-handler-" + provider.getName();
    }
}
