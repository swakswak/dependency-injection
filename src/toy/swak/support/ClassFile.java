package toy.swak.support;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
class ClassFile {
    private final String name;
    private final boolean component;

    ClassFile(String name, boolean component) {
        this.name = name;
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public boolean isComponent() {
        return component;
    }

    @Override
    public String toString() {
        return "ClassFile{" +
                "name='" + name + '\'' +
                ", component=" + component +
                '}';
    }
}
