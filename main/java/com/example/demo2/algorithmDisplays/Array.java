package com.example.demo2.algorithmDisplays;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Array extends Sector{
    //lenze teraz nevieme urobit sipky medzi nimi
    //nebude to lepsie urobit ako maticu

    private boolean isVertical = false;
    private final ArrayList<Label> labels;

    public Array(Pane pane, double leftBorder, double upperBorder, double rightBorder, double bottomBorder) {
        super(pane, leftBorder, upperBorder, rightBorder, bottomBorder);
        this.labels = new ArrayList<>();
    }

    public void setVertical(boolean vertical) {
        this.isVertical = vertical;
    }

    public void setSize(){

    }

    @Override
    public void setBorders(double leftBorder, double upperBorder, double rightBorder, double bottomBorder){

    }


}
