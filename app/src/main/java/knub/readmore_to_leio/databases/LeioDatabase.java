package knub.readmore_to_leio.databases;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class LeioDatabase {

    private final Realm realm;

    public LeioDatabase(Context context) {
        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context)
                .schemaVersion(5)
                .build();
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
        RealmResults<Book> books = realm.where(Book.class).findAll();
        return books.size();
    }

    public int getNumberOfSessions() {
       RealmResults<ReadingSession> sessions = realm.where(ReadingSession.class).findAll();
        return sessions.size();
    }

    public Book createBook() {
        return realm.createObject(Book.class);
    }

    public ReadingSession createReadingSession() {
        return realm.createObject(ReadingSession.class);
    }

    public User createUser() {
        return realm.createObject(User.class);
    }

    public void beginTransaction() {
        realm.beginTransaction();
    }

    public void commitTransaction() {
        realm.commitTransaction();
    }

}
