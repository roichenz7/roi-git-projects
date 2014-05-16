package preDL;

import config.PreDLConfigData;
import config.IPreDLConfigData;
import data.EpisodeData;
import data.SearchResult;
import enums.Quality;
import file.FileDownloader;
import file.FileType;
import providers.IProvider;
import providers.torrent.TorrentProviderFactory;
import service.MyEpisodesService;
import service.MyEpisodesServiceImpl;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class PreDL implements Runnable {

    private final String configFilename;
    private final String username;
    private final String password;

    private IProvider provider;

    private boolean isMarkAsAcquired;
    private boolean isMarkAsWatched;

    public PreDL(String configFilename, String username, String password) {
        this.configFilename = configFilename;
        this.username = username;
        this.password = password;
    }

    public PreDL withMarkAsAcquired() {
        isMarkAsAcquired = true;
        return this;
    }

    public PreDL withMarkAsWatched() {
        isMarkAsWatched = true;
        return this;
    }

    @Override
    public void run() {
        IPreDLConfigData config = new PreDLConfigData();
        if (!config.parse(configFilename)) {
            System.out.println("pre-dl: failed parsing configuration: " + configFilename);
            return;
        }

        final File downloadDir = new File(config.downloadDir());
        if (!downloadDir.exists()) {
            System.out.println("pre-dl: file does not exist: " + config.downloadDir());
            return;
        }
        if (!downloadDir.isDirectory()) {
            System.out.println("pre-dl: download dir argument is not a directory: " + config.downloadDir());
            return;
        }

        System.out.println(config);

        provider = TorrentProviderFactory.create(config.defaultProvider());
        provider.setAcceptedOrigins(config.acceptedOrigins());

        System.out.println("pre-dl: logging in to my episodes, username: " + username);
        final MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl(username, password);

        System.out.println("\npre-dl: getting list of episodes to acquire");
        List<EpisodeData> episodesToAcquire = myEpisodesService.getStatus()
                .stream()
                .filter(x -> !config.ignoredShows().contains(x))
                .flatMap(x -> x.getUnAcquiredEpisodes().stream())
                .filter(EpisodeData::isAired)
                .collect(Collectors.toList());

        if (episodesToAcquire.isEmpty()) {
            System.out.println("pre-dl: there are no un-acquired episodes");
            System.out.println("pre-dl: finished");
            return;
        }

        System.out.println("pre-dl: un-acquired episodes:");
        episodesToAcquire.forEach(System.out::println);

        System.out.println("\npre-dl: processing un-acquired episodes");
        episodesToAcquire.forEach(e -> {
            Quality quality = config.getTvShowQuality(e.getTvShowId());
            System.out.println("\npre-dl: searching for: " + e + " [" + quality + "]");

            try {
                List<SearchResult> results = provider.search(e.getSanitizedTvShowName(),
                        e.getSeason(),
                        e.getEpisode(),
                        quality);

                if (!results.isEmpty()) {
                    System.out.println("pre-dl: got " + results.size() + " results");

                    System.out.println("pre-dl: getting best result");
                    SearchResult result = provider.getBestResult(results);

                    System.out.println("pre-dl: downloading file: " + result);
                    String filename = downloadDir.getPath() + "/" + result.toString() + ".[" + provider.getName() + "]";
                    FileDownloader.downloadFile(result.getDownloadLink(), filename, FileType.TORRENT);
                    System.out.println("pre-dl: file downloaded: " + filename);

                    if (isMarkAsWatched) {
                        System.out.println("pre-dl: marking file as watched: " + result);
                        myEpisodesService.markAsWatched(e.getTvShowId(), e.getSeason(), e.getEpisode());
                    } else if (isMarkAsAcquired) {
                        System.out.println("pre-dl: marking file as acquired: " + result);
                        myEpisodesService.markAsAcquired(e.getTvShowId(), e.getSeason(), e.getEpisode());
                    }
                } else {
                    System.out.println("pre-dl: no results found");
                }
            } catch (Exception ex) {
                System.out.println("pre-dl: failed processing of: " + e + ". reason: " + ex);
            }
        });

        System.out.println("\npre-dl: finished");
    }
}
