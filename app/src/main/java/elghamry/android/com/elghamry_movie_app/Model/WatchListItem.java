package elghamry.android.com.elghamry_movie_app.Model;

/**
 * Created by ELGHAMRY on 28/04/2016.
 */
public class WatchListItem {

    String title;
    String img;
    String date;
    String id;

    public WatchListItem(String title, String img, String date, String id) {
        this.title = title;
        this.img = img;
        this.date = date;
        this.id = id;
    }

    public WatchListItem() {
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }
}
