package Models;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 1113799434508676095L;
    private int id;
    private Street street;
    private int idStreet;
    private int flatNumber;
    private int houseNumber;
    private int corpus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlatNumber() {
        return flatNumber;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public int getCorpus() {
        return corpus;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public int getIdStreet() {
        return idStreet;
    }

    public Address(int idStreet, int flatNumber, int houseNumber,
                   int corpus) {
        this.idStreet = idStreet;
        this.flatNumber = flatNumber;
        this.houseNumber = houseNumber;
        this.corpus = corpus;
    }

    public Address() {
    }

    public Street getStreet() {
        return street;
    }
}