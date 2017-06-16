package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.DBConnect.DecodeBitmapReduce;


/**
 * Created by Test on 4/8/2017.
 */

public class getAddressOfFood {//các phương thức get data từ csdl
    public List<AddressOfFood> getAddressOfFoodByCity(SQLiteDatabase database, int prePos, int idCity){
        String query="select tb_restaurant.id,tb_restaurant.id_city,tb_restaurant.id_district,tb_restaurant.name,tb_restaurant.address,tb_restaurant.avg_rating,tb_restaurant.phone,tb_restaurant.photo,tb_restaurant.total_reviews,tb_restaurant.danhmuc from tb_restaurant where tb_restaurant.id_city='"+idCity+"' limit "+ prePos + ", 10";
        Cursor cursor=database.rawQuery(query,null);
        return getAddressOfFoods(cursor);
    }
    public List<AddressOfFood> getAddressOfFoodByDistrict(SQLiteDatabase database, int prePos, int idDistrict){
        String query="select tb_restaurant.id,tb_restaurant.id_city,tb_restaurant.id_district,tb_restaurant.name,tb_restaurant.address,tb_restaurant.avg_rating,tb_restaurant.phone,tb_restaurant.photo,tb_restaurant.total_reviews,tb_restaurant.danhmuc from tb_restaurant where tb_restaurant.id_district='"+idDistrict+"' limit "+ prePos + ", 10";
        Cursor cursor=database.rawQuery(query,null);
        return getAddressOfFoods(cursor);
    }
    public List<AddressOfFood> getAddressOfFoodByStreet(SQLiteDatabase database, int prePos, int idcity,String phuong){
        String query="select tb_restaurant.id,tb_restaurant.id_city,tb_restaurant.id_district,tb_restaurant.name,tb_restaurant.address,tb_restaurant.avg_rating,tb_restaurant.phone,tb_restaurant.photo,tb_restaurant.total_reviews,tb_restaurant.danhmuc from tb_restaurant where tb_restaurant.id_city='"+idcity+"' and tb_restaurant.address like '%"+phuong.trim()+"%' limit "+ prePos + ", 10";
        Cursor cursor=database.rawQuery(query,null);
        return getAddressOfFoods(cursor);
    }
    public List<AddressOfFood> getAODByCategoryCity(SQLiteDatabase database, int prePos, int idcity,int idCategory){
        String query="select tb_restaurant.id,tb_restaurant.id_city,tb_restaurant.id_district,tb_restaurant.name,tb_restaurant.address,tb_restaurant.avg_rating,tb_restaurant.phone,tb_restaurant.photo,tb_restaurant.total_reviews,tb_restaurant.danhmuc from tb_restaurant where tb_restaurant.id_city='"+idcity+"' and tb_restaurant.danhmuc='"+idCategory+"' limit "+ prePos + ", 10";
        Cursor cursor=database.rawQuery(query,null);
        return getAddressOfFoods(cursor);
    }
    public List<AddressOfFood> getAODByCategoryDistric(SQLiteDatabase database, int prePos, int idDistrict,int idCategory){
        String query="select tb_restaurant.id,tb_restaurant.id_city,tb_restaurant.id_district,tb_restaurant.name,tb_restaurant.address,tb_restaurant.avg_rating,tb_restaurant.phone,tb_restaurant.photo,tb_restaurant.total_reviews,tb_restaurant.danhmuc from tb_restaurant where tb_restaurant.id_district='"+idDistrict+"' and tb_restaurant.danhmuc='"+idCategory+"' limit "+ prePos + ", 10";
        Cursor cursor=database.rawQuery(query,null);
        return getAddressOfFoods(cursor);
    }
    public List<AddressOfFood> getAODByCategoryPhuong(SQLiteDatabase database, int prePos, int idCity,int idCategory, String phuong){
        String query="select tb_restaurant.id,tb_restaurant.id_city,tb_restaurant.id_district,tb_restaurant.name,tb_restaurant.address,tb_restaurant.avg_rating,tb_restaurant.phone,tb_restaurant.photo,tb_restaurant.total_reviews,tb_restaurant.danhmuc from tb_restaurant where tb_restaurant.id_city='"+idCity+"' and tb_restaurant.danhmuc='"+idCategory+"' and tb_restaurant.address like '%"+phuong.trim()+"%' limit "+ prePos + ", 10";
        Cursor cursor=database.rawQuery(query,null);
        return getAddressOfFoods(cursor);
    }
    private List<AddressOfFood> getAddressOfFoods(Cursor cursor){//đổ dữ liệu vào model
        AddressOfFood addressOfFood;
        List<AddressOfFood> addressOfFoods=new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            addressOfFood=new AddressOfFood();
            addressOfFood.setId(cursor.getLong(0));
            addressOfFood.setId_tp(cursor.getInt(1));
            addressOfFood.setId_district(cursor.getInt(2));
            addressOfFood.setName(cursor.getString(3));
            addressOfFood.setAddress(cursor.getString(4));
            addressOfFood.setRating(cursor.getFloat(5));
            //addressOfFood.setPhoto(cursor.getString(7));
            byte[] byteArray = cursor.getBlob(7);
            Bitmap bm = DecodeBitmapReduce.decodeSampledBitmapFromResource(byteArray,100,100);
            addressOfFood.setPhoto(bm);
            addressOfFood.setPhone(cursor.getString(6));
            addressOfFood.setTotalReview(cursor.getInt(8));
            addressOfFood.setIdCategory(cursor.getInt(9));
            addressOfFoods.add(addressOfFood);
            cursor.moveToNext();
        }
        cursor.close();
        return addressOfFoods;
    }
}
