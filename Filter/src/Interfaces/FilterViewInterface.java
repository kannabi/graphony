package Interfaces;

import Presenter.FilterPresenter;

import java.awt.image.BufferedImage;

/**
 * Created by kannabi on 24/03/2017.
 */
public interface FilterViewInterface {

    @RequiresEDT
    void initUI();

    void setPresenter(FilterPresenter presenter);
}
