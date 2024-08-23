package fractal_flame.spring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.nio.file.Path;


@Getter
@Setter
@Service
public class AppService {
    private boolean sinusFlag;
    private boolean sphereFlag;
    private boolean polarFlag;
    private boolean heartFlag;
    private boolean diskFlag;
    private boolean hyperbolicFlag;
    private boolean logarithmicFlag;
    private boolean spiralFlag;

    private boolean autoSaveFlag;
    private String pathToFolder;
}
