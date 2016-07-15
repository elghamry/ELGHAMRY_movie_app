package elghamry.android.com.elghamry_movie_app.Model;

/**
 * Created by ELGHAMRY on 24/04/2016.
 */
public class movie {
    String title,image,overview,date,vote,id;

    public movie( String date,String id, String image,String overview,String title,  String vote ) {
        this.title = title;
        this.image = image;
        this.overview = overview;
        this.date = date;
        this.vote = vote;
        this.id = id;
    }

    public movie() {
    }

    public String getTitle() {
        return title;
    }


    public String getImage() {
        return image;
    }



    public String getOverview() {
        return overview;
    }



    public String getDate() {
        return date;
    }


    public String getVote() {
        return vote;
    }





    public String getId() {
        return id;
    }
}
