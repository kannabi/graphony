package ru.nsu.fit.g14205.schukin;

import ru.nsu.fit.g14205.schukin.Entities.EDTInvocationHandler;
import ru.nsu.fit.g14205.schukin.Interfaces.WireframeViewInterface;
import ru.nsu.fit.g14205.schukin.Model.WireframeModel;
import ru.nsu.fit.g14205.schukin.Presenter.WireframePresenter;
import ru.nsu.fit.g14205.schukin.View.WireframeView;

import java.util.Vector;

import static ru.nsu.fit.g14205.schukin.Model.AlgebraTools.multiplicateVector;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                WireframePresenter presenter = new WireframePresenter();
                WireframeModel model = new WireframeModel();

                WireframeViewInterface view = (WireframeViewInterface) java.lang.reflect.Proxy.newProxyInstance(getClass().getClassLoader(),
                        new Class[]{WireframeViewInterface.class}, new EDTInvocationHandler(new WireframeView()));

                presenter.setModel(model);
                presenter.setView(view);
                presenter.run();
            }
        });
    }
}
