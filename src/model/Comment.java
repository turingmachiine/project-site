package model;

import java.util.Date;

public class Comment {
    int id;
    String commentText;
    String commentDate;
    int upvotes;
    int downvotes;
    User user;
    Post post;

    public Comment(int id, String commentText, String commentDate, int upvotes, int downvotes, User user, Post post) {
        this.id = id;
        this.commentText = commentText;
        this.commentDate = commentDate;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.user = user;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }


    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }
}
