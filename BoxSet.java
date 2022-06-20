import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents the price and the list of books of the box set
 */

public class BoxSet extends AbstractBook {
    // Flyweight Store
    private static final List<BoxSet> boxSets = new ArrayList();

    private final List<Book> aBooks = new ArrayList<>();

    /**
     * Creates a new Box Set when it is not present in the Flyweight store
     * @param pTitle the title of the box set
     * @param pAuthor the author of the book(maybe various)
     * @param pBooks the list of books that the box set has
     */
    private BoxSet(String pTitle, String pAuthor, List<Book> pBooks) {
        super(pTitle,pAuthor);
        for (Book book : pBooks) {
            if(book instanceof BoxSet){
                throw new IllegalArgumentException();
            } else {
                aBooks.add(book);
            }
        }
    }

    /**
     * User can create or get a box set that they want by calling this method
     * @param pTitle the title of the box set
     * @param pBooks the list of books that the box set has
     * @return BoxSet object
     */
    public static BoxSet createBoxSet(String pTitle, List<Book> pBooks){
        assert pTitle != null && pBooks != null && !pBooks.isEmpty();
        // already in flyweight store
        for(BoxSet boxSet: boxSets){
            // consider to be equal if both of them have the same books(order does not matter)
            if(hasSameBooks(boxSet.getBooks(), pBooks)){
                return boxSet;
            }
        }
        // create the new instance and store in flyweight store
        BoxSet b = new BoxSet(pTitle, findAuthor(pBooks), pBooks);
        boxSets.add(b);
        return b;
    }

    /**
     * @return the price of the box set
     */
    @Override
    public float getPrice() {
        float price = 0.0f;
        for(Book book: aBooks){
            price += book.getPrice();
        }
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BoxSet boxSet = (BoxSet) o;
        return hasSameBooks(getBooks(), boxSet.getBooks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), aBooks);
    }

    @Override
    public String toString() {
        return "BoxSet{" +
                "Title=" + getTitle() +
                ", Author=" + getAuthor() +
                ", Books=" + aBooks +
                '}';
    }

    private static boolean hasSameBooks(List<Book> books1, List<Book> books2){
        assert books1 != null && books2 != null;
        if(books1.size() != books2.size()){
            return false;
        } else {
            return books1.containsAll(books2);
        }
    }

    public List<Book> getBooks(){
        return makeCopy();
    }

    private static String findAuthor(List<Book> pBooks){
        assert pBooks != null && !pBooks.isEmpty();
        String author = pBooks.get(0).getAuthor();
        for(Book book : pBooks){
            if(author != book.getAuthor()){
                return "Various";
            }
        }
        return author;
    }

    private List<Book> makeCopy(){
        List<Book> bookCopy = new ArrayList<>();
        for(Book book: aBooks){
            bookCopy.add(book);
        }
        return bookCopy;
    }

}
