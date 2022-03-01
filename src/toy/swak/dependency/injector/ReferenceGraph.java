package toy.swak.dependency.injector;

import java.util.*;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
class ReferenceGraph {
    private final Map<String, Reference> references;

    public ReferenceGraph() {
        this.references = new HashMap<>();
    }

    public void addReference(Reference reference) {
        this.references.put(reference.getFullName(), reference);
    }

    public void addEdge(Reference from, Reference to) {
        if (references.containsKey(from.getFullName())) {
            references.get(from.getFullName()).addNeighbor(to);
        }
    }

    public Iterable<Reference> getReferences() {
        List<Reference> referenceList = new LinkedList<>();
        for (Map.Entry<String, Reference> entry : references.entrySet()) {
            referenceList.add(entry.getValue());
        }

        return referenceList;
    }

    public boolean hasCycle() {
        for (Reference reference : this.getReferences()) {
            if (!reference.isVisited() && hasCycle(reference)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasCycle(Reference reference) {
        reference.setBeingVisited(true);

        for (Reference neighbor : reference.getAdjacencyList()) {
            if (neighbor.isBeingVisited()) {
                return true;
            } else if (!neighbor.isBeingVisited() && this.hasCycle(neighbor)) {
                return true;
            }
        }

        reference.setBeingVisited(false);
        reference.setVisited(true);

        return false;
    }

    @Override
    public String toString() {
        return "ReferenceGraph{" +
                "references=" + references +
                '}';
    }

    public Reference get(String name) {
        if (!references.containsKey(name)) {
            throw new IllegalArgumentException(name);
        }

        return references.get(name);
    }

    public boolean contains(String name) {
        return references.containsKey(name);
    }
}
