package toy.swak.dependency.injector;

import toy.swak.component.Component;
import toy.swak.exceptions.CircularReferenceException;
import toy.swak.scanner.Package;

import java.lang.reflect.Field;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
public class ConstructorDependencyInjector implements DependencyInjector{
    private final ReferenceGraph referenceGraph;
    private final InstanceCreator instanceCreator;
    private final ReferenceConverter referenceConverter;

    public ConstructorDependencyInjector() {
        this.instanceCreator = new InstanceCreator();
        this.referenceGraph = new ReferenceGraph();
        this.referenceConverter = ReferenceConverter.getInstance();
    }

    @Override
    public void injectDependency(Package pkg) {
        this.fillReferences(pkg);
        this.fillReferencesOfReferences();

        if (referenceGraph.hasCycle()) {
            throw new CircularReferenceException();
        }
        instanceCreator.createInstances(referenceGraph.getReferences());
        System.out.println(ComponentPool.getInstance());
    }


    private void fillReferencesOfReferences() {
        for (Reference reference : referenceGraph.getReferences()) {
            Class<?> aClass = referenceConverter.toClass(reference);
            this.connectReferences(aClass, aClass.getDeclaredFields());
        }
    }

    private void connectReferences(Class<?> component, Field[] dependencies) {
        for (Field child : dependencies) {
            checkAndAdd(component, child);
        }
    }

    private void checkAndAdd(Class<?> component, Field childField) {
        Class<?> child = childField.getType();

        if (this.isComponentAnnotationPresent(child)) {
            referenceGraph.addEdgeIfExists(referenceGraph.get(component.getName()), referenceGraph.getOrNew(child.getName()));
            this.connectReferences(child, child.getDeclaredFields());
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
