package ducthinh.foody1.android.a14110191_luuducthinh_foody1.DBConnect;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.AddressOfFood;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.City;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.District;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.Street;

/**
 * Created by Test on 4/2/2017.
 */

public class DBConnect extends SQLiteOpenHelper {//hàm load tạo csdl
    private static String DB_DIR="";
    private static String DB_NAME="Foody8.sqlite";
    private Context context;
    public static SQLiteDatabase database;
    public DBConnect(Context context){
        super(context,DB_NAME,null,1);
        this.context=context;
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_DIR = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_DIR = "/data/data/" + context.getPackageName() + "/databases/";
        }
        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createDataBase() throws IOException {
        boolean databaseExist= checkDataBase();
        if (databaseExist){
        }
        else {
            this.getReadableDatabase();
            copyDatabase(this.context);
        }
    }
    public boolean checkDataBase(){
        File databaseFile = new File(DB_DIR + DB_NAME);
        return databaseFile.exists();
    }
    public boolean copyDatabase(Context context) {
        try{
            InputStream inputStream = context.getAssets().open(DB_NAME);
            String outFileName = DB_DIR + DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }
    public void openDatabase() {
        String dbPath = this.context.getDatabasePath(DB_NAME).getPath();
        if (database != null && database.isOpen()){
            return;
        }
        database = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    public void closeDatabase() {
        if (database != null){
            database.close();
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        copyDatabase(context);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion)
            copyDatabase(context);
    }
    public List<City> getListCity() {
        City city;
        List<City> cities = new ArrayList<>();
        openDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM thanhpho", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            city = new City();
            city.setId(cursor.getInt(0));
            city.setName(cursor.getString(1));
            cities.add(city);
            cursor.moveToNext();
        }
        closeDatabase();
        return cities;
    }
    public List<District> getListDistrist(int idCity){
        District distrist;
        List<District> distrists=new ArrayList<>();
        openDatabase();

        Cursor cursor =database.rawQuery("SELECT quanhuyen.id,quanhuyen.idtp,quanhuyen.name FROM quanhuyen,thanhpho WHERE quanhuyen.idtp=thanhpho.id and thanhpho.id='"+idCity+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            distrist =new District();
            distrist.setId(cursor.getInt(0));
            distrist.setIdTP(cursor.getInt(1));
            distrist.setName(cursor.getString(2));
            distrists.add(distrist);
            cursor.moveToNext();
        }
        closeDatabase();
        return distrists;

    }
    public int TongSoQuan(int idCity){
        int kq=0;
        openDatabase();
        Cursor cursor =database.rawQuery("SELECT count(quanhuyen.id) as tongsoquan FROM quanhuyen,thanhpho WHERE quanhuyen.idtp=thanhpho.id and thanhpho.id='"+idCity+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            kq=cursor.getInt(0);
            cursor.moveToNext();
        }
        closeDatabase();
        return kq;
    }
    public String getNameCity(int idCity){
        String kq="";
        openDatabase();
        Cursor cursor =database.rawQuery("select thanhpho.name from thanhpho where thanhpho.id='"+idCity+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            kq=cursor.getString(0);
            cursor.moveToNext();
        }
        closeDatabase();
        return kq;
    }
    public List<Street> getListStreets(int id_street){
        Street street;
        List<Street>streets=new ArrayList<>();
        openDatabase();
        Cursor cursor =database.rawQuery("select ID,IDDistrict,Name from bt_duong where IDDistrict='"+id_street+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            street=new Street();
            street.setIdDistrict(cursor.getInt(1));
            street.setIdStreet(cursor.getInt(0));
            street.setNameStreet(cursor.getString(2));
            streets.add(street);
            cursor.moveToNext();
        }
        closeDatabase();
        return streets;
    }
    public int getCountStreet(int id_street){
        int kq=0;
        openDatabase();
        Cursor cursor =database.rawQuery("select count(ID) from bt_duong where IDDistrict='"+id_street+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            kq=cursor.getInt(0);
            cursor.moveToNext();
        }
        closeDatabase();
        return kq;
    }
}
