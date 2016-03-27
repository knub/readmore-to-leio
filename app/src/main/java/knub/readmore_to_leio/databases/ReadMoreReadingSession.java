package knub.readmore_to_leio.databases;

public class ReadMoreReadingSession {

    int firstPage;
    int lastPage;
    int bookForeignKey;
    double sessionLength;
    double startTimestamp;

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getBookForeignKey() {
        return bookForeignKey;
    }

    public void setBookForeignKey(int bookForeignKey) {
        this.bookForeignKey = bookForeignKey;
    }

    public double getSessionLength() {
        return sessionLength;
    }

    public void setSessionLength(double sessionLength) {
        this.sessionLength = sessionLength;
    }

    public double getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(double startTimestamp) {
        this.startTimestamp = startTimestamp;
    }
}
