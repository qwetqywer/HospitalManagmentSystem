package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

public class Street implements Serializable {
    private static final long serialVersionUID = 1113799434508676095L;
    public static ObservableList<Street> listStreets =  FXCollections.observableArrayList();
    private int id;
    private String name;

    public static void update(ArrayList<Street> streetArrayList) {
        listStreets.clear();
        listStreets.addAll(streetArrayList);
    }

    @Override
    public String toString() {
        return  id + ". " + name;
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