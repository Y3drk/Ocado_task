package floydWarshallTests;

import org.junit.jupiter.api.Test;
import task.auxiliary.FloydWarshall;
import task.auxiliary.Vector2D;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FloydWarshallTests {
    public static double INFINITY = 99999;

    @Test
    public void checkPathsAndResults() {
        double[][] testGraph = {{0, 10, 3, INFINITY, INFINITY, INFINITY, INFINITY},
                {INFINITY, 0, INFINITY, INFINITY, INFINITY, 5, 1},
                {INFINITY, INFINITY, 0, INFINITY, 1, INFINITY, INFINITY},
                {INFINITY, INFINITY, INFINITY, 0, INFINITY, 8, INFINITY},
                {INFINITY, 4, INFINITY, 20, 0, INFINITY, INFINITY},
                {INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, 0, 16},
                {INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, INFINITY, 0}};

        FloydWarshall algorithm = new FloydWarshall(7, testGraph);

        algorithm.algorithm();

        double[][] results = algorithm.getDistances();

        //expected results
//        [0, 8, 3, 24, 4, 13, 9]
//        [inf, 0, inf, inf, inf, 5, 1]
//        [inf, 5, 0, 21, 1, 10, 6]
//        [inf, inf, inf, 0, inf, 8, 24]
//        [inf, 4, inf, 20, 0, 9, 5]
//        [inf, inf, inf, inf, inf, 0, 16]
//        [inf, inf, inf, inf, inf, inf, 0]

        //expected path -> (0,2) (0,4) (0,1) (0,6)

        List<Vector2D> testPath = algorithm.getPath(6,0,1,1);
        assertEquals(8,results[0][1]);
        assertEquals(16,results[5][6]);

        assertEquals(testPath.get(1).y, new Vector2D(0,4).y);
    }
}
