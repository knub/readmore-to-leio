package knub.readmore_to_leio;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;

public class ReadMoreDatabase {

    String TAG = this.getClass().getSimpleName();

    SQLiteDatabase db;
    public ReadMoreDatabase(String databaseFileName) {
        File file = new File(databaseFileName);
        if (BuildConfig.DEBUG && !file.exists())
            throw new RuntimeException("Database does not exist");

        this.db = SQLiteDatabase.openDatabase(databaseFileName, null, 0);
    }

    public void getAllBooks() {
        Cursor result = db.query("ZBOOK", new String[] {"ZBOOKNAME"}, null, null, null, null, null, null);
        result.moveToFirst();
        while (!result.isLast()) {
            Log.i(TAG, result.getString(0));
            result.moveToNext();
        }
        result.close();
    }
}
