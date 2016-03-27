package knub.readmore_to_leio;

import android.util.Log;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import knub.readmore_to_leio.databases.LeioDatabase;
import knub.readmore_to_leio.databases.ReadMoreBook;
import knub.readmore_to_leio.databases.ReadMoreDatabase;
import knub.readmore_to_leio.databases.Book;

public class ReadMoreToLeioConverter {

    private final ReadMoreDatabase readMore;
    private final LeioDatabase leio;

    private String TAG = this.getClass().getSimpleName();

    public ReadMoreToLeioConverter(ReadMoreDatabase readmore, LeioDatabase leio) {
        this.readMore = readmore;
        this.leio = leio;
    }

    public void convertDatabases() {
        Iterator<ReadMoreBook> readMoreBooks = readMore.getAllBooks();
        while (readMoreBooks.hasNext()) {
            ReadMoreBook readMoreBook = readMoreBooks.next();
            Book leioBook = new Book();

            leioBook.setTitle(readMoreBook.getTitle());
            leioBook.setAuthor(readMoreBook.getAuthor());
            leioBook.setFirstPage(readMoreBook.getFirstPage());
            leioBook.setLastPage(readMoreBook.getLastPage());
            leioBook.setKey(buildKey(readMoreBook));

            double finishedDate = readMoreBook.getEndTimestamp();
            if (finishedDate == 0.0) {
                leioBook.setArchived(false);
                leioBook.setDate(null);
            } else {
                Date date = convertReadMoreTimeStampToDate(finishedDate);
                Log.d(TAG, "Date: " + date.toString());
                leioBook.setArchived(true);
                leioBook.setDate(date);
            }

            leioBook.setOrder(readMoreBook.getOrder());

            leio.storeBook(leioBook);
        }
    }

    private Date convertReadMoreTimeStampToDate(double timestamp) {
        Timestamp stamp = new Timestamp((long) (timestamp + 978307200) * 1000);
        Date date = new Date(stamp.getTime());
        return date;
    }

    int keyCounter = 0;
    private String buildKey(ReadMoreBook readMoreBook) {
        return String.format("%s%s_%d",
                readMoreBook.getTitle().charAt(0),
                readMoreBook.getAuthor().charAt(0),
                keyCounter++);
    }
}
