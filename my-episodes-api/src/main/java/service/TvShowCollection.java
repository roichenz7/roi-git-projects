package service;

import com.uwetrottmann.trakt.v2.entities.BaseShow;
import com.uwetrottmann.trakt.v2.entities.CalendarShowEntry;
import exceptions.GetMyShowsException;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class TvShowCollection {

    private final Collection<BaseShow> shows;

    TvShowCollection(Collection<BaseShow> shows) {
        this.shows = shows;
    }

    Map<Integer, String> myShows() {
        return shows.stream()
                .collect(Collectors.toMap(s -> s.show.ids.trakt, s -> s.show.title));
    }

    BaseShow showById(int traktId) {
        return shows.stream()
                .filter(s -> s.show.ids.trakt == traktId)
                .findAny()
                .orElseThrow(() -> new GetMyShowsException("Cannot find show with id: " + traktId));
    }

    boolean isShowPartOfCollection(CalendarShowEntry entry) {
        return shows.stream().anyMatch(s -> Objects.equals(s.show.ids.trakt, entry.show.ids.trakt));
    }

    boolean contains(CalendarShowEntry entry) {
        return shows.stream()
                .filter(s -> Objects.equals(s.show.ids.trakt, entry.show.ids.trakt))
                .flatMap(s -> s.seasons.stream())
                .filter(s -> Objects.equals(s.number, entry.episode.season))
                .flatMap(s -> s.episodes.stream())
                .anyMatch(e -> Objects.equals(e.number, entry.episode.number) && e.collected_at != null);
    }

    boolean isMissing(CalendarShowEntry entry) {
        return !contains(entry);
    }
}
