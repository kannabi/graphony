package Interfaces;

import java.awt.image.BufferedImage;

/**
 * Created by kannabi on 25/03/2017.
 */
public interface Filter {
    BufferedImage transform(BufferedImage image);
}
