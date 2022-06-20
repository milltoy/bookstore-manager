import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents the genre and the price of the book
 */

public class Novel extends AbstractBook{
    // Flyweight store
    private static final List<Novel> novels = new ArrayList<>();

    private final Genre aGenre;
    private final float aPrice;

    /**
     * Creates a new Book when it is not present in the Flyweight store
     * @param pTitle  the title of the book
     * @param pAuthor the author of the book
     * @param pGenre  the genre of the book
     * @param pPrice  the price of the book
     */
    private Novel(String pTitle, String pAuthor, Genre pGenre, float pPrice){
        super(pTitle, pAuthor);
        aGenre = pGenre;
        aPrice = pPrice;
    }

    /**
     * User can create or get a book that they want by calling this method
     * @param pTitle  the title of the book
     * @param pAuthor the author of the book
     * @param pGenre  the genre of the book
     * @param pPrice  the price of the book
     * @pre pTitle != null && pAuthor != null && pGenre != null && pPrice > 0.0
     * @return Novel object
     */
    public static Novel createNovel(String pTitle, String pAuthor, Genre pGenre, float pPrice){
        assert pTitle != null && pAuthor != null && pGenre != null && pPrice > 0.0;
        // already in flyweight store
        for(Novel novel: novels){
            if(novel.getTitle().equalsIgnoreCase(pTitle) &&
                    novel.getAuthor().equalsIgnoreCase(pAuthor) &&
                    novel.aGenre.equals(pGenre)){
                return novel;
            }
        }
        // create a new instance and store in the flyweight store
        Novel n = new Novel(pTitle, pAuthor, pGenre, pPrice);
        novels.add(n);
        return n;
    }

    public Genre getGenre(){
        return aGenre;
    }

    /**
     * @return the price of the book
     */
    @Override
    public float getPrice() {
        return aPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Novel novel = (Novel) o;
        return aGenre.equals(novel.aGenre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), aGenre);
    }

    @Override
    public String toString() {
        return "Novel{" +
                "Title=" + getTitle() +
                ", Author=" + getAuthor() +
                ", Genre=" + aGenre +
                '}';
    }

}
