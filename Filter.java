import java.util.Map;
import java.util.List;

/**
 * Represents a filter object for filtering books
 */

public interface Filter {
    public boolean isInCriteria(Book pBook);
    public List<Book> filterResult(List<Book> pBooks);
    public Filter makeCopy();
}
