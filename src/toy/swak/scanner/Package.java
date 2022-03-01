package toy.swak.scanner;

import toy.swak.validation.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
public class Package {
    private final String name;
    private final List<Package> subPackages;
    private final List<Class<?>> classes;

    private Package(String name) {
        this.name = name;
        this.subPackages = new LinkedList<>();
        this.classes = new LinkedList<>();
    }

    public static Package of(String name) {
        return new Package(name);
    }

    public String getName() {
        return name;
    }

    public List<Package> getSubPackages() {
        return subPackages;
    }

    public List<Class<?>> getClasses() {
        return classes;
    }

    public void addSubPackage(@NotNull(message = "Package cannot be null.") Package pkg) {
        subPackages.add(pkg);
    }

    public void addClass(@NotNull(message = "Class cannot be null.") Class<?> classFile) {
        classes.add(classFile);
    }

    @Override
    public String toString() {
        return "Package{" +
                "name='" + name + '\'' +
                ", packages=" + subPackages +
                ", classes=" + classes +
                '}';
    }
}
