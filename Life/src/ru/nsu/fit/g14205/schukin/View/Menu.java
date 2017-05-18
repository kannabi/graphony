package ru.nsu.fit.g14205.schukin.View;
/**
 * Created by kannabi on 05.03.17.
 */

import ru.nsu.fit.g14205.schukin.Entities.MenuButtons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static ru.nsu.fit.g14205.schukin.Entities.MenuButtons.*;

public class Menu {
    JMenuBar menubar;

    private static OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(MenuButtons button);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public Menu() {
        menubar = new JMenuBar();

        menubar.add(setUpFileMenu(new JMenu("File")));
        menubar.add(setUpEditMenu(new JMenu("Edit")));
        menubar.add(setUpViewMenu(new JMenu("View")));
        menubar.add(setUpSimulationMenu(new JMenu("Simulation")));
        menubar.add(setUpHelpMenu(new JMenu("Help")));
    }

    public JMenuBar getMenuBar(){
        return menubar;
    }

    private JMenu setUpFileMenu(JMenu fileMenu){
        // добавление простых элементов меню

        JMenuItem itm = new JMenuItem("New");
        itm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                ActionEvent.CTRL_MASK));
        fileMenu.add(itm);
        itm.addActionListener((e) ->listener.onItemClick(NEW_FILE));


        itm = new JMenuItem("Open");
        itm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                ActionEvent.CTRL_MASK));
        itm.addActionListener((ActionEvent e) -> listener.onItemClick(LOAD_CONF));
        fileMenu.add(itm);


        itm = new JMenuItem("Save");
        itm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                ActionEvent.CTRL_MASK));
        itm.addActionListener((ActionEvent e) -> listener.onItemClick(SAVE_CONF));
        fileMenu.add(itm);

        // если нужен элемент меню с иконкой
        //itm = new JMenuItem("Close", new ImageIcon("image.gif"));
        //itm = new JMenuItem(new ImageIcon("image.gif"));

        // добавляем разделитель
        fileMenu.add(new JSeparator());

        itm = new JMenuItem("Exit");
        itm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
                ActionEvent.CTRL_MASK));
        itm.addActionListener((e) -> listener.onItemClick(EXIT));
        fileMenu.add(itm);

        return fileMenu;
    }

    private JMenu setUpEditMenu(JMenu editMenu){
        JMenuItem itm = new JMenuItem("XOR");
        itm.addActionListener((e) -> listener.onItemClick(XOR));
        editMenu.add(itm);

        itm = new JMenuItem("Replace");
        itm.addActionListener((e) -> listener.onItemClick(RPLC));
        editMenu.add(itm);

        editMenu.add(new JSeparator());

        itm = new JMenuItem("Clear");
        itm.addActionListener((e) -> listener.onItemClick(CLEAR));
        editMenu.add(itm);

        editMenu.add(new JSeparator());

        itm = new JMenuItem("Parameters");
        itm.addActionListener((e) -> listener.onItemClick(PARAMS));
        editMenu.add(itm);

        return editMenu;
    }

    private JMenu setUpViewMenu(JMenu viewMenu){
        JMenuItem itm = new JMenuItem("Display Impact Values");
        itm.addActionListener((e) -> listener.onItemClick(IMP));
        viewMenu.add(itm);

        return viewMenu;
    }

    private JMenu setUpSimulationMenu(JMenu simMenu){
        JMenuItem itm = new JMenuItem("Run");
        itm.addActionListener((e) -> listener.onItemClick(PLAY));
        simMenu.add(itm);

        simMenu.add(new JSeparator());

        itm = new JMenuItem("Step");
        itm.addActionListener((e) -> listener.onItemClick(STEP));
        simMenu.add(itm);

        return simMenu;
    }

    private JMenu setUpHelpMenu(JMenu helpMenu){
        JMenuItem itm = new JMenuItem("About");
        itm.addActionListener((e) -> listener.onItemClick(ABOUT));
        helpMenu.add(itm);
        return helpMenu;
    }
}