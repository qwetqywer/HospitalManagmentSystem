package Models;



import java.io.Serializable;
import java.util.ArrayList;

public class Street implements Serializable {
    private static final long serialVersionUID = 1113799434508676095L;
    private int id = 0;
    private String name = "";
    public static ArrayList<Street> listStreets = new ArrayList<Street>();

    public Street(Street street) {
        this.id = street.id;
        this.name = street.name;
    }

    public Street() {

    }

    public Street(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}