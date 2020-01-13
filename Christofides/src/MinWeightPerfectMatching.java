import java.util.Arrays;
import java.util.List;

public class MinWeightPerfectMatching {

    /*
    Using a Brute Force way of finding a match. Works for small inputs but to make it more efficient, we might have to implement the Blossom V Algorithm
     */

    public List<Node> getOddNodes() {
        return oddNodes;
    }

    public void setOddNodes(List<Node> oddNodes) {
        this.oddNodes = oddNodes;
    }

    List<Node> oddNodes; //nodes with odd degree

    int distanceGraph[][];

    public int[][] getDistanceGraph() {
        return distanceGraph;
    }

    public void setDistanceGraph(int[][] distanceGraph) {
        this.distanceGraph = distanceGraph;
    }

    public void runAlgorithm() {

        Node v;
        Node u;
        while(oddNodes.size()!=0 && oddNodes.size()!=1) {
            u  = oddNodes.get(0);
            Node closestVertex = null;
            int tempDistance;
            int distance = Integer.MAX_VALUE;
            for(int i=0;i<oddNodes.size();i++) {
                v = oddNodes.get(i);
                tempDistance = distanceGraph[u.getIndex()][v.getIndex()];
                if(u.getIndex()!=v.getIndex() && tempDistance<distance) {
                    closestVertex = v;
                    distance = tempDistance;
                }
            }

            if(closestVertex!=null) {
                oddNodes.remove(closestVertex);
                u.incrementDegree();
                closestVertex.incrementDegree();
                u.addChild(closestVertex);
                closestVertex.addChild(u);
            }
            oddNodes.remove(u);

        }
    }
}


