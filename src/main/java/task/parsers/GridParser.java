package task.parsers;

import task.auxiliary.Vector2D;
import task.structures.Container;
import task.structures.Module;
import task.structures.ModuleType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Math.max;

public class GridParser {
    private Module[][] modules;
    private double[][] graph;
    final static double INFINITY = 99999;
    private int v;
    private int vx;
    private int vy;
    private int vn;

    public GridParser(String filename){
        try {
            File file = new File("src\\main\\resources\\" + filename);
            Scanner sc = new Scanner(file);

            int lineCounter = 0;

            while (sc.hasNextLine()){
                String input = sc.nextLine();

                if (lineCounter == 0){
                    String[] numbers = input.split(" ");
                    this.vx = Integer.parseInt(numbers[0]);
                    this.vy = Integer.parseInt(numbers[1]);
                    this.vn = Integer.parseInt(numbers[2]);

                    this.modules = new Module[vy][vx];

                } else if (lineCounter <= vy){
                    for (int i = 0; i < this.vx; i++){
                        switch (input.charAt(i)){
                            case 'H' -> {
                                modules[lineCounter-1][i] = new Module(ModuleType.H,new Vector2D(lineCounter-1, i));
                            }
                            case 'B' -> {
                                modules[lineCounter-1][i] = new Module(ModuleType.B,new Vector2D(lineCounter-1, i));
                            }

                            case 'S' -> {
                                modules[lineCounter-1][i] = new Module(ModuleType.S,new Vector2D(lineCounter-1, i));
                            }
                            default -> {
                                modules[lineCounter-1][i] = new Module(ModuleType.O,new Vector2D(lineCounter-1, i));
                            }
                        }
                    }
                } else {
                    String[] data = input.split(" ");
                    int x = Integer.parseInt(data[1]);
                    int y = Integer.parseInt(data[2]);
                    int n = Integer.parseInt(data[3]);
                    modules[y][x].addContainer(new Container(n, data[0]));
                }
                lineCounter++;
            }


        } catch (FileNotFoundException e) {
            System.err.println("File does not exist");
        }

        createGraph();
    }

    private void createGraph(){
        this.v = vx*vy;
        this.graph = new double[v][v];

        for (int j = 0; j < v; j++) {
            for (int k = 0; k < v; k++) {
                graph[j][k] = INFINITY;
            }
        }

        //insides
        for(int i = 1; i < vy-1; i++){
            for (int j = 1; j < vx-1; j++){
                if (modules[i][j].getType().equals(ModuleType.O)){
                    continue;
                }
                double current_time = modules[i][j].getTravelTime();
                graph[vx*i +j][vx*i + j] = 0;
                graph[vx*i +j][vx*i + j -1] = max(current_time,modules[i][j-1].getTravelTime());
                graph[vx*i +j][vx*(i - 1) + j] = max(current_time,modules[i-1][j].getTravelTime());
                graph[vx*i +j][vx*i + j + 1] = max(current_time,modules[i][j+1].getTravelTime());
                graph[vx*i +j][vx*(i + 1) + j] = max(current_time,modules[i+1][j].getTravelTime());
            }
        }
        //bottom and top border
        for (int i = 1; i < vx-1; i++) {
            if (!modules[0][i].getType().equals(ModuleType.O)) {
                double current_time = modules[0][i].getTravelTime();
                graph[i][i - 1] = max(current_time,modules[0][i - 1].getTravelTime());
                graph[i][i + 1] = max(current_time,modules[0][i + 1].getTravelTime());
                graph[i][i + vx] = max(current_time,modules[1][i].getTravelTime());
            }
            graph[i][i] = 0;

            if (!modules[vy - 1][i].getType().equals(ModuleType.O)) {
                double current_time = modules[vy-1][i].getTravelTime();
                graph[vx * (vy - 1) + i][vx * (vy - 1) + i - 1] = max(current_time,modules[vy - 1][i - 1].getTravelTime());
                graph[vx * (vy - 1) + i][vx * (vy - 1) + i + 1] = max(current_time,modules[vy - 1][i + 1].getTravelTime());
                graph[vx * (vy - 1) + i][i + vx * (vy - 2)] = max(current_time,modules[vy - 2][i].getTravelTime());
            }
            graph[vx * (vy - 1) + i][vx * (vy - 1) + i] = 0;
        }

        //left and right border
        for (int i = 1; i < vy-1; i++) {
            if (!modules[i][0].getType().equals(ModuleType.O)) {
                double current_time = modules[i][0].getTravelTime();
                graph[vx * i][vx * i + 1] = max(current_time,modules[i][1].getTravelTime());
                graph[vx * i][vx * (i - 1)] = max(current_time,modules[i - 1][0].getTravelTime());
                graph[vx * i][vx * (i + 1)] = max(current_time,modules[i + 1][0].getTravelTime());
            }
            graph[vx * i][vx * i] = 0;

            if (!modules[i][vx - 1].getType().equals(ModuleType.O)) {
                double current_time = modules[i][vx-1].getTravelTime();
                graph[vx * (i + 1) - 1][vx * (i + 1) - 2] = max(current_time,modules[i][vx - 1].getTravelTime());
                graph[vx * (i + 1) - 1][vx * i - 1] = max(current_time,modules[i - 1][vx - 1].getTravelTime());
            }
            graph[vx * (i + 1) - 1][vx * (i + 1) - 1] = 0;
        }

        //corners
        if (!modules[0][0].getType().equals(ModuleType.O)) {
            double current_time = modules[0][0].getTravelTime();
            graph[0][1] = max(current_time,modules[0][1].getTravelTime());
            graph[0][vx] = max(current_time,modules[1][0].getTravelTime());
        }
        graph[0][0] = 0;

        if (!modules[0][vx-1].getType().equals(ModuleType.O)) {
            double current_time = modules[0][vx-1].getTravelTime();
            graph[vx - 1][vx - 2] = max(current_time,modules[0][vx - 2].getTravelTime());
            graph[vx - 1][2 * vx - 1] = max(current_time,modules[1][vx - 1].getTravelTime());
        }
        graph[vx - 1][vx - 1] = 0;

        if (!modules[vy - 1][0].getType().equals(ModuleType.O)) {
            double current_time = modules[vy-1][0].getTravelTime();
            graph[vx * (vy - 1)][vx * (vy - 2)] = max(current_time,modules[vy - 2][0].getTravelTime());
            graph[vx * (vy - 1)][vx * (vy - 1) + 1] = max(current_time,modules[vy - 1][1].getTravelTime());
        }
        graph[vx * (vy - 1)][vx * (vy - 1)] = 0;

        if (!modules[vy - 1][vx-1].getType().equals(ModuleType.O)) {
            double current_time = modules[vy-1][vx-1].getTravelTime();
            graph[vx * vy - 1][vx * vy - 2] = max(current_time,modules[vy - 1][vx - 2].getTravelTime());
            graph[vx * vy - 1][vx * (vy - 1) - 1] = max(current_time,modules[vy - 2][vx - 1].getTravelTime());
        }
        graph[vx * vy - 1][vx * vy - 1] = 0;
    }

    public Module[][] getModules() {
        return modules;
    }

    public double[][] getGraph() {
        return graph;
    }

    public int getV() {
        return v;
    }

    public int getVx() {
        return vx;
    }

    public int getVy() {
        return vy;
    }

    public int getVn() {
        return vn;
    }
}
