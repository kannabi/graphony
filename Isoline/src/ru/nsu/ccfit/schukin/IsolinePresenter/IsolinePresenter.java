package ru.nsu.ccfit.schukin.IsolinePresenter;

import ru.nsu.ccfit.schukin.Entities.FunctionData;
import ru.nsu.ccfit.schukin.Interfaces.IsolineModelInterface;
import ru.nsu.ccfit.schukin.Interfaces.IsolinePresenterInterface;
import ru.nsu.ccfit.schukin.Interfaces.IsolineViewInterface;
import ru.nsu.ccfit.schukin.Interfaces.RequiresEDT;

import static ru.nsu.ccfit.schukin.Entities.Const.X_MAX;
import static ru.nsu.ccfit.schukin.Entities.Const.Y_MAX;

/**
 * Created by kannabi on 02.04.2017.
 */
public class IsolinePresenter implements IsolinePresenterInterface{
    private IsolineModelInterface model;
    private IsolineViewInterface view;
    private FunctionData data;

    @Override
    public void setModel(IsolineModelInterface model){
        this.model = model;
    }

    @Override
    public IsolineModelInterface getModel(){
        return model;
    }

    @Override
    public void setView(IsolineViewInterface view){
        this.view = view;
    }

    @Override
    public IsolineViewInterface getView(){
        return view;
    }

    @Override
    public void run(){
        view.initUI();
        view.setPresenter(this);
        model.setPresenter(this);
        data = new FunctionData(X_MAX, Y_MAX, model);
        view.paintGraph(model, data);
    }

    @Override
    public FunctionData getFunctionalData(){
        return data;
    }

}
