import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestFilter {
    FilterByNull byNull;
    FilterByAuthor byAuthor;
    FilterByPriceRange byPriceRange;
    FilterByManyCriteria byManyCriteria;
    Book book1 = new BookStub("title1", "author1", 10.0f);
    Book book2 = new BookStub("title2", "author2", 20.0f);
    Book book3 = new BookStub("title3", "author3", 30.0f);
    Book book4 = new BookStub("title4", "author3", 40.0f);
    List<Book> books = new ArrayList<>();

    private static class BookStub implements Book{
        private final String aTitle;
        private final String aAuthor;
        private final float aPrice;

        BookStub(String pTitle, String pAuthor, float pPrice){
            aTitle = pTitle;
            aAuthor = pAuthor;
            aPrice = pPrice;
        }
        @Override
        public String getTitle() { return aTitle; }
        @Override
        public String getAuthor() { return aAuthor; }
        @Override
        public float getPrice() { return aPrice; }
    }

    @BeforeEach
    public void setup()
    {
        byNull = FilterByNull.getFilterByNull();
        byAuthor = new FilterByAuthor();
        byPriceRange = new FilterByPriceRange();
        byManyCriteria = new FilterByManyCriteria();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
    }

    // ================== FilterByNull ==================
    @Test
    public void testFilterByNullFilterResult()
    {
        assertEquals(books, byNull.filterResult(books));
    }
    @Test
    public void testFilterByNullIsInCriteria(){
        assertTrue(byNull.isInCriteria(book1));
    }

    // ================== FilterByAuthor ==================
    @Test
    public void testFilterByAuthorInCriteria()
    {
        byAuthor.addAuthorToFilter("author1");
        assertTrue(byAuthor.isInCriteria(book1));
    }
    @Test
    public void testFilterByAuthorNotInCriteria()
    {
        byAuthor.addAuthorToFilter("author2");
        assertFalse(byAuthor.isInCriteria(book1));
    }
    @Test
    public void testFilterByAuthorDefaultFilterResult()
    {
        assertEquals(books, byAuthor.filterResult(books));
    }
    @Test
    public void testFilterByAuthorFilterResult()
    {
        byAuthor.addAuthorToFilter("author1");
        List<Book> expectedValue = new ArrayList<>();
        expectedValue.add(book1);
        assertEquals(expectedValue, byAuthor.filterResult(books));
    }
    @Test
    public void testGetNamesInFilter()
    {
        byAuthor.addAuthorToFilter("author1");
        byAuthor.addAuthorToFilter("author2");
        List<String> expectedValue = new ArrayList<>();
        expectedValue.add("author1");
        expectedValue.add("author2");
        assertEquals(expectedValue, byAuthor.getAuthorsInFilter());
    }

    @Test
    public void testContainsAuthorTrue()
    {
        byAuthor.addAuthorToFilter("author1");
        assertTrue(byAuthor.isAuthorContain("author1"));
    }

    @Test
    public void testContainsAuthorFalse()
    {
        byAuthor.addAuthorToFilter("author1");
        assertFalse(byAuthor.isAuthorContain("author2"));
    }

    @Test
    public void testContainsAuthorCaseInsensitive()
    {
        byAuthor.addAuthorToFilter("author1");
        assertTrue(byAuthor.isAuthorContain("Author1"));
    }


    // ================== FilterByPriceRange ==================
    @Test
    public void testFilterByPriceDefaultMin()
    {
        assertEquals(0.0f, byPriceRange.getMin());
    }
    @Test
    public void testFilterByPriceDefaultMax()
    {
        assertEquals(Float.POSITIVE_INFINITY, byPriceRange.getMax());
    }
    @Test
    public void testFilterByPriceSetMin()
    {
        byPriceRange.setMin(5.0f);
        assertEquals(5.0f, byPriceRange.getMin());
    }
    @Test
    public void testFilterByPriceSetMax()
    {
        byPriceRange.setMax(10.0f);
        assertEquals(10.0f, byPriceRange.getMax());
    }
    @Test
    public void testFilterByPriceInCriteria()
    {
        assertTrue(byPriceRange.isInCriteria(book1));
    }
    @Test
    public void testFilterByPriceNotInCriteria() {
        byPriceRange.setMax(5.0f);
        assertFalse(byPriceRange.isInCriteria(book1));
    }
    @Test
    public void testFilterByPriceDefaultFilterResult()
    {
        assertEquals(books, byPriceRange.filterResult(books));
    }
    @Test
    public void testFilterByPriceFilterResult()
    {
        byPriceRange.setMax(20.0f);
        List<Book> expectedValue = new ArrayList<>();
        expectedValue.add(book1);
        expectedValue.add(book2);
        assertEquals(expectedValue, byPriceRange.filterResult(books));
    }

    // ================== FilterByManyCriteria ==================
    @Test
    public void testFilterWithOneFilter()
    {
        byAuthor.addAuthorToFilter("author1");
        byManyCriteria.addFilter(byAuthor);
        assertTrue(byManyCriteria.isInCriteria(book1));
    }
    @Test
    public void testFilterWithManyFilters()
    {
        byAuthor.addAuthorToFilter("author3");
        byPriceRange.setMax(35.0f);
        byManyCriteria.addFilter(byAuthor);
        byManyCriteria.addFilter(byPriceRange);
        assertFalse(byManyCriteria.isInCriteria(book4));
    }
    @Test
    public void testByManyCriteriaDefaultFilterResult()
    {
        assertEquals(books, byManyCriteria.filterResult(books));
    }
    @Test
    public void testOneFilterFilterResult()
    {
        byAuthor.addAuthorToFilter("author1");
        byManyCriteria.addFilter(byAuthor);
        List<Book> expectedValue = new ArrayList<>();
        expectedValue.add(book1);
        assertEquals(expectedValue, byManyCriteria.filterResult(books));
    }
    @Test
    public void testManyFilterFilterResult()
    {
        byAuthor.addAuthorToFilter("author3");
        byPriceRange.setMax(35.0f);
        byManyCriteria.addFilter(byAuthor);
        byManyCriteria.addFilter(byPriceRange);
        List<Book> expectedValue = new ArrayList<>();
        expectedValue.add(book3);
        assertEquals(expectedValue, byManyCriteria.filterResult(books));
    }
    @Test
    public void testAddSameCriteria()
    {
        byAuthor.addAuthorToFilter("author1");
        byManyCriteria.addFilter(byAuthor);
        byAuthor.removeAuthorFromFilter(0);
        byAuthor.addAuthorToFilter("author3");
        byManyCriteria.addFilter(byAuthor);
        List<Book> expectedValue = new ArrayList<>();
        expectedValue.add(book3);
        expectedValue.add(book4);
        assertEquals(expectedValue, byManyCriteria.filterResult(books));
    }

    @Test
    public void testRemoveFilter() throws IllegalAccessException {
        byManyCriteria.addFilter(byAuthor);
        byManyCriteria.removeFilter(byAuthor);
        Field privateField = null;
        try{
            privateField = FilterByManyCriteria.class.getDeclaredField("aFilterCriteria");
            privateField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        List<Filter> filter = (List<Filter>) privateField.get(byManyCriteria);
        assertFalse(filter.contains(byAuthor));
    }
}
