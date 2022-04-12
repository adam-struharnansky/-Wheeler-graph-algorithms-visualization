package com.example.demo2.algorithms;

import com.example.demo2.algorithmDisplays.*;
import com.example.demo2.algorithmManager.AlgorithmManager;

public class TestAlgorithm extends Algorithm {

    private OrientedGraphDisplay graphDisplay;
    private MatrixDisplay matrixDisplay;
    private TextDisplay textDisplay;

    public TestAlgorithm(AlgorithmManager algorithmManager){
        super(algorithmManager);

        this.graphDisplay = (OrientedGraphDisplay) super.addDisplay(DisplayManager.DisplayType.DirectedGraph, "", 1);
        this.matrixDisplay = (MatrixDisplay) super.addDisplay(DisplayManager.DisplayType.Matrix, "", 1);
        this.textDisplay = (TextDisplay) super.addDisplay(DisplayManager.DisplayType.Text, "", 1);


    }

    @Override
    public void nextStep() {
    }

}
