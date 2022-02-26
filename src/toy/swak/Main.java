package toy.swak;

import java.io.*;
import java.util.stream.Stream;

public class Main {
    private static final String BASE_PACKAGE = "toy.swak";

    public static void main(String[] args) throws IOException {
        ClassScanner.scan(BASE_PACKAGE);
    }

//    private static void scan(String packagePath) throws IOException {
//
//        InputStream resourceAsStream = ClassLoader.getSystemClassLoader()
//                .getResourceAsStream(packagePath.replace(".", "/"));
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
//        Stream<String> lines = bufferedReader.lines();
//        lines.forEach(line -> {
//            if (!line.endsWith(".class")) {
//                try {
//                    System.out.println("package: " + line);
//                    scan(packagePath + "." + line);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                System.out.println("class: " + line);
//            }
//        });
//    }
}
