package knub.readmore_to_leio;

import java.util.Iterator;
import java.util.Map;

import knub.readmore_to_leio.databases.LeioDatabase;
import knub.readmore_to_leio.databases.ReadMoreDatabase;
import knub.readmore_to_leio.realm.Book;

public class ReadMoreToLeioConverter {

    private final ReadMoreDatabase readmore;
    private final LeioDatabase leio;

    public ReadMoreToLeioConverter(ReadMoreDatabase readmore, LeioDatabase leio) {
        this.readmore = readmore;
        this.leio = leio;
    }

    public void convertDatabases() {
        Iterator<Map<String, String>> readmoreBooks = readmore.getAllBooks();
        while (readmoreBooks.hasNext()) {
            Map<String, String> readmoreBook = readmoreBooks.next();
            Book leioBook = new Book();

            leioBook.setKey(buildKey(readmoreBook));
            leioBook.setTitle(readmoreBook.get("ZBOOKNAME"));

            leio.storeBook(leioBook);
        }
    }

    int keyCounter = 0;
    private String buildKey(Map<String, String> readmoreBook) {
        return String.format("%s%s_%d",
                readmoreBook.get("ZBOOKNAME").charAt(0),
                readmoreBook.get("ZAUTHORNAME").charAt(0),
                keyCounter++);
    }
}
