package ru.nsu.ccfit.schukin.Interfaces;

import ru.nsu.ccfit.schukin.Entities.ColorMap;

import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by kannabi on 02.04.2017.
 */

public interface IsolineModelInterface {
    void setPresenter(IsolinePresenterInterface presenter);

    IsolinePresenterInterface getPresenter();

    double getFunctionValue(double x, double y);

    ColorMap getColorMap();
}
