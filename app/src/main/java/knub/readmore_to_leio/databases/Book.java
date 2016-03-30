package knub.readmore_to_leio.databases;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Book extends RealmObject {

    @Required
    private String title = "";
    @Required
    private String author = "";
    private int firstPage = 0;
    private int lastPage = 0;
    @Required @PrimaryKey
    private String key = "";

    private boolean ebook = false;
    @Required
    private String readingUnit = "page";
    private boolean outOfOrder = false;

    private boolean archived = false;
    private Date date;

    private Date scheduledDate;
    private double scheduledTime = 0.0;
    private int scheduledPages = 0;
    private double scheduledDays = 0.0;
    private Date schedulingDate;

    private int order = 0;

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
        this.author = author;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isEbook() {
        return ebook;
    }

    public void setEbook(boolean ebook) {
        this.ebook = ebook;
    }

    public String getReadingUnit() {
        return readingUnit;
    }

    public void setReadingUnit(String readingUnit) {
        this.readingUnit = readingUnit;
    }

    public boolean isOutOfOrder() {
        return outOfOrder;
    }

    public void setOutOfOrder(boolean outOfOrder) {
        this.outOfOrder = outOfOrder;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public double getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(double scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public int getScheduledPages() {
        return scheduledPages;
    }

    public void setScheduledPages(int scheduledPages) {
        this.scheduledPages = scheduledPages;
    }

    public double getScheduledDays() {
        return scheduledDays;
    }

    public void setScheduledDays(double scheduledDays) {
        this.scheduledDays = scheduledDays;
    }

    public Date getSchedulingDate() {
        return schedulingDate;
    }

    public void setSchedulingDate(Date schedulingDate) {
        this.schedulingDate = schedulingDate;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
