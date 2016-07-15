package elghamry.android.com.elghamry_movie_app.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ELGHAMRY on 25/03/2016.
 */
public class Review implements Parcelable {
    String Author;
    String Content;

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Author);
        dest.writeString(this.Content);
    }

    public Review() {
    }

    protected Review(Parcel in) {
        this.Author = in.readString();
        this.Content = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
