package service;

import com.uwetrottmann.trakt.v2.TraktV2;
import com.uwetrottmann.trakt.v2.entities.BaseShow;
import com.uwetrottmann.trakt.v2.entities.Username;
import com.uwetrottmann.trakt.v2.enums.Extended;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import data.EpisodeData;
import data.TvShowData;
import exceptions.GetMyShowsException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TraktServiceImpl implements MyEpisodesService {

    private final Username username;
    private final TraktV2 impl;

    public TraktServiceImpl(String username, String apiKey) {
        this.username = new Username(username);
        impl = new TraktV2();
        impl.setApiKey(apiKey);
    }

    @Override
    public Map<Integer, String> getMyShows() {
        final List<BaseShow> shows;
        try {
            shows = impl.users().collectionShows(username, Extended.DEFAULT_MIN);
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
        // TODO: Use calendars + users.collectionShows
        return Collections.emptyList();
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
}
