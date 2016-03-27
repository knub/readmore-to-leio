package knub.readmore_to_leio.databases;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.Iterator;

import knub.readmore_to_leio.BuildConfig;
import static knub.readmore_to_leio.databases.ReadMoreColumnNames.*;

public class ReadMoreDatabase {

    String TAG = this.getClass().getSimpleName();
    String[] COLUMN_NAMES = {
        TITLE,
        AUTHOR,
        FIRST_PAGE,
        LAST_PAGE,
        STARTED_AT,
        FINISHED_AT,
        ORDER
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
        Cursor result = db.query("ZBOOK", COLUMN_NAMES, null, null, null, null, null, null);
        return new ResultIterator(result);
    }
}

class ResultIterator implements Iterator<ReadMoreBook> {

    String TAG = this.getClass().getSimpleName();

    private final Cursor cursor;
    String[] columnNames;

    public ResultIterator(Cursor cursor) {
        this.cursor = cursor;
        cursor.moveToFirst();

        columnNames = cursor.getColumnNames();
    }

    @Override
    public boolean hasNext() {
        if (cursor.isLast()) {
            cursor.close();
            return false;
        }
        return true;
    }

    @Override
    public ReadMoreBook next() {
        cursor.moveToNext();
        ReadMoreBook book = new ReadMoreBook();

        book.setTitle(cursor.getString(indexOf(TITLE)));
        book.setAuthor(cursor.getString(indexOf(AUTHOR)));
        book.setFirstPage(cursor.getInt(indexOf(FIRST_PAGE)));
        book.setLastPage(cursor.getInt(indexOf(LAST_PAGE)));
        book.setStartTimestamp(cursor.getDouble(indexOf(STARTED_AT)));
        book.setEndTimestamp(cursor.getDouble(indexOf(FINISHED_AT)));

        return book;
    }

    private int indexOf(String columnName) {
        return cursor.getColumnIndex(columnName);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
