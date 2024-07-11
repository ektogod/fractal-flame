package fractal_flame.pixel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Pixel {
    private int Red;
    private int Green;
    private int Blue;
    private int hitNumber;
    private double normal;
}
