package ru.nsu.fit.g14205.schukin.Interfaces;

/**
 * Created by kannabi on 16.04.2017.
 */
public interface WireframePresenterInterface {

    void setModel(WireframeModelInterface model);

    void setView(WireframeViewInterface view);

    WireframeModelInterface getModel();

    WireframeViewInterface getView();

    void run();
}
