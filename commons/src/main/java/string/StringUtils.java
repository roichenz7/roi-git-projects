package string;

public final class StringUtils {

    private StringUtils() {}

    public static String removeSpecialChars(String source) {
        return source.replaceAll("-", " ").replaceAll("[^\\w\\s]", "");
    }
}
