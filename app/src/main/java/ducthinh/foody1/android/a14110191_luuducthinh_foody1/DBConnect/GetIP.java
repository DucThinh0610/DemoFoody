package ducthinh.foody1.android.a14110191_luuducthinh_foody1.DBConnect;

/**
 * Created by Test on 5/23/2017.
 */

public class GetIP {//hàm này để trả về địa chỉ mặc định của webservices
    public String getIP(int chose){
        if (chose==1){//localhost
            return "http://192.168.43.24/RestfullFinal/";
        }
        else if(chose==2){//hình ảnh
            return "http://192.168.43.24/RestfullFinal/img/";
        }
        else {//host
            return "http://foodyfinal.somee.com//RestfullFinal/";
        }
    }
}
