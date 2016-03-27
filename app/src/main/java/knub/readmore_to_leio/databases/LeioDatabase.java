package knub.readmore_to_leio.databases;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class LeioDatabase {

    private final Realm realm;

    public LeioDatabase(Context context) {
        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        // Get a Realm instance for this thread
        realm = Realm.getInstance(realmConfig);
        clearDatabase();
    }

    private void clearDatabase() {
        realm.beginTransaction();
        realm.clear(Book.class);
        realm.clear(ReadingSession.class);
        realm.commitTransaction();
    }

    public int getNumberOfBooks() {
        RealmResults<Book> puppies = realm.where(Book.class).findAll();
        return puppies.size();
    }

    public void storeBook(Book book) {
        realm.beginTransaction();
        realm.copyToRealm(book);
        realm.commitTransaction();
    }
}
