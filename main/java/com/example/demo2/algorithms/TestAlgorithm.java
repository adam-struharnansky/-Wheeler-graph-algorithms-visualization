package com.example.demo2.algorithms;

import com.example.demo2.algorithmController.AlgorithmController;
import com.example.demo2.algorithmDisplays.AlgorithmDisplay;
import com.example.demo2.algorithmDisplays.MatrixDisplay;
import com.example.demo2.algorithmDisplays.TextDisplay;
import javafx.scene.layout.HBox;

public class TestAlgorithm extends Algorithm {

    private final MatrixDisplay bwtDisplay1;
    private final MatrixDisplay bwtDisplay2;
    private final MatrixDisplay bwtDisplay3;
    private final TextDisplay bwtDisplay4;

    private final AlgorithmDisplay algorithmDisplay;
    private final AlgorithmController algorithmController;

    public TestAlgorithm(HBox algorithmControllerHBox, HBox algorithmDisplayHBox){
        this.algorithmDisplay = new AlgorithmDisplay(algorithmDisplayHBox);
        this.algorithmController = new AlgorithmController(algorithmControllerHBox);

        this.bwtDisplay1 = (MatrixDisplay)
                this.algorithmDisplay.addDisplay(AlgorithmDisplay.DisplayType.Matrix, "D1", 2);
        this.bwtDisplay2 = (MatrixDisplay)
                this.algorithmDisplay.addDisplay(AlgorithmDisplay.DisplayType.Matrix, "D2", 3);
        this.bwtDisplay3 = (MatrixDisplay)
                this.algorithmDisplay.addDisplay(AlgorithmDisplay.DisplayType.Matrix, "D3", 4);
        this.bwtDisplay4 = (TextDisplay)
                this.algorithmDisplay.addDisplay(AlgorithmDisplay.DisplayType.Text, "D4", 2);

        this.algorithmController.add("NextStep",new Algorithm.InvokerNextStep());
    }


    @Override
    public boolean hasNext() {
        System.out.println("in test");
        return true;
    }

    @Override
    public void nextStep() {

    }
}
