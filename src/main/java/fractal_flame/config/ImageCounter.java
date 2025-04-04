package fractal_flame.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ImageCounter {
    private final File file = new File("src/main/java/fractal_flame/config/counter.properties");

    public long getAndIncrement(){
        long increment;
        Properties props;
        try (var in = new FileInputStream(file)) {
            props = new Properties();
            props.load(in);
            increment = Long.parseLong(props.getProperty("image.counter")) + 1;
            props.setProperty("image.counter", increment + "");
        } catch (IOException e) {
            throw new RuntimeException("Cannot read counter file", e);
        }

        try (var out = new FileOutputStream(file)) {
            props.store(out, "");
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong with properties saving", e);
        }

        return increment;
    }

    public long get(){
        long counter;
        Properties props;
        try (var in = new FileInputStream(file)) {
            props = new Properties();
            props.load(in);
            counter = Long.parseLong(props.getProperty("image.counter"));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read counter file", e);
        }

        try (var out = new FileOutputStream(file)) {
            props.store(out, "");
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong with properties saving", e);
        }

        return counter;
    }
}
