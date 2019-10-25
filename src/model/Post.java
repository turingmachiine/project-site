package model;

import java.util.Date;

public class Post {
    int id;
    String title;
    String postText;
    String postDate;
    House houseReference;
    User author;
    String image;

    public Post(int id, String title, String postText, String postDate, House houseReference, User author, String image) {
        this.id = id;
        this.title = title;
        this.postText = postText;
        this.postDate = postDate;
        this.houseReference = houseReference;
        this.author = author;
        this.image = image;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public House getHouseReference() {
        return houseReference;
    }

    public void setHouseReference(House houseReference) {
        this.houseReference = houseReference;
    }

}
