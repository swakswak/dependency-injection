package toy.swak.support;

import java.util.LinkedList;
import java.util.List;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
class Package {
    private final String name;
    private final List<Package> subPackages;
    private final List<ClassFile> classes;

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

    public List<ClassFile> getClasses() {
        return classes;
    }

    public void addSubPackage(String name) {
        subPackages.add(Package.of(name));
    }

    public void addClass(String name, boolean isComponent) {
        classes.add(new ClassFile(name, isComponent));
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
