package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Presentation;

import android.widget.FrameLayout;

import java.util.List;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.AddressOfFood;

/**
 * Created by Test on 4/8/2017.
 */

public interface IPreAddressOfFood {
    List<AddressOfFood> getListOfFoodByCityLoadMore(int prePost, int idCity, FrameLayout fameprogress);
    List<AddressOfFood> getListOfFoodByStreetLoadMore(int prePost, int idCity, String phuong, FrameLayout fameprogress);
    List<AddressOfFood> getListOfFoodByDistrictLoadMore(int prePost, int idDistrict, FrameLayout fameprogress);
    List<AddressOfFood> getListOfFoodByCityCategoryLoadMore(int prePost, int idCity, int idCategoty, FrameLayout fameprogress);
    List<AddressOfFood> getListOfFoodByDistrictCategoryLoadMore(int prePost, int idDistrict, int idCategory, FrameLayout fameprogress);
    List<AddressOfFood> getListOfFoodByStreetCategoryLoadMore(int prePost, int idCity, int idCategory, String phuong, FrameLayout fameprogress);
}
