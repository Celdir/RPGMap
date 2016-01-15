import simplevoronoi.*;
import pcg.Pcg32;

import java.util.List;

public class RPGMap {
    private double width;
    private double height;
    public List<Tile> tiles;

    public RPGMap(int numTiles, double width, double height) {
        this.width = width;
        this.height = height;
        generateTiles(numTiles);
        smoothPointDistribution(2);
        assignBiomes();
    }

    private void generateTiles(int numTiles) {
        double[] xVals = new double[numTiles];
        double[] yVals = new double[numTiles];
        Pcg32 r = new Pcg32();
        for (int i = 0; i < xVals.length; i++) {
            xVals[i] = r.nextDouble(width);
            yVals[i] = r.nextDouble(height);
        }
        Voronoi v = new Voronoi(0.00001f);

        tiles = v.generateVoronoi(xVals, yVals, 0, width, 0, height);
    }

    private void smoothPointDistribution(int smoothness) {
        for (int i = 0; i < smoothness; i++) {
            double[] xVals = new double[tiles.size()];
            double[] yVals = new double[tiles.size()];
            
            for (int j = 0; j < tiles.size(); j++) {
                double xSum = 0;
                double ySum = 0;
                for (GraphEdge e : tiles.get(j).getEdges()) {
                    xSum += e.x1; // only use x1 and y1 so we don't double count the points
                    ySum += e.y1;
                }
                xVals[j] = xSum / tiles.get(j).getEdges().size();
                yVals[j] = ySum / tiles.get(j).getEdges().size();
            }
            Voronoi v = new Voronoi(0.00001f);

            tiles = v.generateVoronoi(xVals, yVals, 0, width, 0, height);
        }
    }

    private void assignBiomes() {
        
    }
}
