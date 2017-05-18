package ru.nsu.fit.g14205.schukin.Presenter;

import ru.nsu.fit.g14205.schukin.Entities.Point;
import ru.nsu.fit.g14205.schukin.Entities.ToolBarButton;
import ru.nsu.fit.g14205.schukin.Interfaces.LifePresenterInterface;
import ru.nsu.fit.g14205.schukin.Model.LifeModel;
import ru.nsu.fit.g14205.schukin.View.LifeView;
import ru.nsu.fit.g14205.schukin.View.NewDocument.NewDocument;
import ru.nsu.fit.g14205.schukin.View.SettingWindow.SettingsWindow;

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static ru.nsu.fit.g14205.schukin.Entities.Const.*;

/**
 * Created by kannabi on 06.03.2017.
 */

public class LifePresenter implements LifePresenterInterface{
    LifeModel model;
    LifeView view;

    private int len, thickness;
    private int m, n;
    private LinkedList<Point> startPoints = new LinkedList<>();

    public LifePresenter(){
        try {
            File fin = new File(PATH_TO_CONF);
            readConfiguration(fin);
        } catch (IOException e){
            setDefaultConfiguration();
        } catch (NoSuchElementException e){
            setDefaultConfiguration();
        }
    }
    private volatile boolean isIterationGoing = false;

    @Override
    public void setModel(LifeModel model){
        this.model = model;
        setDefaultLifeParams();
    }

    @Override
    public LifeModel getModel(){
        return model;
    }

    @Override
    public void setView(LifeView view){
        this.view = view;
    }

    @Override
    public LifeView getView (){
        return view;
    }

    @Override
    public void run(){
        view.setPresenter(this);
        view.createInterface(model.buildMap(len, m, n, thickness), len);
        view.setReplaceModeButtons(model.getReplaceMode());
    }

    @Override
    public void mouseClicked(int x, int y){
        view.paintMap(model.addAliveHex(x, y));
    }

    @Override
    public void getNextStep(){
        view.paintMap(model.refreshMap());
    }

    @Override
    public void clearMap(){
        view.paintMap(model.armageddon());
    }

    @Override
    public void setReplaceMode(ToolBarButton mode){
        model.setReplaceMode(mode);
    }

    @Override
    public void startIterationProcess(){
        changeIterationIsGoing();
        Thread running = new Thread(() -> {
           while (isIterationGoing){
               getNextStep();
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e){
                   e.printStackTrace();
               }
           }
        });
        running.start();
    }

    @Override
    public void redrawMap(){
        view.setReplaceModeButtons(model.getReplaceMode());
        view.paintMap(model.getMap());
    }

    @Override
    public void saveConfiguration(){
        JFileChooser fileChooser = new JFileChooser("FAK_14205_Schukin_Life_Data");
        int answer = fileChooser.showSaveDialog(view);

        if (answer == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter out = new FileWriter(fileChooser.getSelectedFile(), false);
                LinkedList<Point> points = model.getAliveHexList();

                out.write(Integer.toString(m) + " " + Integer.toString(n) + "\n");
                out.write(Integer.toString(thickness) + "\n");
                out.write(Integer.toString(len) + "\n");
                out.write(points.size() + "\n");
                for (Point it : points)
                    out.write(it.getY() + " " + it.getX() + "\n");
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showHelpDialog(){
        Thread dialog = new Thread(() -> JOptionPane.showMessageDialog(null, HELP_MESSAGE));
        dialog.start();
    }

    @Override
    public void showSettingsWindow(){
        Thread dialog = new Thread(() -> new SettingsWindow(model, this));
        dialog.start();
    }

    @Override
    public void chooseFile(){
        JFileChooser fileChooser = new JFileChooser("FAK_14205_Schukin_Life_Data");
        int answer = fileChooser.showOpenDialog(view);

        if(answer == JFileChooser.APPROVE_OPTION)
            try {
                readConfiguration(fileChooser.getSelectedFile());
                model.buildMap(len, m, n, thickness);
                model.addInitsHexes(startPoints);
                startPoints.clear();
            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e_){
                e_.printStackTrace();
            }
        view.paintMap(model.getMap());
    }

    @Override
    public void createNewGameDocument(){
        Thread createNew = new Thread(() -> new NewDocument(model, this));
        createNew.start();
    }

    @Override
    public void exit(){
        System.exit(0);
    }

    private synchronized void changeIterationIsGoing(){
        isIterationGoing = !isIterationGoing;
    }

    private void readConfiguration(File fin) throws IOException, NoSuchElementException{
        try{
            Scanner in = new Scanner(fin);

            m = safeRead(in);
            n = safeRead(in);
            thickness = safeRead(in);
            len = safeRead(in);
            int num = safeRead(in);
            for (int i = 0; i < num; ++i)
                startPoints.add(new Point(safeRead(in), safeRead(in)));

            in.close();
        } catch (IOException e){
            e.printStackTrace();
            throw e;
        } catch (NoSuchElementException e_){
            e_.printStackTrace();
            throw e_;
        }
    }

    private int safeRead(Scanner in){
        Integer cur = null;
        while (cur == null)
            if(!in.hasNextInt())
                in.nextLine();
            else
                cur = in.nextInt();

        return cur;
    }

    private void setDefaultConfiguration(){
        m = DEFAULT_M;
        n = DEFAULT_N;
        thickness = DEFAULT_THINKNESS;
        len = DEFAULT_LENGTH;
    }

    private void setDefaultLifeParams(){
        model.setBirth_begin(BIRTH_BEGIN);
        model.setBirth_end(BIRTH_END);
        model.setLive_begin(LIVE_BEGIN);
        model.setLive_end(LIVE_END);
        model.setFst_impact(FST_IMPACT);
        model.setSnd_impact(SND_IMPACT);
    }
}