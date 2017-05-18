package View.Dialogs;

import javax.swing.*;
import java.awt.event.*;

public class GammaDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSlider gammaSlider;
    double res;
    boolean answer = false;

    public GammaDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        gammaSlider.setMaximum(200);
        gammaSlider.setMinimum(0);
        gammaSlider.setValue(100);
        gammaSlider.setMajorTickSpacing(1);

        this.pack();
        this.setVisible(true);
    }

    private void onOK() {
        // add your code here

        System.out.println(gammaSlider.getValue());
        res = (double)gammaSlider.getValue() / 100;
        System.out.println(res);

        answer = true;
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public double getResult(){
        return res;
    }

    public boolean getAnswer(){
        return answer;
    }
}
