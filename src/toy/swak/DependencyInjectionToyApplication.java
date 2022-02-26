package toy.swak;

public class DependencyInjectionToyApplication {
    private static final String BASE_PACKAGE = "toy.swak";

    public static void main(String[] args) {
        PackageScanner packageScanner = PackageScanner.getInstance();
        packageScanner.scan(BASE_PACKAGE);
    }
}
