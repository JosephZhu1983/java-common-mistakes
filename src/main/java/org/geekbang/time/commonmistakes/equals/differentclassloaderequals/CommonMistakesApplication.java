package org.geekbang.time.commonmistakes.equals.differentclassloaderequals;

import java.io.IOException;
import java.io.InputStream;

public class CommonMistakesApplication {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                InputStream inputStream = getClass().getResourceAsStream(fileName);
                if (inputStream == null) {
                    return super.loadClass(name);
                }
                try {
                    byte[] b = new byte[inputStream.available()];
                    inputStream.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException();
                }
            }
        };


        Object point1 = classLoader.loadClass(Point.class.getName()).newInstance();
        Point point2 = new Point();
        Point point3 = (Point) ClassLoader.getSystemClassLoader().loadClass(Point.class.getName()).newInstance();

        System.out.println(point1 instanceof Point);
        System.out.println(point1.getClass() == Point.class);
        System.out.println(point1.equals(point2));

        System.out.println(point3 instanceof Point);
        System.out.println(point3.getClass() == Point.class);
        System.out.println(point3.equals(point2));
    }
}

