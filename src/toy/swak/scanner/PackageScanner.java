package toy.swak.scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public Package scan(String packagePath) {
        try (InputStream resourceStream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(this.toOSPathString(packagePath));
             InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(resourceStream));
             BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
            Stream<String> lines = bufferedReader.lines();
            Package extracted = this.extractCurrentPackage(packagePath);
            lines.forEach(line -> {
                if (this.isClass(line)) {
                    extracted.addClass(this.toClass(packagePath, line));
                } else {
                    extracted.addSubPackage(this.scan(packagePath + "." + line));
                }
            });

            return extracted;
        } catch (IOException e) {
            throw new IllegalArgumentException(packagePath);
        }
    }

    private String toOSPathString(String packagePath) {
        return packagePath.replace(".", "/");
    }

    private Package extractCurrentPackage(String packagePath) {
        String[] packages = packagePath.split("\\.");
        String currentPackageName = packages[packages.length - 1];

        return Package.of(currentPackageName);
    }

    private Class<?> toClass(String packagePath, String line) {
        try {
            String className = line.replace(".class", "");
            return Class.forName(packagePath + "." + className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("packagePath=" + packagePath + ", line=" + line);
        }
    }

    private boolean isClass(String line) {
        return line.endsWith(".class");
    }
}
