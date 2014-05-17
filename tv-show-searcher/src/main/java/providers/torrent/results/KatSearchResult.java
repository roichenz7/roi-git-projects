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

        Element element = elements.get(0).select("div a[href=#]").get(0);
        String name = element.attr("onclick")
                .replaceAll(".*, \\{ 'name': '", "")
                .replaceAll("', 'magnet':.*", "")
                .replaceAll("%20", "\\.");

        ShowData showData = ShowData.fromFilename(name);
        initialize(showData);

        seeds = Integer.parseInt(elements.get(4).text());
        peers = Integer.parseInt(elements.get(5).text());

        element = elements.get(0).select("div a[title=Download torrent file]").get(0);
        String[] array = element.attr("href").split("\\?");
        downloadLink = array[0];
    }
}