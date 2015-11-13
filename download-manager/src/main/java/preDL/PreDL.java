package preDL;

import config.IPreDLConfigData;
import config.PreDLConfigData;
import data.EpisodeData;
import enums.Quality;
import preDL.handlers.EpisodeHandler;
import preDL.handlers.EpisodeHandlerAdapter;
import preDL.handlers.EpisodeHandlerBase;
import providers.Provider;
import service.MyEpisodesService;
import service.MyEpisodesServiceImpl;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class PreDL implements Runnable {

    private final String configFilename;
    private final String username;
    private final String password;
    private EpisodeHandler episodeHandler;
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

        episodeHandler = new EpisodeHandlerBase() {
            @Override
            protected boolean doHandle(EpisodeData episode, Quality quality, String downloadPath) {
                return false;
            }
        };

        EpisodeHandler curr = episodeHandler;
        for (Provider provider : config.providers()) {
            provider.setAcceptedOrigins(config.acceptedOrigins());
            curr = ((EpisodeHandlerBase) curr).setNext(new EpisodeHandlerAdapter(provider));
        }

        System.out.println("pre-dl: logging in to my episodes, username: " + username);
        final MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl(username, password);

        System.out.println("\npre-dl: getting list of episodes to acquire");
        List<EpisodeData> episodesToAcquire = myEpisodesService.getStatus()
                .stream()
                .filter(x -> !config.ignoredShows().contains(x))
                .flatMap(x -> x.getUnAcquiredEpisodes().stream())
                .filter(e -> !e.isSpecial())
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
            try {
                Quality quality = config.getTvShowQuality(e.getTvShowId());
                System.out.println("\npre-dl: searching for: " + e + " [" + quality + "]");
                if (!episodeHandler.handle(e, quality, downloadDir.getPath()))
                    throw new RuntimeException("failed to handle episode: " + e);

                if (isMarkAsWatched) {
                    System.out.println("pre-dl: marking file as watched: " + e);
                    myEpisodesService.markAsWatched(e.getTvShowId(), e.getSeason(), e.getEpisode());
                } else if (isMarkAsAcquired) {
                    System.out.println("pre-dl: marking file as acquired: " + e);
                    myEpisodesService.markAsAcquired(e.getTvShowId(), e.getSeason(), e.getEpisode());
                }
            } catch (Exception ex) {
                System.out.println("pre-dl: failed processing of: " + e + ". reason: " + ex);
            }
        });

        System.out.println("\npre-dl: finished");
    }
}
