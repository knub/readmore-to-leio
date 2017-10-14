package knub.readmore_to_leio;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import knub.readmore_to_leio.databases.LeioDatabase;
import knub.readmore_to_leio.databases.ReadMoreDatabase;

public class MainActivity extends AppCompatActivity {

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
        // Run device, e.g. Nexus 6P API
        // Start Android Device Monitor or, simpler, use the scripts under db_management
        // Files are under /data/data/knub.readmore_to_leio/files
        // Change next line after these comments
        //  the realm database should be called `Leio.realm` and compressed into a zip file with the `.lbf` extension
        String path = APPLICATION_FILE_PATH + "/20171010T201646-ReadMore.backup";
        Log.i("Converter", path);
        ReadMoreDatabase readMoreDb = new ReadMoreDatabase(path);
        LeioDatabase leioDb = new LeioDatabase(getApplicationContext());
        ReadMoreToLeioConverter converter = new ReadMoreToLeioConverter(readMoreDb, leioDb);
        converter.convertDatabases();

        String toastText = String.format("%d books - %d sessions", leioDb.getNumberOfBooks(), leioDb.getNumberOfSessions());
        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();

        readMoreDb.close();
        leioDb.close();
        Log.i("DONE", "DONE");
    }

}
