package task;

import task.auxiliary.FloydWarshall;
import task.auxiliary.Vector2D;
import task.parsers.GridParser;
import task.parsers.JobParser;
import task.structures.Module;

import java.util.Collections;
import java.util.List;

public class solver {
    static double INFINITY = 99999;
    public static void main(String[] args) {

        GridParser gridData = new GridParser(args[0]);
        JobParser jobData = new JobParser(args[1]);

        FloydWarshall algorithmData = new FloydWarshall(gridData.getV(), gridData.getGraph());

        algorithmData.algorithm();


        Module[][] modules = gridData.getModules();
        String product = jobData.getProductType();

        double[][] results = new double[gridData.getVy()][gridData.getVx()];
        double[][] FWData = algorithmData.getDistances();


        int startingPoint = gridData.getVx() * jobData.getStartingPoint().y + jobData.getStartingPoint().x;
        int finishingPoint = gridData.getVx() * jobData.getFinishPoint().y + jobData.getFinishPoint().x;


        for (int i = 0; i < gridData.getVy(); i++) {
            for (int j = 0; j < gridData.getVx(); j++) {
                double reachingTime = modules[i][j].getProductTime(product);
                if (reachingTime != INFINITY) {
                    results[i][j] = FWData[gridData.getVx() * i + j][startingPoint] + reachingTime + FWData[gridData.getVx() * i + j][finishingPoint];
                } else results[i][j] = INFINITY;
            }
        }


        int BestReachPlaceX = 0;
        int BestReachPlaceY = 0;
        double BestResult = INFINITY;

        for (int i = 0; i < gridData.getVy(); i++) {
            for (int j = 0; j < gridData.getVx(); j++) {
                if (results[i][j] < BestResult) {
                    BestResult = results[i][j];
                    BestReachPlaceY = i;
                    BestReachPlaceX = j;
                }
            }
        }

        List<Vector2D> firstStagePath = algorithmData.getPath(startingPoint, gridData.getVx() * BestReachPlaceY + BestReachPlaceX,gridData.getVx(), gridData.getVy());
        List<Vector2D> secondStagePath = algorithmData.getPath(finishingPoint,gridData.getVx() * BestReachPlaceY + BestReachPlaceX, gridData.getVx(), gridData.getVy());
        Collections.reverse(firstStagePath);
        firstStagePath.add(new Vector2D(BestReachPlaceX,BestReachPlaceY));

        System.out.println(firstStagePath.size() +secondStagePath.size()-1);
        System.out.println(BestResult);
        for (Vector2D vector2D : firstStagePath) {
            System.out.println(vector2D.toString());
        }
        for (Vector2D vector2D : secondStagePath) {
            System.out.println(vector2D.toString());
        }
    }
}
