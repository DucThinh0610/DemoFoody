package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model;

/**
 * Created by Test on 4/7/2017.
 */

public class Review {
    private Integer id,itemId;
    private String name,comment,commmentTrim,Avatar,reviewUrl;
    private Double rating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommmentTrim() {
        return commmentTrim;
    }

    public void setCommmentTrim(String commmentTrim) {
        this.commmentTrim = commmentTrim;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
