import Entities.EDTInvocationHandler;
import Interfaces.FilterViewInterface;
import Model.FilterModel;
import Presenter.FilterPresenter;
import View.FilterView;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FilterPresenter presenter = new FilterPresenter();
                FilterModel model = new FilterModel();

                FilterViewInterface view = (FilterViewInterface) java.lang.reflect.Proxy.newProxyInstance(getClass().getClassLoader(),
                        new Class[]{FilterViewInterface.class}, new EDTInvocationHandler(new FilterView()));

                presenter.setModel(model);
                presenter.setView(view);
                presenter.run();
            }
        });
    }
}
