import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.*;

public class TestBook {
    Novel harryPotter1;
    Novel harryPotter2;
    Novel hunger;
    BoxSet boxSet;
    ArrayList<Book> books = new ArrayList();

    private void resetFlyweightStore(Class className, String fieldName, Object o){
        try{
            Field field = className.getDeclaredField(fieldName);
            field.setAccessible(true);

            List flyweightStore = (ArrayList) field.get(o);
            flyweightStore.clear();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setup()
    {
        harryPotter1 = Novel.createNovel("Harry Potter and the Philosopher’s Stone", "J.K. Rowling", Genre.FANTASY, 14.99f);
        harryPotter2 = Novel.createNovel("Harry Potter and the Philosopher’s Stone", "J.K. Rowling", Genre.FANTASY, 14.99f);
        hunger = Novel.createNovel("The Hunger", "Alma Katsu", Genre.HORROR, 22.38f);

        books.add(harryPotter1);
        books.add(hunger);

        boxSet = BoxSet.createBoxSet("Box Set", books);
    }

    @AfterEach
    public void tearDown()
    {
        resetFlyweightStore(Novel.class, "novels", harryPotter1);
        resetFlyweightStore(BoxSet.class, "boxSets", boxSet);
    }

    @Test
    public void testTitleNovel()
    {
        assertEquals("Harry Potter and the Philosopher’s Stone", harryPotter1.getTitle());
    }
    @Test
    public void testAuthorNovel()
    {
        assertEquals("J.K. Rowling", harryPotter1.getAuthor());
    }
    @Test
    public void testGenreNovel()
    {
        assertEquals(Genre.FANTASY , harryPotter1.getGenre());
    }
    @Test
    public void testPriceNovel()
    {
        assertEquals(14.99f , harryPotter1.getPrice());
    }
    @Test
    public void testSameNovel(){
        assertSame(harryPotter1, harryPotter2);
    }
    @Test
    public void testTitleBoxSet()
    {
        assertEquals("Box Set", boxSet.getTitle());
    }
    @Test
    public void testAuthorBoxSet()
    {
        assertEquals("Various", boxSet.getAuthor());
    }
    @Test
    public void testBooksOfBoxSet()
    {
        assertEquals(books, boxSet.getBooks());
    }
    @Test
    public void testPriceBoxSet()
    {
        assertEquals(37.37f, boxSet.getPrice());
    }
    @Test
    public void testInvalidBoxSet()
    {
        try{
            ArrayList<Book> boxSetList = new ArrayList<>();
            boxSetList.add(boxSet);
            BoxSet invalidBoxSet = BoxSet.createBoxSet("Invalid Box Set", boxSetList);
            fail();
        } catch (IllegalArgumentException e){
            // pass
        }
    }
}
