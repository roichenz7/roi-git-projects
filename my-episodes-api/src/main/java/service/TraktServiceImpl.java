package service;

import com.uwetrottmann.trakt.v2.TraktV2;
import com.uwetrottmann.trakt.v2.entities.BaseShow;
import com.uwetrottmann.trakt.v2.entities.CalendarShowEntry;
import com.uwetrottmann.trakt.v2.entities.Username;
import com.uwetrottmann.trakt.v2.enums.Extended;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import data.EpisodeData;
import data.TvShowData;
import exceptions.GetMyShowsException;
import org.joda.time.Duration;
import org.joda.time.Instant;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TraktServiceImpl implements MyEpisodesService {

    private static final int DAYS_TO_SCAN = 14;

    private final Username username;
    private final TraktV2 traktApi;

    public TraktServiceImpl(String username, String apiKey) {
        this.username = new Username(username);
        traktApi = new TraktV2();
        traktApi.setApiKey(apiKey);
    }

    @Override
    public Map<Integer, String> getMyShows() {
        final List<BaseShow> shows;
        try {
            shows = getCollectedShows();
        } catch (OAuthUnauthorizedException e) {
            throw new GetMyShowsException(e);
        }

        return shows
                .stream()
                .collect(Collectors.toMap(s -> s.show.ids.trakt, s -> s.show.title));
    }

    @Override
    public List<TvShowData> getStatus() {
        Map<Integer, TvShowData> tvShows = getMyShows()
                .entrySet()
                .stream()
                .map(TvShowData::new)
                .collect(Collectors.toMap(TvShowData::getId,
                        Function.<TvShowData>identity()));

        getUnAcquiredEpisodes().forEach(e ->
                tvShows.get(e.getTvShowId())
                        .addUnAcquiredEpisode(e));

        getUnSeenEpisodes().forEach(e ->
                tvShows.get(e.getTvShowId())
                        .addUnSeenEpisode(e));

        return tvShows.values()
                .stream()
                .sorted((l, r) -> l.getName().compareTo(r.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<EpisodeData> getUnAcquiredEpisodes() {
        final List<CalendarShowEntry> newEpisodes;
        final List<BaseShow> collectedShows;
        try {
            newEpisodes = getNewEpisodes();
            collectedShows = getCollectedShows();
        } catch (OAuthUnauthorizedException e) {
            throw new GetMyShowsException(e);
        }

        return newEpisodes.stream()
                .filter(e -> isShowPartOfCollection(collectedShows, e))
                        // .filter(e -> !isShowHidden(collectedShows, e)) // TODO: fix
                .filter(e -> !isEpisodeCollected(collectedShows, e))
                .map(EpisodeData::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<EpisodeData> getUnSeenEpisodes() {
        return Collections.emptyList();
    }

    @Override
    public void markAsAcquired(int showId, int season, int episode) {
        // Empty
    }

    @Override
    public void markAsAcquired(String showName, int season, int episode) {
        // Empty
    }

    @Override
    public void markAsWatched(int showId, int season, int episode) {
        // Empty
    }

    @Override
    public void markAsWatched(String showName, int season, int episode) {
        // Empty
    }

    private List<CalendarShowEntry> getNewEpisodes() {
        return traktApi.calendars().shows(calculateStartDate(), DAYS_TO_SCAN);
    }

    private List<BaseShow> getCollectedShows() throws OAuthUnauthorizedException {
        return traktApi.users().collectionShows(username, Extended.DEFAULT_MIN);
    }

    private boolean isShowPartOfCollection(Collection<BaseShow> collectedShows, CalendarShowEntry showEntry) {
        return collectedShows.stream()
                .anyMatch(s -> Objects.equals(s.show.ids.trakt, showEntry.show.ids.trakt));
    }

    private boolean isShowHidden(Collection<BaseShow> collectedShows, CalendarShowEntry showEntry) {
        return collectedShows.stream()
                .filter(s -> Objects.equals(s.show.ids.trakt, showEntry.show.ids.trakt))
                .filter(s -> s.hidden_seasons != null)
                .flatMap(s -> s.hidden_seasons.stream())
                .anyMatch(s -> Objects.equals(s.number, showEntry.episode.season));
    }

    private boolean isEpisodeCollected(Collection<BaseShow> collectedShows, CalendarShowEntry showEntry) {
        return collectedShows.stream()
                .filter(s -> Objects.equals(s.show.ids.trakt, showEntry.show.ids.trakt))
                .flatMap(s -> s.seasons.stream())
                .filter(s -> Objects.equals(s.number, showEntry.episode.season))
                .flatMap(s -> s.episodes.stream())
                .anyMatch(e -> Objects.equals(e.number, showEntry.episode.number) && e.collected_at != null);
    }

    private String calculateStartDate() {
        return Instant.now().minus(Duration.standardDays(DAYS_TO_SCAN)).toDateTime().toString("YYYY-MM-dd");
    }
}
