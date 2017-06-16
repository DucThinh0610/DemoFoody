package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model;

import android.graphics.Bitmap;

/**
 * Created by Test on 4/2/2017.
 */

public class AddressOfFood {
    private long id;
    private int id_tp;
    private int id_district;
    private int idCategory;
    private String name, address;
    private float rating;
    private String phone;
    private Bitmap photo;
    private int totalReview;

    public AddressOfFood() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getId_tp() {
        return id_tp;
    }

    public void setId_tp(int id_tp) {
        this.id_tp = id_tp;
    }

    public int getId_district() {
        return id_district;
    }

    public void setId_district(int id_district) {
        this.id_district = id_district;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public int getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(int totalReview) {
        this.totalReview = totalReview;
    }

    public AddressOfFood(long id, int id_tp, int id_district, int idCategory, String name, String address, float rating, String phone, Bitmap photo, int totalReview) {
        this.id = id;
        this.id_tp = id_tp;
        this.id_district = id_district;
        this.idCategory = idCategory;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.phone = phone;
        this.photo = photo;
        this.totalReview = totalReview;
    }
}
