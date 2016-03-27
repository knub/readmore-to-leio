package knub.readmore_to_leio;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    private String APPLICATION_FILE_PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        APPLICATION_FILE_PATH = getApplicationContext().getFilesDir().getPath();
    }

    public void convertDatabase(View v) {
        ReadMoreDatabase database = new ReadMoreDatabase(APPLICATION_FILE_PATH + "/20160327T123540-ReadMore.sqlite3");
        database.getAllBooks();
        Toast toast = Toast.makeText(getApplicationContext(), "Database created", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void debugButtonClicked(View v) {
        String[] permissions = { Manifest.permission.WRITE_EXTERNAL_STORAGE };
        requestPermissions(permissions, 43);
        Toast.makeText(getApplicationContext(), "Debug button clicked", Toast.LENGTH_SHORT).show();
    }

}
