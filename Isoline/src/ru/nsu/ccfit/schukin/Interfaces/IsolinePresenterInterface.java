package ru.nsu.ccfit.schukin.Interfaces;

import ru.nsu.ccfit.schukin.Entities.FunctionData;

/**
 * Created by kannabi on 02.04.2017.
 */
public interface IsolinePresenterInterface {
    void setModel(IsolineModelInterface model);

    IsolineModelInterface getModel();

    void setView(IsolineViewInterface view);

    IsolineViewInterface getView();

    FunctionData getFunctionalData();

    void run();
}
