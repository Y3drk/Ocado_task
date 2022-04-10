package parsersTests;

import org.junit.jupiter.api.Test;
import task.auxiliary.Vector2D;
import task.parsers.JobParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobParserTests {

    @Test
    public void parsingCorrectnessTest(){
        String filename = "job-test.txt";
        //test file available in resources

        JobParser jobTest = new JobParser(filename);

        assertEquals(new Vector2D(0,0).x, jobTest.getStartingPoint().x);
        assertEquals(new Vector2D(0,0).y, jobTest.getStartingPoint().y);

        assertEquals(new Vector2D(1,2).x, jobTest.getFinishPoint().x);
        assertEquals(new Vector2D(1,2).y, jobTest.getFinishPoint().y);

        assertEquals("P1", jobTest.getProductType());
    }
}
