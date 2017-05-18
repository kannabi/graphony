package ru.nsu.ccfit.schukin.Interfaces;

import ru.nsu.ccfit.schukin.Entities.FunctionData;

/**
 * Created by kannabi on 02.04.2017.
 */
public interface IsolineViewInterface {
    void setPresenter(IsolinePresenterInterface presenter);

    IsolinePresenterInterface getPresenter();

    void initUI();

    @RequiresEDT
    void paintGraph(IsolineModelInterface model, FunctionData data);
}
