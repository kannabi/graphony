package ru.nsu.ccfit.schukin.IsolineView;

import ru.nsu.ccfit.schukin.Entities.ToolbarButton;

import javax.swing.*;
import java.awt.*;

import static ru.nsu.ccfit.schukin.Entities.Const.*;
import static ru.nsu.ccfit.schukin.Entities.ToolbarButton.*;

/**
 * Created by kannabi on 02.04.2017.
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

        JToggleButton gridBt = new JToggleButton(GRID_BUTTON);
        gridBt.addActionListener((e) ->{
            listener.onButtonClick(GRID);
            if(gridBt.isSelected())
                gridBt.setSelected(false);
            else
                gridBt.setSelected(true);
        });
        jtb.add(gridBt);

        JToggleButton enBt = new JToggleButton(ENTRY_POINTS_BUTTON);
        enBt.addActionListener((e) ->{
            listener.onButtonClick(ENTRY_POINTS);
            if(enBt.isSelected())
                enBt.setSelected(false);
            else
                enBt.setSelected(true);
        });
        jtb.add(enBt);

        bt = new JButton(HELP_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(HELP));
        jtb.add(bt);
    }
}
