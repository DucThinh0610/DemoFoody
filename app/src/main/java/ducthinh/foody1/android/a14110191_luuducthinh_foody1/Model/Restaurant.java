package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Test on 5/13/2017.
 */

public class Restaurant {
    int id,total_reviews,danhmuc;
    int id_city;
    int id_district;
    String name,phone,photo,tenmonan;
    String address;
    float avg_rating;
    Date ngaytao;

    public Restaurant(JSONObject jsonObject) {
        try {
            this.id=jsonObject.getInt("id");
            this.total_reviews=jsonObject.getInt("total_reviews");
            this.danhmuc=jsonObject.getInt("danhmuc");
            this.id_city=jsonObject.getInt("id_city");
            this.id_district=jsonObject.getInt("id_district");
            this.name=jsonObject.getString("name");
            this.phone=jsonObject.getString("phone");
            this.photo=jsonObject.getString("photo");
            this.tenmonan=jsonObject.getString("tenmonan");
            this.address=jsonObject.getString("address");
            this.avg_rating=Float.parseFloat(String.valueOf(jsonObject.getDouble("avg_rating")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Restaurant(int id, int total_reviews, int danhmuc, int id_city, int id_district, String name, String phone, String photo, String tenmonan, String address, float avg_rating, Date ngaytao) {
        this.id = id;
        this.total_reviews = total_reviews;
        this.danhmuc = danhmuc;
        this.id_city = id_city;
        this.id_district = id_district;
        this.name = name;
        this.phone = phone;
        this.photo = photo;
        this.tenmonan = tenmonan;
        this.address = address;
        this.avg_rating = avg_rating;
        this.ngaytao = ngaytao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal_reviews() {
        return total_reviews;
    }

    public void setTotal_reviews(int total_reviews) {
        this.total_reviews = total_reviews;
    }

    public int getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(int danhmuc) {
        this.danhmuc = danhmuc;
    }

    public int getId_city() {
        return id_city;
    }

    public void setId_city(int id_city) {
        this.id_city = id_city;
    }

    public int getId_district() {
        return id_district;
    }

    public void setId_district(int id_district) {
        this.id_district = id_district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(float avg_rating) {
        this.avg_rating = avg_rating;
    }

    public Date getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Date ngaytao) {
        this.ngaytao = ngaytao;
    }
}
