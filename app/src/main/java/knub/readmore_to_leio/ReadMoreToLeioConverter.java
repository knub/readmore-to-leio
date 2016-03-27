package knub.readmore_to_leio;

import android.util.Log;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import knub.readmore_to_leio.databases.LeioDatabase;
import knub.readmore_to_leio.databases.ReadMoreBook;
import knub.readmore_to_leio.databases.ReadMoreDatabase;
import knub.readmore_to_leio.databases.Book;
import knub.readmore_to_leio.databases.ReadMoreReadingSession;
import knub.readmore_to_leio.databases.ReadingSession;

public class ReadMoreToLeioConverter {

    private final ReadMoreDatabase readMore;
    private final LeioDatabase leio;

    private String TAG = this.getClass().getSimpleName();

    public ReadMoreToLeioConverter(ReadMoreDatabase readmore, LeioDatabase leio) {
        this.readMore = readmore;
        this.leio = leio;
    }

    public void convertDatabases() {
        Map<Integer, Book> books = createBooks();

        Iterator<ReadMoreReadingSession> readMoreSessions = readMore.getAllReadingSessions();
        while (readMoreSessions.hasNext()) {
            ReadMoreReadingSession readMoreSession = readMoreSessions.next();
            leio.beginTransaction();
            ReadingSession leioSession = convertSingleSession(readMoreSession);

            Book book = books.get(readMoreSession.getBookForeignKey());
            if (book == null) {
                Log.e(TAG, readMoreSession.toString());
                Log.e(TAG, Integer.toString(readMoreSession.getBookForeignKey()));
            }
            assert book != null;
            leioSession.setBook(book);
            book.getReadingSessions().add(leioSession);
            leioSession.setKey(buildSessionKey(leioSession));
            leio.commitTransaction();
        }
    }

    private ReadingSession convertSingleSession(ReadMoreReadingSession readMoreSession) {
        ReadingSession session = leio.createReadingSession();

        session.setFirstPage(readMoreSession.getFirstPage());
        session.setLastPage(readMoreSession.getLastPage());
        session.setDuration(readMoreSession.getSessionLength());
        session.setDate(convertReadMoreTimeStampToDate(readMoreSession.getStartTimestamp()));
        return session;
    }

    private Map<Integer, Book> createBooks() {
        Map<Integer, Book> books = new HashMap<>();

        Iterator<ReadMoreBook> readMoreBooks = readMore.getAllBooks();
        while (readMoreBooks.hasNext()) {
            ReadMoreBook readMoreBook = readMoreBooks.next();
            leio.beginTransaction();
            Book leioBook = convertSingleBook(readMoreBook);
            leio.commitTransaction();

            Log.w(TAG, leioBook.getTitle());
            Log.w(TAG, Integer.toString(readMoreBook.getPrimaryKey()));
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

    private Date convertReadMoreTimeStampToDate(double timestamp) {
        Timestamp stamp = new Timestamp((long) (timestamp + 978307200) * 1000);
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
