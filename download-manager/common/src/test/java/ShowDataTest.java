import data.ShowData;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ShowDataTest {

    @Test
    public void test1() {
        ShowData showData = ShowData.fromFilename("The.Big.Bang.Theory.S07E20.720p.HDTV.X264-DIMENSION");

        assertThat(showData.getTitle(), is("The Big Bang Theory"));
        assertThat(showData.getSeason(), is("Season 7"));
        assertThat(showData.getSeasonNumber(), is(7));
        assertThat(showData.getEpisodeNumber(), is(20));
        assertThat(showData.getQuality(), is("720p"));
        assertThat(showData.getOrigin(), is("DIMENSION"));
        assertThat(showData.isProper(), is(false));
    }

    @Test
    public void test2() {
        ShowData showData = ShowData.fromFilename("The.Good.Wife.S05E09.HDTV.x264-LOL");

        assertThat(showData.getTitle(), is("The Good Wife"));
        assertThat(showData.getSeason(), is("Season 5"));
        assertThat(showData.getSeasonNumber(), is(5));
        assertThat(showData.getEpisodeNumber(), is(9));
        assertThat(showData.getQuality(), is(""));
        assertThat(showData.getOrigin(), is("LOL"));
        assertThat(showData.isProper(), is(false));
    }

    @Test
    public void test3() {
        ShowData showData = ShowData.fromFilename("House.of.Cards.2013.S02E12.PROPER.1080p.x264-NTb");

        assertThat(showData.getTitle(), is("House of Cards 2013"));
        assertThat(showData.getSeason(), is("Season 2"));
        assertThat(showData.getSeasonNumber(), is(2));
        assertThat(showData.getEpisodeNumber(), is(12));
        assertThat(showData.getQuality(), is("1080p"));
        assertThat(showData.getOrigin(), is("NTb"));
        assertThat(showData.isProper(), is(true));
    }
}
