package toy.swak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.stream.Stream;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
class ClassScanner {
    public static void scan(String packagePath) throws IOException {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packagePath.replace(".", "/"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        Stream<String> lines = bufferedReader.lines();
        lines.forEach(line -> {
            if (!line.endsWith(".class")) {
                try {
                    System.out.println("package: " + line);
                    scan(packagePath + "." + line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("class: " + line);
                try {
                    Class<?> klass = Class.forName(packagePath + "." + line.replace(".class", ""));
                    for (Annotation annotation : klass.getAnnotations()) {
                        System.out.println(annotation.annotationType().getName());
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
