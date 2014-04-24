package exceptions;

public class SearchException extends RuntimeException {

    public SearchException(String query) {
        super("Search failed, query: " + query);
    }

    public SearchException(String query, String message) {
        super("Search failed, query: " + query + ". " + message);
    }

    public SearchException(String query, Throwable e) {
        super("Search failed, query: " + query, e);
    }
}
