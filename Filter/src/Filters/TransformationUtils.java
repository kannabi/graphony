package Filters;

import java.awt.*;

/**
 * Created by kannabi on 26/03/2017.
 */
class TransformationUtils {

    static int getRed(int rgb){
        return new Color(rgb).getRed();
//        return rgb & 0xff;
    }

    static int getGreen(int rgb){
        return new Color(rgb).getGreen();
//        return (rgb & 0xff00) >> 8;
    }

    static int getBlue(int rgb){
        return new Color(rgb).getBlue();
//        return (rgb & 0xff0000) >> 16;
    }

    static int getSafeVal(int val){
        return val <= 0 ? 0 : val >= 0xFF ? 0xFF : val;
//        return val;
    }
}

