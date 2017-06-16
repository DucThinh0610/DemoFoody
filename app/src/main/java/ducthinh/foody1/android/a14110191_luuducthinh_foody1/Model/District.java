package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model;

/**
 * Created by Test on 4/2/2017.
 */

public class District {
    private int id;
    private String name;
    private int idTP;

    public District() {
    }

    public District(int id, String name, int idTP) {
        this.id = id;
        this.name = name;
        this.idTP = idTP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdTP() {
        return idTP;
    }

    public void setIdTP(int idTP) {
        this.idTP = idTP;
    }
}
