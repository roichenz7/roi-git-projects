package providers.torrent.results;

import data.SearchResult;
import data.ShowData;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class KatSearchResult extends SearchResult {

    public KatSearchResult(Element source) {
        super(source);
    }

    @Override
    protected void initialize(Element source) {
        Elements elements = source.getElementsByTag("td");

        Element element = elements.get(0).select("[data-sc-params]").get(0);
        String name = element.attr("data-sc-params")
                .replaceAll("\\{ 'name': '", "")
                .replaceAll("', 'magnet':.*", "")
                .replaceAll("%20", "\\.")
                .replaceAll("%5B.*", "");

        ShowData showData = ShowData.fromFilename(name);
        initialize(showData);

        seeds = Integer.parseInt(elements.get(4).text());
        peers = Integer.parseInt(elements.get(5).text());

        element = elements.get(0).select("div a[title=Download torrent file]").get(0);
        String[] array = element.attr("href").split("\\?");
        downloadLink = array[0].replaceAll("\\.torrent", "/temp\\.torrent");

        if (downloadLink.startsWith("//")) {
            downloadLink = "http:" + downloadLink;
        }
    }
}