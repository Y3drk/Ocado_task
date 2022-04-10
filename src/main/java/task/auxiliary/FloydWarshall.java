package task.auxiliary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Double.compare;

public class FloydWarshall {
    int v;
    final static double INFINITY = 99999;
    private final double[][] graph;
    private double[][] distances;
    private int[][] parents;

    public FloydWarshall(int vertices, double graph[][]){
        this.graph = graph;
        v = vertices;
    }

    public void algorithm(){
        distances = new double[v][v];
        parents = new int[v][v];

        for (int r = 0; r < this.v; r++){
            for (int c = 0; c < this.v; c++ ){
                distances[r][c] = this.graph[r][c];
                if (compare(this.graph[r][c], INFINITY) != 0 && r != c){
                    parents[r][c] = r;
                } else parents[r][c] = -1;
            }
        }

        for (int t = 0; t < this.v; t++){
            for (int u = 0; u < this.v; u++)
            {
                for (int v = 0; v < this.v; v++)
                {
                    if (distances[u][v] > distances[u][t] + distances[t][v]) {
                        distances[u][v] = distances[u][t] + distances[t][v];
                        parents[u][v] = parents[t][v];
                    }
                }
            }
        }
    }

    public double[][] getDistances() {
        return distances;
    }

    public List<Vector2D> getPath(int start, int stop, int vx, int vy){
        List<Vector2D> path = new ArrayList<>();
        int curr = start;
        while (curr != stop){
            path.add(new Vector2D(curr%vx,curr/vx));
            curr = this.parents[stop][curr];
        }
        Collections.reverse(path);
        return path;
    }
}
