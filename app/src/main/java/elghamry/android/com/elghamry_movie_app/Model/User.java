package elghamry.android.com.elghamry_movie_app.Model;

/**
 * Created by ELGHAMRY on 24/04/2016.
 */
public class User {
    private String name;
    private String email;

    // private HashMap<String, Object> timestampJoined;

    /**
     * Required public constructor
     */
    public User() {
    }

    /**
     * Use this constructor to create new User.
     * Takes user name, email and timestampJoined as params
     *
     * @param name
     * @param email

     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
        //  this.timestampJoined = timestampJoined;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }



}
