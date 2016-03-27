package knub.readmore_to_leio.databases;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import knub.readmore_to_leio.BuildConfig;

public class ReadMoreDatabase {

    String TAG = this.getClass().getSimpleName();

    SQLiteDatabase db;
    public ReadMoreDatabase(String databaseFileName) {
        File file = new File(databaseFileName);
        if (BuildConfig.DEBUG && !file.exists())
            throw new RuntimeException("Database does not exist");

        this.db = SQLiteDatabase.openDatabase(databaseFileName, null, 0);
    }

    public Iterator<Map<String, String>> getAllBooks() {
        @SuppressLint("Recycle")
        Cursor result = db.query("ZBOOK", new String[] { "ZBOOKNAME", "ZAUTHORNAME" }, null, null, null, null, null, null);
        return new ResultIterator(result);
    }
}

class ResultIterator implements Iterator<Map<String, String>> {

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
    public Map<String, String> next() {
        cursor.moveToNext();
        Map<String, String> row = new HashMap<>();
        for (String columnName : columnNames) {
            int idx = cursor.getColumnIndex(columnName);
            String val = cursor.getString(idx);
            row.put(columnName, val);
        }
        return row;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
