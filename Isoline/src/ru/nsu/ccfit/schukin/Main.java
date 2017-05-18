package ru.nsu.ccfit.schukin;

import ru.nsu.ccfit.schukin.Entities.EDTInvocationHandler;
import ru.nsu.ccfit.schukin.Interfaces.IsolineModelInterface;
import ru.nsu.ccfit.schukin.Interfaces.IsolinePresenterInterface;
import ru.nsu.ccfit.schukin.Interfaces.IsolineViewInterface;
import ru.nsu.ccfit.schukin.IsolineModel.IsolineModel;
import ru.nsu.ccfit.schukin.IsolinePresenter.IsolinePresenter;
import ru.nsu.ccfit.schukin.IsolineView.IsolineView;

import java.awt.*;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                IsolinePresenterInterface presenter = new IsolinePresenter();
                IsolineModelInterface model = new IsolineModel();

                IsolineViewInterface view = (IsolineViewInterface) java.lang.reflect.Proxy.newProxyInstance(getClass().getClassLoader(),
                        new Class[]{IsolineViewInterface.class}, new EDTInvocationHandler(new IsolineView()));

                presenter.setModel(model);
                presenter.setView(view);
                presenter.run();
            }
        });
    }
}
