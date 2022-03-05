package toy.swak.dependency.injector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
class ReferenceGraph {
    private final Map<String, Reference> references;

    ReferenceGraph() {
        this.references = new HashMap<>();
    }

    public void addReference(Reference reference) {
        this.references.put(reference.getFullName(), reference);
    }

    public void addEdgeIfExists(Reference from, Reference to) {
        if (this.contains(from.getFullName()) && this.contains(to.getFullName())) {
            this.addEdge(from, to);
        }
    }

    private void addEdge(Reference from, Reference to) {
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

    public Reference getOrNew(String childName) {
        return this.contains(childName) ? this.get(childName) : Reference.of(childName);
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
}
