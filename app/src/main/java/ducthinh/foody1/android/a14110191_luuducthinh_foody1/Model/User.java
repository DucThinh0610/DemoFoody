package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model;

/**
 * Created by Test on 5/23/2017.
 */

public class User {
    String email,password,disName;
    boolean islogin;

    public User(String email, String password, String disName, boolean islogin) {
        this.email = email;
        this.password = password;
        this.disName = disName;
        this.islogin = islogin;
    }

    public boolean islogin() {
        return islogin;
    }

    public void setIslogin(boolean islogin) {
        this.islogin = islogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisName() {
        return disName;
    }

    public void setDisName(String disName) {
        this.disName = disName;
    }


    public User() {

    }
}
