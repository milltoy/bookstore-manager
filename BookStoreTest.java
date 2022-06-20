import java.util.ArrayList;

public class BookStoreTest {
    public static void main(String[] args) {
        // ==================== Book ====================
        Book harryPotter1 = Novel.createNovel("Harry Potter and the Philosopher’s Stone", "J.K. Rowling", Genre.FANTASY, 14.99f);
        Book harryPotter2 = Novel.createNovel("Harry Potter and the Chamber of Secrets", "J.K. Rowling", Genre.FANTASY, 12.5f);
        Book harryPotter3 = Novel.createNovel("Harry Potter and the Prisoner of Azkaban", "J.K. Rowling", Genre.FANTASY, 12.5f);
        Book hunger = Novel.createNovel("The Hunger", "Alma Katsu", Genre.HORROR, 22.38f);
        Book goneGirl = Novel.createNovel("Gone Girl", "Gillian Flynn", Genre.MYSTERIES, 5.99f);
        Book snowCrash = Novel.createNovel("Snow Crash", "Neal Stephenson", Genre.SCI_FI, 23.0f);
        Book risky = Novel.createNovel("Risky", "Aurora Rose", Genre.ROMANCE, 17.95f);
        Book joyland1 = Novel.createNovel("Joyland", "Emily Schultz", Genre.MYSTERIES, 34.99f);
        Book joyland2 = Novel.createNovel("Joyland", "Stephen King", Genre.MYSTERIES, 14.95f);

        // ==================== Box Set ====================
        ArrayList<Book> harryPotterList = new ArrayList();
        harryPotterList.add(harryPotter1);
        harryPotterList.add(harryPotter2);
        harryPotterList.add(harryPotter3);
        BoxSet harryPotterBoxSet = BoxSet.createBoxSet("Harry Potter Box Set", harryPotterList);

        // ==================== BookStore ====================
        Bookstore.createBookstore("Bookstore Name");
        Bookstore bookstore = Bookstore.getBookstore();
        bookstore.addBookForSale(harryPotter1, 5);
        bookstore.addBookForSale(hunger, 8);
        bookstore.addBookForSale(harryPotter2, 1);
        bookstore.removeBookForSale(hunger, 4);

        FilterByAuthor byName = new FilterByAuthor();
        byName.addAuthorToFilter("J.K. Rowling");
        System.out.println(bookstore.getBookFilterResult(byName));

        FilterByPriceRange byPriceRange = new FilterByPriceRange();
        byPriceRange.setMax(13.0f);
        System.out.println(bookstore.getBookFilterResult(byPriceRange));

        FilterByManyCriteria manyCri = new FilterByManyCriteria();
        manyCri.addFilter(byName);
        manyCri.addFilter(byPriceRange);
        System.out.println(bookstore.getBookFilterResult(manyCri));
    }
}
