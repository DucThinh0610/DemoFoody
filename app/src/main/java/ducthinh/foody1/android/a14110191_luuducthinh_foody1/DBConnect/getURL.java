package ducthinh.foody1.android.a14110191_luuducthinh_foody1.DBConnect;

/**
 * Created by Test on 5/13/2017.
 */

public class getURL {//hàm này có chức năng lọc dữ liệu theo các biến khác nhau
    public String getURLofRes(int prePost,int idCity,int idDistrict, int idStreet, int idCate,boolean loadNew){
        String url="";
        String baseURL="http://ducthinh0610.somee.com/RestfullFinal/";
        if (loadNew==true){
            if (idCate!=-1){//load dữ liệu theo quán mới nhất + lọc theo danh mục
                if (idStreet!=-1){//load mới nhất + danh mục + street
                    return url="api/Restaurant?idNStr="+idStreet+"&idCate="+idCate+"&prePost="+prePost+"";
                }
                if (idDistrict!=-1){//load theo mới nhất + danh mục + district
                    return url="api/Restaurant?idNDis="+idDistrict+"&idCate="+idCate+"&prePost="+prePost+"";
                }
                if (idCity!=-1){//load theo mới nhất + danh mục +city
                    return url="api/Restaurant?idNCity="+idCity+"&idCate="+idCate+"&prePost="+prePost+"";
                }
            }
            else {//load mới nhất không theo danh mục
                if (idStreet!=-1){//load mới nhất+ street
                    return url="api/Restaurant?idNStr="+idStreet+"&prePost="+prePost+"";
                }
                if (idDistrict!=-1){//load theo mới nhất + district
                    return url="api/Restaurant?idNDis="+idDistrict+"&prePost="+prePost+"";
                }
                if (idCity!=-1){//load theo mới nhất +city
                    return url="api/Restaurant?idNCity="+idCity+"&prePost="+prePost+"";
                }
            }
        }
        else {
            if (idCate!=-1){//load lọc theo danh mục
                if (idStreet!=-1){//load  danh mục + street
                    return url="api/Restaurant?idStr="+idCity+"&idCate="+idCate+"&prePost="+prePost+"";
                }
                if (idDistrict!=-1){//load  danh mục + district
                    return url="api/Restaurant?idDis="+idCity+"&idCate="+idCate+"&prePost="+prePost+"";
                }
                if (idCity!=-1){//load  danh mục +city
                    return url="api/Restaurant?idCity="+idCity+"&idCate="+idCate+"&prePost="+prePost+"";
                }
            }
            else {//load mới nhất không theo danh mục
                if (idStreet!=-1){//load  street
                    return url="api/Restaurant?idStr="+idStreet+"&prePost="+prePost+"";
                }
                if (idDistrict!=-1){//load theo  district
                    return url="api/Restaurant?idDis="+idDistrict+"&prePost="+prePost+"";
                }
                if (idCity!=-1){//load theo city
                    return url="api/Restaurant?idCity="+idCity+"&prePost="+prePost+"";
                }
            }
        }
        return url;
    }
}
