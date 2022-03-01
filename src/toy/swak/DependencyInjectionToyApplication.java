package toy.swak;

import toy.swak.dependency.injector.DependencyInjector;
import toy.swak.scanner.Package;
import toy.swak.scanner.PackageScanner;

public class DependencyInjectionToyApplication {
    private static final String TOP_PACKAGE = "toy";

    public static void main(String[] args) {
        PackageScanner packageScanner = PackageScanner.getInstance();
        Package scanned = packageScanner.scan(TOP_PACKAGE);

        DependencyInjector dependencyInjector = new DependencyInjector(TOP_PACKAGE);
        dependencyInjector.injectDependency(scanned);
//        print(scanned, "");
    }

    public static void print(Package scanned, String parent) {
        System.out.println(parent + "." + scanned.getName());
        for (Class<?> aClass : scanned.getClasses()) {
            System.out.println("\t" + aClass);
        }
        for (Package subPackage : scanned.getSubPackages()) {
            print(subPackage, parent + "." + scanned.getName());
        }

    }
}
