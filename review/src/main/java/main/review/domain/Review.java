package main.review.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class Review {

    @GeneratedValue
    @Id
    private Long id;

    private Integer rating;

    private String username;

    private String comment;

    private String gameName;

    public Review() {
    }

    public Integer getRating() {
        return rating;
    }

    public Review(Integer rating, String username, String comment, String gameName) {
        this.rating = rating;
        this.username = username;
        this.comment = comment;
        this.gameName = gameName;
    }
    public Review(Review review){
        this.id = review.id;
        this.rating = review.rating;
        this.username = review.username;
        this.comment = review.comment;
        this.gameName = review.gameName;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating=" + rating +
                ", username='" + username + '\'' +
                ", comment='" + comment + '\'' +
                ", gameName='" + gameName + '\'' +
                '}';
    }
}
