public class FilterByNull extends AbstractFilter{
    private final static FilterByNull INSTANCE = new FilterByNull();

    private FilterByNull(){}

    public static FilterByNull getFilterByNull(){ return INSTANCE; }

    @Override
    public boolean isInCriteria(Book pBook) {
        return true;
    }

    @Override
    public Filter makeCopy() {
        return INSTANCE;
    }
}
