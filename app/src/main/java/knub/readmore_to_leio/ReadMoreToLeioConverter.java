package knub.readmore_to_leio;

import android.util.Log;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import knub.readmore_to_leio.databases.Book;
import knub.readmore_to_leio.databases.LeioDatabase;
import knub.readmore_to_leio.databases.ReadMoreBook;
import knub.readmore_to_leio.databases.ReadMoreDatabase;
import knub.readmore_to_leio.databases.ReadMoreReadingSession;
import knub.readmore_to_leio.databases.ReadingSession;

/**
 * Given access to both databases, these class converts a ReadMore database into a Leio realm
 * database.
 */
public class ReadMoreToLeioConverter {

    private final ReadMoreDatabase readMore;
    private final LeioDatabase leio;

    public ReadMoreToLeioConverter(ReadMoreDatabase readmore, LeioDatabase leio) {
        this.readMore = readmore;
        this.leio = leio;
    }

    /**
     * Performs the conversion.
     */
    public void convertDatabases() {
        Log.i("Converter", "Converting Books");
        Map<Integer, Book> books = convertBooks();
        Log.i("Converter", "Converted Books");
        Log.i("Converter", "Converting Sessions");
        convertSessions(books);
        Log.i("Converter", "Converted Sessions");
    }

    private Map<Integer, Book> convertBooks() {
        Map<Integer, Book> books = new HashMap<>();

        Iterator<ReadMoreBook> readMoreBooks = readMore.getAllBooks();
        while (readMoreBooks.hasNext()) {
            ReadMoreBook readMoreBook = readMoreBooks.next();
            Log.i("Converter", readMoreBook.getTitle());
            leio.beginTransaction();
            Book leioBook = convertSingleBook(readMoreBook);
            leio.commitTransaction();

            books.put(readMoreBook.getPrimaryKey(), leioBook);
        }
        return books;
    }

    private Book convertSingleBook(ReadMoreBook readMoreBook) {
        Book leioBook = leio.createBook();

        leioBook.setTitle(readMoreBook.getTitle());
        leioBook.setAuthor(readMoreBook.getAuthor());
        leioBook.setFirstPage(readMoreBook.getFirstPage());
        leioBook.setLastPage(readMoreBook.getLastPage());
        leioBook.setKey(buildBookKey(readMoreBook));

        double finishedDate = readMoreBook.getEndTimestamp();
        if (finishedDate == 0.0) {
            leioBook.setArchived(false);
            leioBook.setDate(null);
        } else {
            Date date = convertReadMoreTimeStampToDate(finishedDate);
            leioBook.setArchived(true);
            leioBook.setDate(date);
        }

        leioBook.setOrder(readMoreBook.getOrder());
        return leioBook;
    }

    private void convertSessions(Map<Integer, Book> books) {
        Iterator<ReadMoreReadingSession> readMoreSessions = readMore.getAllReadingSessions();
        while (readMoreSessions.hasNext()) {
            ReadMoreReadingSession readMoreSession = readMoreSessions.next();
            leio.beginTransaction();
            Log.i("Converter", "Converting session");
            ReadingSession leioSession = convertSingleSession(readMoreSession);

            // identify belonging book
            Book book = books.get(readMoreSession.getBookForeignKey());
            //assert book != null;
            if (book == null) {
                Log.w("Converter", "Book is null");
            }
            leioSession.setBook(book);
            // set session key based on book now
            leioSession.setKey(buildSessionKey(leioSession));
            leio.commitTransaction();
        }
    }

    private ReadingSession convertSingleSession(ReadMoreReadingSession readMoreSession) {
        ReadingSession session = leio.createReadingSession();

        session.setFirstPage(readMoreSession.getFirstPage());
        session.setLastPage(readMoreSession.getLastPage());
        session.setDuration(readMoreSession.getSessionLength());
        // ReadMore stores start timestamp of session. Leio uses end timestamp of session.
        // Thus, we just add the session length to the start timestamp
        session.setDate(convertReadMoreTimeStampToDate(readMoreSession.getStartTimestamp() +
                readMoreSession.getSessionLength()));
        return session;
    }

    /**
     * Helper methods
     */

    private Date convertReadMoreTimeStampToDate(double timestamp) {
        // There is a strange offset in the ReadMore databases, it counts the seconds from
        // Jan 1st, 2001. Depending on whether it is stored in UTC or in local time, this offset may
        // need to be adapted. I do not have access to a ReadMore database from a different time
        // zone. This offset is based on the German time zone.
        long READMORE_DB_TIMESTAMP_OFFSET = 978307200;
        long MILLISECONDS_PER_SECOND = 1000;
        Timestamp stamp = new Timestamp((long) (timestamp + READMORE_DB_TIMESTAMP_OFFSET) * MILLISECONDS_PER_SECOND);
        return new Date(stamp.getTime());
    }

    int bookKeyCounter = 0;
    private String buildBookKey(ReadMoreBook readMoreBook) {
        return String.format("%s%s_%d",
                readMoreBook.getTitle().charAt(0),
                readMoreBook.getAuthor().charAt(0),
                bookKeyCounter++);
    }
    int sessionKeyCounter = 0;
    private String buildSessionKey(ReadingSession readingSession) {
        return String.format("%sS%d",
                readingSession.getBook().getKey(),
                sessionKeyCounter++);
    }
}
