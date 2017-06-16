package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Presentation;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.AddressOfFood;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.getAddressOfFood;

import static ducthinh.foody1.android.a14110191_luuducthinh_foody1.DBConnect.DBConnect.database;

/**
 * Created by Test on 4/8/2017.
 */

public class PreAddressOfFood implements IPreAddressOfFood {//tạo 1 phương thức để lấy data từ csdl theo mục đích nhất định, lọc dữ liệu theo các biến
    private static final int LOAD_BY_STREET=2;
    private static final int LOAD_BY_CITY=1;
    private static final int LOAD_BY_DISTRICT=3;
    private static final int LOAD_BY_STREETCATEGORY=6;
    private static final int LOAD_BY_CITYCATEGORY=4;
    private static final int LOAD_BY_DISTRICTCATEGORY=5;
    getAddressOfFood getAdd;
    Context context;

    public PreAddressOfFood(Context context) {
        this.context = context;
        getAdd=new getAddressOfFood();
    }

    @Override
    public List<AddressOfFood> getListOfFoodByCityLoadMore(int prePost, int idCity, FrameLayout fameprogress) {
        return getAddressOfFoodsLoad(prePost,0,idCity,0,"",fameprogress,LOAD_BY_CITY);
    }


    @Override
    public List<AddressOfFood> getListOfFoodByStreetLoadMore(int prePost, int idCity, String phuong, FrameLayout fameprogress) {
        return getAddressOfFoodsLoad(prePost,0,idCity,0,phuong,fameprogress,LOAD_BY_STREET);
    }


    @Override
    public List<AddressOfFood> getListOfFoodByDistrictLoadMore(int prePost, int idDistrict, FrameLayout fameprogress) {
        return getAddressOfFoodsLoad(prePost,0,0,idDistrict,"",fameprogress,LOAD_BY_DISTRICT);
    }

    @Override
    public List<AddressOfFood> getListOfFoodByCityCategoryLoadMore(int prePost, int idCity, int idCategoty, FrameLayout fameprogress) {
        return getAddressOfFoodsLoad(prePost,idCategoty,idCity,0,"",fameprogress,LOAD_BY_CITYCATEGORY);
    }

    @Override
    public List<AddressOfFood> getListOfFoodByDistrictCategoryLoadMore(int prePost, int idDistrict, int idCategory, FrameLayout fameprogress) {
        return getAddressOfFoodsLoad(prePost,idCategory,0,idDistrict,"",fameprogress,LOAD_BY_DISTRICTCATEGORY);
    }

    @Override
    public List<AddressOfFood> getListOfFoodByStreetCategoryLoadMore(int prePost, int idCity, int idCategory, String phuong, FrameLayout fameprogress) {
        return getAddressOfFoodsLoad(prePost,idCategory,idCity,0,phuong,fameprogress,LOAD_BY_STREETCATEGORY);
    }
    //hàm này sẽ gọi lại các phương thức get dữ liệu đã tạo trước đó
    private List<AddressOfFood> getAddressOfFoodsLoad(int prePost,int idCategory, int idCity,int idDistrict, String phuong, final FrameLayout frameProgress, int loadby){
        List<AddressOfFood>addressOfFoods;
        if (loadby==LOAD_BY_CITY){
            addressOfFoods=getAdd.getAddressOfFoodByCity(database,prePost,idCity);
        }
        else if (loadby==LOAD_BY_STREET){
            addressOfFoods=getAdd.getAddressOfFoodByStreet(database,prePost,idCity,phuong);
        }
        else if(loadby==LOAD_BY_DISTRICT) {
            addressOfFoods=getAdd.getAddressOfFoodByDistrict(database,prePost,idDistrict);
        }
        else if (loadby==LOAD_BY_CITYCATEGORY){
            addressOfFoods=getAdd.getAODByCategoryCity(database,prePost,idCity,idCategory);
        }
        else if (loadby==LOAD_BY_DISTRICTCATEGORY){
            addressOfFoods=getAdd.getAODByCategoryDistric(database,prePost,idDistrict,idCategory);
        }
        else {
            addressOfFoods=getAdd.getAODByCategoryPhuong(database,prePost,idCity,idCategory,phuong);
        }
        if(!addressOfFoods.isEmpty()){//tạo ra 1 framlayout và framlayout này sẽ hiện lên khi mà bắt đầu get data từ csdl
            frameProgress.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    frameProgress.setVisibility(View.GONE);
                }
            }, 3000);
        }
        return addressOfFoods;
    }
}
