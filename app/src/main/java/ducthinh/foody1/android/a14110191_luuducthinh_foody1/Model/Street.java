package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model;

/**
 * Created by Test on 4/3/2017.
 */

public class Street {
    private int idStreet;
    private int idDistrict;
    private String nameStreet;

    public int getIdStreet() {
        return idStreet;
    }

    public void setIdStreet(int idStreet) {
        this.idStreet = idStreet;
    }

    public int getIdDistrict() {
        return idDistrict;
    }

    public void setIdDistrict(int idDistrict) {
        this.idDistrict = idDistrict;
    }

    public String getNameStreet() {
        return nameStreet;
    }

    public void setNameStreet(String nameStreet) {
        this.nameStreet = nameStreet;
    }

    public Street(int idStreet, int idDistrict, String nameStreet) {

        this.idStreet = idStreet;
        this.idDistrict = idDistrict;
        this.nameStreet = nameStreet;
    }

    public Street() {

    }
}
