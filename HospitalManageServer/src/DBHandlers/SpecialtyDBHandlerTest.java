package DBHandlers;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpecialtyDBHandlerTest {

    private static SpecialtyDBHandler specialtyDBHandler;
    private static String name;
    private static int idSpecialty;

    @BeforeClass
    public static void init(){
        specialtyDBHandler = new SpecialtyDBHandler();
        name = "Хирург";
        idSpecialty = 8;
    }

    @Test
    public void findRecordByID() {
        assertEquals("findRecordByID",name,
                specialtyDBHandler.findRecordByID(idSpecialty));
    }

    @Test
    public void findRecordByName() {
        assertEquals("findRecordByName",idSpecialty,
                specialtyDBHandler.findRecordByName(name));
    }
}