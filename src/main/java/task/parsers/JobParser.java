package task.parsers;

import task.auxiliary.Vector2D;
import task.structures.Container;
import task.structures.Module;
import task.structures.ModuleType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JobParser {
    private Vector2D startingPoint;
    private Vector2D finishPoint;
    private String productType;

    public JobParser(String filename){
        try {
            File file = new File("src\\main\\resources\\" + filename);
            Scanner sc = new Scanner(file);

            int lineCounter = 0;

            while (sc.hasNextLine()){
                String input = sc.nextLine();
                String[] data = input.split(" ");
                switch (lineCounter){
                    case 0 -> {
                        this.startingPoint = new Vector2D(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                    }
                    case 1 -> {
                        this.finishPoint = new Vector2D(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                    }
                    default -> {
                        this.productType = data[0];
                    }
                }
                lineCounter++;
            }


        } catch (FileNotFoundException e) {
            System.err.println("File does not exist");
        }
    }

    public Vector2D getStartingPoint() {
        return startingPoint;
    }

    public Vector2D getFinishPoint() {
        return finishPoint;
    }

    public String getProductType() {
        return productType;
    }
}
