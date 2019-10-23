package model;

import java.util.Date;

public class Post {
    int id;
    String title;
    String postText;
    Date postDate;
    House houseReference;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public House getHouseReference() {
        return houseReference;
    }

    public void setHouseReference(House houseReference) {
        this.houseReference = houseReference;
    }

    public Post(int id, String title, String postText, Date postDate, House houseReference) {
        this.id = id;
        this.title = title;
        this.postText = postText;
        this.postDate = postDate;
        this.houseReference = houseReference;
    }
}
