import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides the default implementation of filterResult method for subclass
 */
public abstract class AbstractFilter implements Filter{
    @Override
    public List<Book> filterResult(List<Book> pBooks) {
        List<Book> bookFilter = new ArrayList<>();
        for(Book book: pBooks){
            if(isInCriteria(book)){
                bookFilter.add(book);
            }
        }
        return bookFilter;
    }
}
