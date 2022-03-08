package toy.swak.dependency.injector;

import toy.swak.exceptions.DependencyInjectionException;
import toy.swak.exceptions.InstanceCreationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
class InstanceCreator {
    private final ComponentPool componentPool;
    private final ReferenceConverter referenceConverter;

    InstanceCreator() {
        this.componentPool = ComponentPool.getInstance();
        this.referenceConverter = ReferenceConverter.getInstance();
    }

    void createInstances(Iterable<Reference> references) {
        for (Reference reference : references) {
            this.createInstances(reference.getAdjacencyList());

            Class<?> aClass = referenceConverter.toClass(reference);
            this.validateComponentConstructorConstraint(aClass);
            Constructor<?> constructor = aClass.getDeclaredConstructors()[0];

            if (componentPool.containsKey(constructor.getName())) {
                continue;
            }

            List<Object> dependencies = this.getDependencies(reference);
            this.createInstance(constructor, dependencies);
        }
    }

    private void validateComponentConstructorConstraint(Class<?> aClass) {
        if (aClass.getDeclaredConstructors().length > 1) {
            throw new DependencyInjectionException();
        }
    }

    private List<Object> getDependencies(Reference reference) {
        List<Object> dependencies = new LinkedList<>();
        for (Reference adjacent : reference.getAdjacencyList()) {
            dependencies.add(componentPool.get(adjacent.getFullName()));
        }
        return dependencies;
    }

    private void createInstance(Constructor<?> constructor, List<Object> dependencies) {

        try {
            constructor.setAccessible(true);
            Object created = null;
            created = constructor.newInstance(dependencies.toArray());
            componentPool.put(constructor.getName(), created);
            constructor.setAccessible(false);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new InstanceCreationException(e.getMessage());
        }
    }
}
