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
        String[] array = temp.replaceAll("http://", "").split("=");
        String filename = array[2].replaceAll("\\+%5BPublicHD%5D\\.torrent", "");

        ShowData showData = ShowData.fromFilename(filename);
        initialize(showData);

        seeds = Integer.parseInt(elements.get(4).text());
        peers = Integer.parseInt(elements.get(5).text());

        String hash = array[1].replaceAll("&f", "").toUpperCase();
        downloadLink = "http://phd.re/download.php?id=" + hash + "&f=a.torrent";
    }
}