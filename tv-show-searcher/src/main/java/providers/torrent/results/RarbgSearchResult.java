package providers.torrent.results;

import data.SearchResult;
import data.ShowData;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RarbgSearchResult extends SearchResult {

    public RarbgSearchResult(Element source, String baseUrl) {
        super(source);
        downloadLink = baseUrl + downloadLink;
    }

    @Override
    protected void initialize(Element source) {
        Elements elements = source.getElementsByTag("td");

        String name = elements.get(1)
                .child(0)
                .text()
                .replaceAll("\\[.*\\]", "")
                .trim();

        ShowData showData = ShowData.fromFilename(name, " ");
        initialize(showData);

        seeds = Integer.parseInt(elements.get(4).text());
        peers = Integer.parseInt(elements.get(5).text());

        final String id = elements.get(1).child(0).attr("href").replaceAll("/torrent/", "");
        downloadLink = "/download.php?id=" + id + "&f=temp.torrent";
    }
}
