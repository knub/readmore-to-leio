package knub.readmore_to_leio.databases;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.Iterator;

import knub.readmore_to_leio.BuildConfig;

import static knub.readmore_to_leio.databases.ReadMoreColumnNames.AUTHOR;
import static knub.readmore_to_leio.databases.ReadMoreColumnNames.BOOK_FOREIGN_KEY;
import static knub.readmore_to_leio.databases.ReadMoreColumnNames.FINISHED_AT;
import static knub.readmore_to_leio.databases.ReadMoreColumnNames.FIRST_PAGE;
import static knub.readmore_to_leio.databases.ReadMoreColumnNames.LAST_PAGE;
import static knub.readmore_to_leio.databases.ReadMoreColumnNames.ORDER;
import static knub.readmore_to_leio.databases.ReadMoreColumnNames.PRIMARY_KEY;
import static knub.readmore_to_leio.databases.ReadMoreColumnNames.SESSION_LENGTH;
import static knub.readmore_to_leio.databases.ReadMoreColumnNames.SESSION_START;
import static knub.readmore_to_leio.databases.ReadMoreColumnNames.STARTED_AT;
import static knub.readmore_to_leio.databases.ReadMoreColumnNames.TITLE;

public class ReadMoreDatabase {

    String[] BOOK_COLUMN_NAMES = {
        PRIMARY_KEY,
        TITLE,
        AUTHOR,
        FIRST_PAGE,
        LAST_PAGE,
        STARTED_AT,
        FINISHED_AT,
        ORDER
    };

    String[] READING_SESSION_COLUMN_NAMES = {
        PRIMARY_KEY,
        FIRST_PAGE,
        LAST_PAGE,
        BOOK_FOREIGN_KEY,
        SESSION_LENGTH,
        SESSION_START
    };

    SQLiteDatabase db;

    public ReadMoreDatabase(String databaseFileName) {
        File file = new File(databaseFileName);
        if (BuildConfig.DEBUG && !file.exists())
            throw new RuntimeException("Database does not exist");

        this.db = SQLiteDatabase.openDatabase(databaseFileName, null, 0);
    }

    public Iterator<ReadMoreBook> getAllBooks() {
        @SuppressLint("Recycle")
        Cursor result = db.query("ZBOOK", BOOK_COLUMN_NAMES, null, null, null, null, null, null);
        return new BookResultIterator(result);
    }


    public Iterator<ReadMoreReadingSession> getAllReadingSessions() {
        @SuppressLint("Recycle")
        Cursor result = db.query("ZREADINGSESSION", READING_SESSION_COLUMN_NAMES,null, null, null,
                null, null, null);
        return new ReadingSessionResultIterator(result);
    }
}

abstract class ReadMoreIterator<T> implements Iterator<T> {

    protected final Cursor cursor;
    String[] columnNames;

    public ReadMoreIterator(Cursor cursor) {
        this.cursor = cursor;
        cursor.moveToFirst();

        columnNames = cursor.getColumnNames();
    }

    @Override
    public boolean hasNext() {
        if (cursor.isAfterLast()) {
            cursor.close();
            return false;
        }
        return true;
    }

    protected int indexOf(String columnName) {
        return cursor.getColumnIndex(columnName);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

class BookResultIterator extends ReadMoreIterator<ReadMoreBook> {

    public BookResultIterator(Cursor cursor) {
        super(cursor);
    }

    @Override
    public ReadMoreBook next() {
        ReadMoreBook book = new ReadMoreBook();

        book.setPrimaryKey(cursor.getInt(indexOf(PRIMARY_KEY)));
        book.setTitle(cursor.getString(indexOf(TITLE)));
        book.setAuthor(cursor.getString(indexOf(AUTHOR)));
        book.setFirstPage(cursor.getInt(indexOf(FIRST_PAGE)));
        book.setLastPage(cursor.getInt(indexOf(LAST_PAGE)));
        book.setStartTimestamp(cursor.getDouble(indexOf(STARTED_AT)));
        book.setEndTimestamp(cursor.getDouble(indexOf(FINISHED_AT)));

        cursor.moveToNext();

        return book;
    }
}

class ReadingSessionResultIterator extends ReadMoreIterator<ReadMoreReadingSession> {

    public ReadingSessionResultIterator(Cursor cursor) {
        super(cursor);
    }

    @Override
    public ReadMoreReadingSession next() {
        ReadMoreReadingSession readingSession = new ReadMoreReadingSession();

        readingSession.setFirstPage(cursor.getInt(indexOf(FIRST_PAGE)));
        readingSession.setLastPage(cursor.getInt(indexOf(LAST_PAGE)));
        readingSession.setBookForeignKey(cursor.getInt(indexOf(BOOK_FOREIGN_KEY)));
        readingSession.setSessionLength(cursor.getDouble(indexOf(SESSION_LENGTH)));
        readingSession.setStartTimestamp(cursor.getDouble(indexOf(SESSION_START)));

        cursor.moveToNext();

        return readingSession;
    }

}
