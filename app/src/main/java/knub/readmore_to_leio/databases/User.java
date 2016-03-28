package knub.readmore_to_leio.databases;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class User extends RealmObject {

    Book selectedBook;
    boolean countAllArchived = true;
    boolean autoStart = true;
    @Required
    String bookDetail = "timeToGo";
    @Required
    String weeklyChart = "timeRead";

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

    public boolean isCountAllArchived() {
        return countAllArchived;
    }

    public void setCountAllArchived(boolean countAllArchived) {
        this.countAllArchived = countAllArchived;
    }

    public boolean isAutoStart() {
        return autoStart;
    }

    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
    }

    public String getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(String bookDetail) {
        this.bookDetail = bookDetail;
    }

    public String getWeeklyChart() {
        return weeklyChart;
    }

    public void setWeeklyChart(String weeklyChart) {
        this.weeklyChart = weeklyChart;
    }
}
