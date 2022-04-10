package parsersTests;

import org.junit.jupiter.api.Test;
import task.parsers.GridParser;
import task.structures.Module;
import task.structures.ModuleType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GridParserTests {
    public static double INFINITY = 99999;

    @Test
    public void parsingCorrectnessTest(){
        String filename = "grid-test.txt";
        //test file available in resources

        GridParser gridTest = new GridParser(filename);

        Module[][] testModules = gridTest.getModules();

        //module type tests
        assertEquals(ModuleType.O, testModules[0][2].getType());
        assertEquals(ModuleType.H, testModules[2][2].getType());
        assertEquals(ModuleType.S, testModules[1][0].getType());
        assertEquals(ModuleType.B, testModules[0][1].getType());

        //Reaching containers tests
        assertEquals(10, testModules[2][2].getProductTime("P1"));
        assertEquals(4, testModules[2][1].getProductTime("P1"));
        assertEquals(2, testModules[1][0].getProductTime("P2"));
        assertEquals(INFINITY, testModules[0][0].getProductTime("P1"));

    }
}
