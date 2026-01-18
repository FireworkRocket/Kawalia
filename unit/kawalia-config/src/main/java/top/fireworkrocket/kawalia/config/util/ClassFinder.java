package top.fireworkrocket.kawalia.config.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ClassFinder {

    private ClassFinder() {}

    public static Set<Class<?>> findClasses(String... basePackages) throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        for (String basePackage : basePackages) {
            classes.addAll(findClassesInPackage(basePackage));
        }
        return classes;
    }

    private static Set<Class<?>> findClassesInPackage(String basePackage) throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        String path = basePackage.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            URLConnection connection = resource.openConnection();
            if (connection instanceof JarURLConnection) {
                JarFile jarFile = ((JarURLConnection) connection).getJarFile();
                classes.addAll(findClassesInJar(jarFile, path));
            } else {
                classes.addAll(findClassesInDirectory(new File(resource.getFile()), basePackage));
            }
        }
        return classes;
    }

    private static Set<Class<?>> findClassesInDirectory(File directory, String packageName) throws ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClassesInDirectory(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }

    private static Set<Class<?>> findClassesInJar(JarFile jarFile, String packagePath) throws ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (name.startsWith(packagePath) && name.endsWith(".class")) {
                String className = name.replace('/', '.').substring(0, name.length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }
}
