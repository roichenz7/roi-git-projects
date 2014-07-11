package providers.torrent;

public class KatProvider extends KatProviderBase {

    @Override
    public String getName() {
        return "KAT";
    }

    @Override
    public String getBaseUrl() {
        return "http://kickass.to";
    }
}
