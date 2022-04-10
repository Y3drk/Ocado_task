package task.structures;

import task.auxiliary.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Module {
    public static double INFINITY = 99999;
    private final ModuleType type;
    private final Vector2D position;
    private final double travelTime;
    private List<Container> containers;


    public Module(ModuleType type, Vector2D position){
        containers = new ArrayList<>();
        this.type = type;
        this.position = position;
        this.travelTime = setTravelTime(type);
    }

    public ModuleType getType() {
        return type;
    }

    public Vector2D getPosition() {
        return position;
    }

    public double getTravelTime() {
        return travelTime;
    }

    public void addContainer(Container container){
        containers.add(container);
    }

    private double setTravelTime(ModuleType type){
        switch(type){
            case H -> {return 0.5;}
            case B -> {return 1;}
            case S -> {return 2;}
            default -> {return INFINITY;}
        }
    }

    private double countTime(int n){
        switch(this.type){
            case H -> {return 3*n + 4;}
            case B -> {return 2*n + 2;}
            case S -> {return n + 1;}
            default -> {return INFINITY;}
        }
    }

    public double getProductTime(String product){
            double minimalTime = INFINITY;
            for (Container container: containers){
                if (container.getProduct().equals(product)){
                    double newTime = countTime(container.getDepth());
                    if (minimalTime > newTime){
                        minimalTime = newTime;
                    }
                }
            }
        return minimalTime;
    }
}
