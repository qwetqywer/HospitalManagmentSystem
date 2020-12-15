package Models;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 1113799434508676095L;
    private int id;
    private Street street;
    private int flatNumber;
    private int houseNumber;
    private int corpus;

    public Address(Street street, int flatNumber, int houseNumber,
                   int corpus) {
        this.street = street;
        this.flatNumber = flatNumber;
        this.houseNumber = houseNumber;
        this.corpus = corpus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public int getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(int flatNumber) {
        this.flatNumber = flatNumber;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getCorpus() {
        return corpus;
    }

    public void setCorpus(int corpus) {
        this.corpus = corpus;
    }

}
