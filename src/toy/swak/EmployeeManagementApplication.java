package toy.swak;

import toy.swak.dependency.injector.ConstructorDependencyInjector;
import toy.swak.dependency.injector.DependencyInjector;
import toy.swak.scanner.Package;
import toy.swak.scanner.PackageScanner;

public class EmployeeManagementApplication {
    private static final String TOP_PACKAGE = "toy";

    public static void main(String[] args) {
        PackageScanner packageScanner = PackageScanner.getInstance();
        Package scanned = packageScanner.scan(TOP_PACKAGE);

        DependencyInjector dependencyInjector = new ConstructorDependencyInjector();
        dependencyInjector.injectDependency(scanned);
    }
}
