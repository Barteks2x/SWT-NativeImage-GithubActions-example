package io.github.barteks2x.swtnativeimageexample.jarloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import javax.swing.JOptionPane;

public class JarLoadMain {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        Path temp = Files.createTempDirectory("swtnativeimageexampleJarLoad");
        InputStream jar;
        if (OperatingSystem.isUnix()) {
            jar = JarLoadMain.class.getResourceAsStream("/linux/swt.jar");
        } else if (OperatingSystem.isMac()) {
            jar = JarLoadMain.class.getResourceAsStream("/mac/swt.jar");
        } else if (OperatingSystem.isWindows() && is64bit()) {
            jar = JarLoadMain.class.getResourceAsStream("/windows64/swt.jar");
        } else if (OperatingSystem.isWindows() && !is64bit()) {
            jar = JarLoadMain.class.getResourceAsStream("/windows32/swt.jar");
        } else {
            JOptionPane.showMessageDialog(null, "Error: unknown operating system", "Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Unknown OS");
        }
        URL currJarUrl = JarLoadMain.class.getProtectionDomain().getCodeSource().getLocation();
        Path swt = temp.resolve("swt.jar");
        Path currJarPath = Paths.get(currJarUrl.toURI());
        Files.copy(jar, swt);

        if (OperatingSystem.isMac()) {
            String javaHome = System.getProperty("java.home");
            if (javaHome != null) {
                System.out.println("Relaunching");
                Path java = Paths.get(javaHome).resolve("bin").resolve("java");
                int err = Runtime.getRuntime().exec(
                        new String[]{
                                java.toString(),
                                "-XstartOnFirstThread",
                                "-Xms32M",
                                "-cp",
                                currJarPath + File.pathSeparator + swt,
                                "io.github.barteks2x.swtnativeimageexample.swt.GuiMain"
                        }
                ).waitFor();
                if (err != 0) {
                    JOptionPane.showMessageDialog(null, "An error occurred while running GUI. Error code: " + err, "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
        }

        ClassLoader cl = new URLClassLoader(new URL[]{swt.toFile().toURI().toURL(), currJarUrl}, null);
        Thread.currentThread().setContextClassLoader(cl);
        try {
            Class<?> guiMain = cl.loadClass("io.github.barteks2x.swtnativeimageexample.swt.GuiMain");
            guiMain.getDeclaredMethod("main", String[].class).invoke(null, new Object[]{new String[0]});
        } catch (Throwable e) {
            JOptionPane.showMessageDialog(null, "Error: unable to load GUI", "Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    private static class OperatingSystem {

        private static String OS = System.getProperty("os.name", "unknown").toLowerCase(Locale.ROOT);

        public static boolean isWindows() {
            return OS.contains("win");
        }

        public static boolean isMac() {
            return OS.contains("mac");
        }

        public static boolean isUnix() {
            return OS.contains("nux");
        }
    }

    private static final String KEYS_ARCH[] = {
            "sun.arch.data.model",
            "com.ibm.vm.bitmode",
            "os.arch",
    };

    public static boolean is64bit() {
        for (String key : KEYS_ARCH) {
            String property = System.getProperty(key);
            if (property != null) {
                return property.contains("64");
            }
        }
        throw new RuntimeException("Can't find architecture");
    }
}
