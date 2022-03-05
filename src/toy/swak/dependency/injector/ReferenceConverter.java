package toy.swak.dependency.injector;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
class ReferenceConverter {
    private static ReferenceConverter instance;

    private ReferenceConverter() {
    }

    public static synchronized ReferenceConverter getInstance() {
        if (instance == null) {
            return new ReferenceConverter();
        }
        return instance;
    }

    public Class<?> toClass(Reference reference) {
        try {
            return Class.forName(reference.getFullName());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Class not found: " + reference.getFullName());
        }
    }
}
