package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

public class Specialty implements Serializable {
    public static ObservableList<Specialty> listSpecialties =  FXCollections.observableArrayList();
    private static final long serialVersionUID = 1113799434508676095L;
    private int id;
    private String name;

    public Specialty(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Specialty() {

    }

    public static void update(ArrayList<Specialty> specialtiesArrayList) {
        listSpecialties.clear();
        listSpecialties.addAll(specialtiesArrayList);
    }

    @Override
    public String toString() {
        return  name;
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