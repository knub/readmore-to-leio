package knub.readmore_to_leio.databases;

public class ReadMoreBook {

    private int primaryKey;
    private String title;
    private String author;

    private int firstPage;
    private int lastPage;

    private double startTimestamp;
    private double endTimestamp;

    private int order;

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null) {
            this.author = "[no author]";
        } else {
            this.author = author;
        }
    }

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

    public double getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(double startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public double getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(double endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
