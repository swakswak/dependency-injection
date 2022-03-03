package toy.swak.dependency.injector;

import java.util.LinkedList;
import java.util.List;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
public class Reference {
    private final String fullName;
    private final List<Reference> adjacencyList;
    private boolean visited;
    private boolean beingVisited;

    private Reference(String name) {
        this.fullName = name;
        this.adjacencyList = new LinkedList<>();
    }

    public static Reference of(String name) {
        return new Reference(name);
    }

    public void addNeighbor(Reference adjacent) {
        this.adjacencyList.add(adjacent);
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isBeingVisited() {
        return beingVisited;
    }

    public void setBeingVisited(boolean beingVisited) {
        this.beingVisited = beingVisited;
    }

    public List<Reference> getAdjacencyList() {
        return adjacencyList;
    }

    public boolean contains(Reference neighbor) {
        return adjacencyList.stream().anyMatch(reference -> reference.equals(neighbor));
    }

    public boolean contains(String referenceName) {
        return adjacencyList.stream().anyMatch(reference -> reference.getFullName().equals(referenceName));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Reference) {
            return this.fullName.equals(((Reference) o).fullName);
        }
        return false;
    }
}
