package Presenter;

import Interfaces.FilterPresenterInterface;
import Interfaces.FilterViewInterface;
import Model.FilterModel;
import View.FilterView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static Entities.Const.DEFAULT_PANEL_HEIGHT;
import static Entities.Const.DEFAULT_PANEL_WIDTH;

/**
 * Created by kannabi on 24/03/2017.
 */
public class FilterPresenter implements FilterPresenterInterface {
    private FilterModel model;
    private FilterViewInterface view;

    @Override
    public void run(){
        view.setPresenter(this);
        model.setPresenter(this);
        view.initUI();
    }

    @Override
    public void setView(FilterViewInterface view){
        this.view = view;
    }

    @Override
    public void setModel(FilterModel model){
        this.model = model;
    }

    @Override
    public FilterModel getModel(){
        return model;
    }

    @Override
    public FilterViewInterface getView(){
        return view;
    }
}
