import java.util.List;
import java.util.ArrayList;

/**
 * Filter books by many criteria
 */

public class FilterByManyCriteria extends AbstractFilter{
    public final List<Filter> aFilterCriteria;

    /**
     * Creates a new filter by many criteria
     */
    public FilterByManyCriteria(){
        aFilterCriteria = new ArrayList<>();
    }

    /**
     * Each filter has to be of different criteria
     * If more than one filter of the same criteria is added, the old one will be
     * replaced by the new one.
     * @param pFilter
     * @pre pFilter != null && !(pFilter instanceof FilterByManyCriteria)
     */
    public void addFilter(Filter pFilter){
        assert pFilter != null && !(pFilter instanceof FilterByManyCriteria);
        //  not allowed to have two filters with the same criteria
        int index = -1;
        for(int i = 0; i < aFilterCriteria.size(); i++){
            if(aFilterCriteria.get(i).getClass().equals(pFilter.getClass())){
                index = i;
                break;
            }
        }
        if(index != -1){
            aFilterCriteria.remove(index);
        }
        aFilterCriteria.add(pFilter.makeCopy());
    }

    /**
     * @param pFilter filter that user wants to remove
     * @pre pFilter != null
     */
    public void removeFilter(Filter pFilter){
        assert pFilter != null;
        aFilterCriteria.remove(pFilter);
    }

    /**
     * @param pBook
     * @return true if pBook satisfy all the filters in the list
     *         false otherwise
     */
    @Override
    public boolean isInCriteria(Book pBook) {
        for(Filter filter: aFilterCriteria){
            if(!filter.isInCriteria(pBook)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Filter makeCopy() {
        FilterByManyCriteria filterCopy = new FilterByManyCriteria();
        for(Filter filter: aFilterCriteria){
            filterCopy.addFilter(filter.makeCopy());
        }
        return filterCopy;
    }
}
