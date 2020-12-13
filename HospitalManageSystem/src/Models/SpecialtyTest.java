package Models;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SpecialtyTest {

    private static Specialty specialty;

    @BeforeClass
    public static void initTest(){
        specialty = new Specialty();
        specialty.setId(3);
        specialty.setName("Хирург");
    }


    @AfterClass
    public static void afterTest(){
        specialty = null;
    }

    @Test
    public void update() {
        Specialty specialty1 = new Specialty(1,"Терапевт");
        Specialty specialty2 = new Specialty(2,"Хирург");
        Specialty specialty3 = new Specialty(3,"Психиатр");


        ArrayList<Specialty> specialtyArrayList = new ArrayList<>();
        specialtyArrayList.add(specialty1);
        specialtyArrayList.add(specialty2);
        specialtyArrayList.add(specialty3);

        Specialty.update(specialtyArrayList);

        Assert.assertEquals(specialtyArrayList,Specialty.listSpecialties);
    }

    @Test
    public void getId() {
        assertEquals("testGetId",3, specialty.getId());
    }

    @Test
    public void getName() {
        assertEquals("testGetName","Хирург", specialty.getName());
    }

    @Test
    public void setId() {
        specialty.setId(1);
        assertEquals(1,specialty.getId());
    }

    @Test
    public void setName() {
        specialty.setName("Окулист");
        assertEquals("Окулист",specialty.getName());
    }
}