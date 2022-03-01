package toy.swak.dependency.injector;

import toy.swak.component.Component;
import toy.swak.exceptions.CircularReferenceException;
import toy.swak.scanner.Package;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
public class DependencyInjector {
    private final ReferenceGraph referenceGraph;

    public DependencyInjector() {
        this.referenceGraph = new ReferenceGraph();
    }

    // TODO: STUB -> 구현
    public void injectDependency(Package pkg) {
        this.fillReferences(pkg);
        this.fillReferencesOfReferences();

        if (referenceGraph.hasCycle()) {
            throw new CircularReferenceException();
        }

        for (Reference reference : referenceGraph.getReferences()) {

        }

    }

    // TODO: STUB -> 구현
    private void newInstance(Iterable<Reference> references) {
        for (Reference reference : references) {
            this.newInstance(reference.getAdjacencyList());
            Class<?> aClass = this.toClass(reference);
        }
    }

    private void fillReferencesOfReferences() {
        for (Reference reference : referenceGraph.getReferences()) {
            Class<?> aClass = this.toClass(reference);
            this.connectReferences(aClass, aClass.getDeclaredFields());
        }
    }

    private void connectReferences(Class<?> component, Field[] dependencies) {
        for (Field child : dependencies) {
            checkAndAdd(component, child);
        }
    }

    private void checkAndAdd(Class<?> component, Field child) {
        if (this.isComponentAnnotationPresent(child.getType())) {
            if (referenceGraph.contains(component.getName())) {
                Reference reference = referenceGraph.get(component.getName());
                if (reference.contains(child.getType().getName())) {
                    return;
                }

                referenceGraph.addEdge(
                        referenceGraph.get(component.getName()),
                        referenceGraph.contains(child.getType().getName()) ?
                                referenceGraph.get(child.getType().getName())
                                : Reference.of(child.getType().getName())
                );
            }

            this.connectReferences(child.getType(), child.getType().getDeclaredFields());
        }
    }

    private Class<?> toClass(Reference reference) {
        try {
            return Class.forName(reference.getFullName());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Class not found: " + reference.getFullName());
        }
    }

    private void fillReferences(Package pkg) {
        pkg.getClasses().stream()
                .filter(this::isComponentAnnotationPresent)
                .forEach(aClass -> referenceGraph.addReference(Reference.of(aClass.getName())));

        for (Package subPackage : pkg.getSubPackages()) {
            this.fillReferences(subPackage);
        }
    }

    private boolean isComponentAnnotationPresent(Class<?> aClass) {
        return aClass.isAnnotationPresent(Component.class);
    }
}
