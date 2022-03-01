package toy.swak.dependency.injector;

import toy.swak.component.Component;
import toy.swak.scanner.Package;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
public class DependencyInjector {
    private final String topPackage;
    private final String componentName;
    private final Map<Class<?>, Set<Class<?>>> dependencyMap;
    private final Stack<Class<?>> dependencyStack;

    public DependencyInjector(String topPackage) {
        this.topPackage = topPackage;
        this.componentName = Component.class.getName();
        this.dependencyMap = new HashMap<>();
        this.dependencyStack = new Stack<>();
    }

    public void injectDependency(Package pkg) {
        this.scanAndAddKeyToDependencyMap(pkg);

        for (Class<?> component : dependencyMap.keySet()) {
            for (Field field : component.getDeclaredFields()) {
                if (dependencyMap.containsKey(field.getType())) {
                    dependencyMap.get(component).add(field.getType());
                }
            }
        }
        this.checkCircularReference();
        System.out.println(dependencyMap);

    }

    private void checkCircularReference() {
        Set<String> components = new LinkedHashSet<>();

        for (Map.Entry<Class<?>, Set<Class<?>>> entry : dependencyMap.entrySet()) {
            components.add(entry.getKey().getName());
            for (Class<?> dependency : entry.getValue()) {
                components.contains(dependency);

                if (this.isComponentAnnotationPresent(dependency)) {

                }
            }
        }
    }

    private void scanAndAddKeyToDependencyMap(Package pkg) {
        pkg.getClasses().stream()
                .filter(this::isComponentAnnotationPresent)
                .forEach(aClass -> dependencyMap.put(aClass, new HashSet<>()));

        for (Package subPackage : pkg.getSubPackages()) {
            this.scanAndAddKeyToDependencyMap(subPackage);
        }
    }

    private boolean isComponentAnnotationPresent(Class<?> aClass) {
        return aClass.isAnnotationPresent(Component.class);
    }

//    private boolean isComponent(Class<?> aClass) {
//        return Component.class.getSimpleName().equals(aClass.getSimpleName());
//    }
}
