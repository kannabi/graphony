package ru.nsu.fit.g14205.schukin.Interfaces;

/**
 * Created by kannabi on 16.04.2017.
 */
public interface WireframeViewInterface {
    void setPresenter(WireframePresenterInterface presenter);

    WireframePresenterInterface getPresenter();

    void initUI();

    void repaintView();

    void setModel(WireframeModelInterface model);

    WireframeModelInterface getModel();

    void updateParameters();
}
