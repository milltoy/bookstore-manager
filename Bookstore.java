import java.util.*;

/**
 * Represents a single bookstore
 */
public class Bookstore {
    private final Map<Book, Integer> aBookSale = new HashMap<>(); // key: book, value: amount
    private final String aBookstoreName;
    private static Bookstore INSTANCE = null;

    /**
     * @param pBookstoreName name of the bookstore
     */
    private Bookstore(String pBookstoreName){
        assert pBookstoreName != null;
        aBookstoreName = pBookstoreName;
    }

    /**
     * Create an instance of Bookstore if it does not exist yet
     * @param pBookstoreName name of the bookstore
     * @pre pBookStoreName != null
     */
    public static void createBookstore(String pBookstoreName){
        assert pBookstoreName != null;
        if(INSTANCE == null){
            INSTANCE = new Bookstore(pBookstoreName);
        }
    }

    /**
     * @return a single instance of Bookstore
     */
    public static Bookstore getBookstore() { return INSTANCE; }

    /**
     * @param pBook book to be added to the bookstore
     * @param pAmount amount of book
     * @pre pBook != null && pAmount > 0
     */
    public void addBookForSale(Book pBook, int pAmount){
        assert pBook != null && pAmount > 0;

        // update amount if novel is already present
        // else, add new book to store
        if((aBookSale.computeIfPresent(pBook, (k, v) -> v + pAmount)) == null){
            aBookSale.put(pBook, pAmount);
        }
    }

    /**
     * @param pBook book object to be removed from the bookstore
     * @param pAmount amount of book to be removed
     * @pre pBook != null && pAmount > 0
     */
    public void removeBookForSale(Book pBook, int pAmount){
        // sell the book out, do nothing if book does not exist
        assert pBook != null && pAmount > 0;
        Integer amount = aBookSale.get(pBook);
        if(amount != null){
            if(pAmount >= amount){
                // no book left
                aBookSale.remove(pBook);
            } else {
                aBookSale.put(pBook, amount - pAmount);
            }
        }
    }

    public String getBookstoreName() { return aBookstoreName; }

    /**
     * Filter the books according to the given filter
     * @param pBookFilter
     * @return list of books after the given filter is applied to
     */
    public List<Book> getBookFilterResult(Filter pBookFilter){
        assert pBookFilter != null;
        return pBookFilter.filterResult(getOnlyBooks());
    }

    private List<Book> getOnlyBooks(){
        List books = new ArrayList();
        for(Map.Entry mapElement: aBookSale.entrySet()){
            Book key = (Book) mapElement.getKey();
            books.add(key);
        }
        return books;
    }

}
