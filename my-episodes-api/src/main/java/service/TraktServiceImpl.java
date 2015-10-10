package service;

import com.uwetrottmann.trakt.v2.TraktV2;
import com.uwetrottmann.trakt.v2.entities.CalendarShowEntry;
import com.uwetrottmann.trakt.v2.entities.Username;
import com.uwetrottmann.trakt.v2.enums.Extended;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import data.EpisodeData;
import data.TvShowData;
import exceptions.GetMyShowsException;
import org.joda.time.Duration;
import org.joda.time.Instant;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TraktServiceImpl implements MyEpisodesService {

    private static final int DAYS_TO_SCAN = 21;

    private final Username username;
    private final TraktV2 traktApi;

    public TraktServiceImpl(String username, String apiKey) {
        this.username = new Username(username);
        traktApi = new TraktV2();
        traktApi.setApiKey(apiKey);
    }

    @Override
    public Map<Integer, String> getMyShows() {
        try {
            return getShowCollection().myShows();
        } catch (OAuthUnauthorizedException e) {
            throw new GetMyShowsException(e);
        }
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
        final TvShowCollection collection;
        try {
            newEpisodes = getNewEpisodes();
            collection = getShowCollection();
        } catch (OAuthUnauthorizedException e) {
            throw new GetMyShowsException(e);
        }

        return newEpisodes.stream()
                .filter(collection::isShowPartOfCollection)
                .filter(collection::isMissing)
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

    private TvShowCollection getShowCollection() throws OAuthUnauthorizedException {
        return new TvShowCollection(traktApi.users().collectionShows(username, Extended.FULL));
    }

    private String calculateStartDate() {
        return Instant.now().minus(Duration.standardDays(DAYS_TO_SCAN)).toDateTime().toString("YYYY-MM-dd");
    }
}
