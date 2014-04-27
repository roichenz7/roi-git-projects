import data.ShowData;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ShowDataTest {

    @Test
    public void test1() {
        ShowData showData = ShowData.fromFilename("The.Big.Bang.Theory.S07E20.720p.HDTV.X264-DIMENSION");

        assertThat(showData.isEmpty(), is(false));
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

        assertThat(showData.isEmpty(), is(false));
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

        assertThat(showData.isEmpty(), is(false));
        assertThat(showData.getTitle(), is("House of Cards 2013"));
        assertThat(showData.getSeason(), is("Season 2"));
        assertThat(showData.getSeasonNumber(), is(2));
        assertThat(showData.getEpisodeNumber(), is(12));
        assertThat(showData.getQuality(), is("1080p"));
        assertThat(showData.getOrigin(), is("NTb"));
        assertThat(showData.isProper(), is(true));
    }

    @Test
    public void test4() {
        ShowData showData = ShowData.fromFilename("30.rock.s03e01.xvid-lol");

        assertThat(showData.isEmpty(), is(false));
        assertThat(showData.getTitle(), is("30 rock"));
        assertThat(showData.getSeason(), is("Season 3"));
        assertThat(showData.getSeasonNumber(), is(3));
        assertThat(showData.getEpisodeNumber(), is(1));
        assertThat(showData.getQuality(), is(""));
        assertThat(showData.getOrigin(), is("lol"));
        assertThat(showData.isProper(), is(false));
    }

    @Test
    public void test5() {
        ShowData showData = ShowData.fromFilename("game.of.thrones.s01e10.720p.proper.xvid-fov");

        assertThat(showData.isEmpty(), is(false));
        assertThat(showData.getTitle(), is("game of thrones"));
        assertThat(showData.getSeason(), is("Season 1"));
        assertThat(showData.getSeasonNumber(), is(1));
        assertThat(showData.getEpisodeNumber(), is(10));
        assertThat(showData.getQuality(), is("720p"));
        assertThat(showData.getOrigin(), is("fov"));
        assertThat(showData.isProper(), is(true));
    }

    @Test
    public void test6() {
        ShowData showData = ShowData.fromFilename("Arrow.S14xE15.Xvid");

        assertThat(showData.isEmpty(), is(false));
        assertThat(showData.getTitle(), is("Arrow"));
        assertThat(showData.getSeason(), is("Season 14"));
        assertThat(showData.getSeasonNumber(), is(14));
        assertThat(showData.getEpisodeNumber(), is(15));
        assertThat(showData.getQuality(), is(""));
        assertThat(showData.getOrigin(), is(""));
        assertThat(showData.isProper(), is(false));
    }

    @Test
    public void test7() {
        ShowData showData = ShowData.fromFilename("Pooki Shmooki - s11xe12.720p");

        assertThat(showData.isEmpty(), is(false));
        assertThat(showData.getTitle(), is("Pooki Shmooki"));
        assertThat(showData.getSeason(), is("Season 11"));
        assertThat(showData.getSeasonNumber(), is(11));
        assertThat(showData.getEpisodeNumber(), is(12));
        assertThat(showData.getQuality(), is("720p"));
        assertThat(showData.getOrigin(), is(""));
        assertThat(showData.isProper(), is(false));
    }

    @Test
    public void test8() {
        ShowData showData = ShowData.fromFilename("Quak Quak - 04x17.1080p");

        assertThat(showData.isEmpty(), is(false));
        assertThat(showData.getTitle(), is("Quak Quak"));
        assertThat(showData.getSeason(), is("Season 4"));
        assertThat(showData.getSeasonNumber(), is(4));
        assertThat(showData.getEpisodeNumber(), is(17));
        assertThat(showData.getQuality(), is("1080p"));
        assertThat(showData.getOrigin(), is(""));
        assertThat(showData.isProper(), is(false));
    }
}
