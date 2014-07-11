package providers.torrent;

public class KatProxyProvider extends KatProviderBase {

    @Override
    public String getName() {
        return "KATProxy";
    }

    @Override
    public String getBaseUrl() {
        return "http://kickass.to";
    }
}
