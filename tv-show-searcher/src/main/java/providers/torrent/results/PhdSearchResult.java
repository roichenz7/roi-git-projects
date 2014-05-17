package providers.torrent.results;

import data.SearchResult;
import data.ShowData;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PhdSearchResult extends SearchResult {

    public PhdSearchResult(Element source) {
        super(source);
    }

    @Override
    protected void initialize(Element source) {
        Elements elements = source.getElementsByTag("td");

        String temp = elements.get(2).getElementsByAttribute("href").get(2).attr("href");
        String[] array = temp.replaceAll("http://", "").split("/");
        String filename = array[3].replaceAll("\\+%5BPublicHD%5D\\.torrent", "");

        ShowData showData = ShowData.fromFilename(filename);
        initialize(showData);

        seeds = Integer.parseInt(elements.get(4).text());
        peers = Integer.parseInt(elements.get(5).text());

        if (temp.contains("istoretor")) {
            String hash = array[2].toUpperCase();
            downloadLink = "http://istoretor.com/fdown.php?hash=" + hash;
        }
    }
}