/**
 * Filter books by price range
 */

public class FilterByPriceRange extends AbstractFilter{
    private float aMin;
    private float aMax;

    /**
     * create a new filter by price range
     */
    public FilterByPriceRange(){
        aMin = 0.0f;
        aMax = Float.POSITIVE_INFINITY;
    }

    /**
     * set the minimum price
     * @param pMin the minimum price that user is interested in
     * @pre pMin > 0.0 && pMin < aMax
     */
    public void setMin(float pMin){
        if(pMin > 0.0 && pMin < aMax){
            aMin = pMin;
        }
    }

    /**
     * set the maximum price
     * @param pMax the maximum price that user is interested in
     * @pre pMax > 0.0 && aMin < pMax
     */
    public void setMax(float pMax) {
        if(pMax > 0.0 && aMin < pMax){
            aMax = pMax;
        }
    }

    /**
     * @return the maximum price
     */
    public float getMax() { return aMax; }

    /**
     * @return the minimum price
     */
    public float getMin(){ return aMin; }

    /**
     * @param pBook
     * @return true if the price of pBook is within the range
     *         false otherwise
     */
    @Override
    public boolean isInCriteria(Book pBook){
        return aMin <= pBook.getPrice() && aMax >= pBook.getPrice();
    }

    @Override
    public Filter makeCopy() {
        FilterByPriceRange filterCopy = new FilterByPriceRange();
        filterCopy.setMin(aMin);
        filterCopy.setMax(aMax);
        return filterCopy;
    }
}
