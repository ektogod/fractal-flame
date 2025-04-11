package fractal_flame.config;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class ImageCounter {
    //private final File file = new File("src/main/java/fractal_flame/config/counter.properties");

    public int getAndIncrement(String path) {
        int counter;
        File file = new File(path + "/counter.txt");
        if (!file.exists()) {
            createFile(file);
            counter = 1;
        } else {
            try (Scanner scanner = new Scanner(file)) {
                counter = scanner.nextInt();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            try (FileOutputStream out = new FileOutputStream(file)) {
                out.write((++counter + "").getBytes());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return counter;
    }

    public long get(String path) {
        long counter;
        File file = new File(path + "/counter.txt");
        if (!file.exists()) {
            createFile(file);
            counter = 1;
        } else {
            try (Scanner scanner = new Scanner(file)) {
                counter = scanner.nextInt();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return counter;
    }

        private void createFile (File file){
            try {
                if (file.createNewFile()) {
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write("1".getBytes());
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
