package toy.swak.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
public class PackageScanner {
    private static PackageScanner instance;

    private PackageScanner() {
    }

    public static synchronized PackageScanner getInstance() {
        if (instance == null) {
            return new PackageScanner();
        }
        return instance;
    }

    public void scan(String packagePath) {
        try (InputStream resourceStream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packagePath.replace(".", "/"));
             InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(resourceStream));
             BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
            String[] split = packagePath.split(".");
            for (String packageName : split) {
                Package pkg = Package.of(packageName);
            }

            Stream<String> lines = bufferedReader.lines();
            lines.forEach(line -> {
                if (this.isClass(line)) {
                    this.scanClasses(packagePath, line);
                } else {
                    this.scanPackages(packagePath, line);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scanPackages(String packagePath, String line) {
        System.out.println("- Package: " + line);
        scan(packagePath + "." + line);
    }

    private void scanClasses(String packagePath, String line) {
        try {
            System.out.println("- Class: " + line);
            String classPath = packagePath + "." + line.replace(".class", "");
            Class<?> klass = Class.forName(classPath);
//            System.out.println(klass.getPackage().getName().replace(packagePath, ""));
            for (Annotation annotation : klass.getAnnotations()) {
                System.out.println("\t- @" + annotation.annotationType().getSimpleName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean isClass(String line) {
        return line.endsWith(".class");
    }
}
