import java.util.Objects;

/**
 * Provides the default method of getTitle, getAuthor for subclass
 */

public abstract class AbstractBook implements Book{
    private final String aTitle;
    private final String aAuthor;

    public AbstractBook(String pTitle, String pAuthor){
        assert pTitle != null && pAuthor != null;
        aTitle = pTitle;
        aAuthor = pAuthor;
    }

    @Override
    public String getTitle() {
        return aTitle;
    }

    @Override
    public String getAuthor() {
        return aAuthor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBook book = (AbstractBook) o;
        return aTitle.equalsIgnoreCase(book.aTitle) && aAuthor.equalsIgnoreCase(book.aAuthor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aTitle, aAuthor);
    }
}
