package ru.nsu.fit.g14205.schukin.View;

import ru.nsu.fit.g14205.schukin.Entities.ToolbarButton;

import javax.swing.*;
import java.awt.*;

import static ru.nsu.fit.g14205.schukin.Entities.Const.*;
import static ru.nsu.fit.g14205.schukin.Entities.ToolbarButton.*;

/**
 * Created by kannabi on 18.04.2017.
 */
public class Toolbar extends JPanel {
    private JToolBar jtb = new JToolBar();

    private static OnButtonClickListener listener;

    public interface OnButtonClickListener{
        void onButtonClick(ToolbarButton button);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener){
        this.listener = listener;
    }

    public Toolbar() {
        setLayout(new BorderLayout());

        add(jtb, BorderLayout.NORTH);
        jtb.setFloatable(false);

        JButton bt = new JButton(OPEN_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(OPEN));
        jtb.add(bt);

        bt = new JButton(SAVE_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(SAVE));
        jtb.add(bt);

        bt = new JButton(INIT_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(INIT));
        jtb.add(bt);

        bt = new JButton(SETTINGS_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(SETTINGS));
        jtb.add(bt);

        bt = new JButton(HELP_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(HELP));
        jtb.add(bt);
    }
}
