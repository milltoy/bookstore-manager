import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestBookStore {
    Book book1 = new BookStub();
    Book book2 = new BookStub();
    List listOfBooks = new ArrayList<>();
    Bookstore bookstore;
    FilterStub filterStub = FilterStub.getFilterStub();

    private static class BookStub implements Book{
        BookStub(){}
        @Override
        public String getTitle() {
            return null;
        }
        @Override
        public String getAuthor() {
            return null;
        }
        @Override
        public float getPrice() {
            return 0;
        }
    }

    private static class FilterStub implements Filter{
        private static FilterStub INSTANCE = null;

        private FilterStub(){}
        public static FilterStub getFilterStub()
        {
            if(INSTANCE == null){
                INSTANCE = new FilterStub();
            }
            return INSTANCE;
        }
        @Override
        public boolean isInCriteria(Book pBook) {
            return true;
        }

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
        @Override
        public Filter makeCopy() {
            return null;
        }
    }

    Field getPrivateField(Class className, String fieldName){
        Field privateField = null;
        try{
            privateField = className.getDeclaredField(fieldName);
            privateField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return privateField;
    }

    Map<Book, Integer> getBooksOfBookstore(){
        Map<Book, Integer> books = null;
        try{
            Field field = getPrivateField(Bookstore.class, "aBookSale");
            books = (Map<Book, Integer>) field.get(bookstore);
            return books;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @BeforeEach
    public void setup()
    {
        Bookstore.createBookstore("Bookstore");
        bookstore = Bookstore.getBookstore();
    }

    @AfterEach
    public void tearDown()
    {
        listOfBooks.clear();
        Map<Book, Integer> books = getBooksOfBookstore();
        books.clear();
    }

    @Test
    public void testGetBookstoreName()
    {
        assertEquals("Bookstore", bookstore.getBookstoreName());
    }

    @Test
    public void testAddBook()
    {
        bookstore.addBookForSale(book1, 5);
        Map<Book, Integer> books = getBooksOfBookstore();
        assertTrue(books.containsKey(book1) && books.get(book1) == 5);
    }
    @Test
    public void testAddSameBook()
    {
        bookstore.addBookForSale(book1, 5);
        bookstore.addBookForSale(book1, 2);
        Map<Book, Integer> books = getBooksOfBookstore();
        assertEquals(7, books.get(book1));
    }
    @Test
    public void testRemoveBookLessThanAvailable()
    {
        bookstore.addBookForSale(book1, 5);
        bookstore.removeBookForSale(book1, 3);
        Map<Book, Integer> books = getBooksOfBookstore();
        assertEquals(2, books.get(book1));
    }
    @Test
    public void testRemoveBookEqualToAvailable()
    {
        bookstore.addBookForSale(book1, 5);
        bookstore.removeBookForSale(book1, 5);
        Map<Book, Integer> books = getBooksOfBookstore();
        assertFalse(books.containsKey(book1));
    }
    @Test
    public void testRemoveBookMoreThanAvailable()
    {
        bookstore.addBookForSale(book1, 5);
        bookstore.removeBookForSale(book1, 10);
        Map<Book, Integer> books = getBooksOfBookstore();
        assertFalse(books.containsKey(book1));
    }

    @Test
    public void testBookFilter()
    {
        bookstore.addBookForSale(book1, 5);
        List<Book> filterResult = bookstore.getBookFilterResult(filterStub);
        listOfBooks.add(book1);
        assertEquals(listOfBooks, filterResult);
    }
}
