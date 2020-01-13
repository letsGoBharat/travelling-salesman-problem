import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NearestNeighbour {


    private int graph[][];

    boolean[] used;

    public int[][] getGraph() {
        return graph;
    }

    public void setGraph(int[][] graph) {
        this.graph = graph;
    }

    public List<Integer> getTour() {
        return tour;
    }

    List<Integer> tour;

    public void runAlgorithm() {

        tour = new ArrayList<>(graph.length);
        used = new boolean[graph.length];
        int index = 0;

        do {
            tour.add(index);
            used[index]=true;
            index = findNextNeighbour(index);
        } while(index!=-1);


    }

    public int findNextNeighbour(int index) {

        int bestIndex = -1;
        int minDistance =  Integer.MAX_VALUE;;
        int tempDistance;

            for(int j=0;j<graph.length;j++) {
                tempDistance = graph[index][j];
                if(index!=j && !used[j] && tempDistance<minDistance) {
                    minDistance = tempDistance;
                    bestIndex = j;
                }
            }
            return bestIndex;
        }

    }

