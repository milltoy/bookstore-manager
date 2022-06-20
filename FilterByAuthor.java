import java.util.List;
import java.util.ArrayList;

/**
 * Filter books by authors
 */

public class FilterByAuthor extends AbstractFilter{
    private final List<String> aAuthors;

    /**
     * Creates a new filter by authors
     */
    public FilterByAuthor(){
        aAuthors = new ArrayList<>();
    }

    public void addAuthorToFilter(String pAuthor){
        assert pAuthor != null;
        aAuthors.add(pAuthor);
    }

    public void removeAuthorFromFilter(int index){
        assert index >= 0 && index < aAuthors.size();
        aAuthors.remove(index);
    }

    /**
     * @return the copy of the list of authors
     */
    public List<String> getAuthorsInFilter(){
        return new ArrayList(aAuthors);
    }

    public boolean isAuthorContain(String pAuthor)
    {
        // case insensitive
        return aAuthors.stream().anyMatch(x -> x.equalsIgnoreCase(pAuthor));
    }

    /**
     * @param pBook
     * @return true if the author of pBook is contained in the list of authors
     *         false otherwise
     */
    @Override
    public boolean isInCriteria(Book pBook){
        if(aAuthors.isEmpty()){ return true; }
        else { return isAuthorContain(pBook.getAuthor()); }
    }

    @Override
    public Filter makeCopy() {
        FilterByAuthor filterCopy = new FilterByAuthor();
        for(String name: aAuthors){
            filterCopy.addAuthorToFilter(name);
        }
        return filterCopy;
    }

}
