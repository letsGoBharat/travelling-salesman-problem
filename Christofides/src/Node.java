import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    /*
    Data structure that maintains the city information
     */

    private int key;
    private boolean vertex;
    private Node parent;
    private List<Node> children = new ArrayList<>();
    private int degree;
    private int index;
    private float xCoordinate;
    private float yCoordinate;

    public boolean isTraversed() {
        return traversed;
    }

    public void setTraversed(boolean traversed) {
        this.traversed = traversed;
    }

    private boolean traversed;

    public int getIndex() {
        return index;
    }

    public void incrementDegree() {
        this.degree++;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setVertex(boolean vertex) {
        this.vertex = vertex;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public int getKey() {
        return key;
    }

    public boolean isVertex() {
        return vertex;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public int getDegree() {
        return degree;
    }

    public Node(float xCoordinate, float yCoordinate) {

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public float getxCoordinate() {
        return xCoordinate;
    }


    public float getyCoordinate() {
        return yCoordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return index == node.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
