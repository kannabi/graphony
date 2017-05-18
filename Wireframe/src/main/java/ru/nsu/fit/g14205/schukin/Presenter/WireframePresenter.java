package ru.nsu.fit.g14205.schukin.Presenter;

import ru.nsu.fit.g14205.schukin.Interfaces.WireframeModelInterface;
import ru.nsu.fit.g14205.schukin.Interfaces.WireframePresenterInterface;
import ru.nsu.fit.g14205.schukin.Interfaces.WireframeViewInterface;

/**
 * Created by kannabi on 16.04.2017.
 */
public class WireframePresenter implements WireframePresenterInterface {
    private WireframeModelInterface model;
    private WireframeViewInterface view;

    public void setModel(WireframeModelInterface model){
        this.model = model;
    }

    public void setView(WireframeViewInterface view){
        this.view = view;
    }

    public WireframeModelInterface getModel(){
        return model;
    }

    public WireframeViewInterface getView(){
        return view;
    }

    public void run(){
        model.setPresenter(this);
        view.setPresenter(this);
        view.setModel(model);
        view.initUI();
        view.updateParameters();
    }
}
