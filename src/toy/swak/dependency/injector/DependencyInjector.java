package toy.swak.dependency.injector;

import toy.swak.component.Component;
import toy.swak.exceptions.CircularReferenceException;
import toy.swak.scanner.Package;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
public class DependencyInjector {
    private final ReferenceGraph referenceGraph;
    private final Map<String, Object> dependencyMap;

    public DependencyInjector() {
        this.referenceGraph = new ReferenceGraph();
        this.dependencyMap = new ConcurrentHashMap<>();
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

        this.newInstance(referenceGraph.getReferences());
    }

    // TODO: STUB -> 구현
    private void newInstance(Iterable<Reference> references) {
        for (Reference reference : references) {

            Class<?> aClass = this.toClass(reference);

            if (reference.getAdjacencyList().isEmpty()) {
                try {
                    Object created = aClass.getDeclaredConstructor().newInstance();
                    dependencyMap.put(reference.getFullName(), created);
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                this.newInstance(reference.getAdjacencyList());

                try {
                    Object created = aClass.getDeclaredConstructor().newInstance();
                    dependencyMap.put(reference.getFullName(), created);
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            try {
                for (Reference reference1 : reference.getAdjacencyList()) {

                    aClass.getDeclaredConstructor().newInstance();
                }

            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
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
