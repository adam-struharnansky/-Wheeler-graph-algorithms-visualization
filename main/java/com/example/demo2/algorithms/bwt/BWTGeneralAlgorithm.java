package com.example.demo2.algorithms.bwt;

import com.example.demo2.algorithmDisplays.DisplayManager;
import com.example.demo2.algorithmDisplays.MatrixDisplay;
import com.example.demo2.algorithmDisplays.TextDisplay;
import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.algorithmManager.AlgorithmType;
import com.example.demo2.algorithms.Algorithm;
import com.example.demo2.multilingualism.LanguageListenerAdder;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;

public class BWTGeneralAlgorithm extends Algorithm {

    private final AlgorithmManager algorithmManager;
    private final TextDisplay textDisplay;
    private final MatrixDisplay matrixDisplay;

    private String input;
    private String output;
    private ArrayList<String> rotations;

    private final TextField inputTextField;
    private final Button startButton;
    private Button endButton;

    public BWTGeneralAlgorithm(AlgorithmManager algorithmManager) {
        super(algorithmManager);
        this.matrixDisplay = (MatrixDisplay) super.addDisplay(DisplayManager.DisplayType.Matrix, "", 1);
        this.textDisplay = (TextDisplay) super.addDisplay(DisplayManager.DisplayType.Text, "", 1);
        this.algorithmManager = algorithmManager;
        //todo - zakazat vstup $ - skotrolovat
        this.inputTextField = new TextField();
        super.addController(this.inputTextField, 0,0);

        this.startButton = new Button();
        LanguageListenerAdder.addLanguageListener("startAlgorithm", this.startButton);
        this.startButton.setOnAction(actionEvent -> start(this.inputTextField.getText().replaceAll("\\$", "")));
        super.addController(this.startButton, 1,0);
        //todo - pridat opis alg

    }

    private void start(String input){
        this.input = input + '$';
        this.rotations = new ArrayList<>();
        this.matrixDisplay.setMatrixSize(this.input.length() + 1, this.input.length());

        StringBuilder start = new StringBuilder(this.input);
        StringBuilder end = new StringBuilder();
        for(int i = 0; i<this.input.length(); i++){
            this.rotations.add(String.valueOf(start) + end);
            for(int j = 0;j<this.input.length();j++){
                this.matrixDisplay.setSquareText(i + 1, j, this.rotations.get(i).charAt(j));
            }
            end.append(start.charAt(0));
            start.deleteCharAt(0);
        }
        //todo - pridat popis alg
        super.removeController(this.startButton);
        super.removeController(this.inputTextField);
        this.endButton = new Button();
        LanguageListenerAdder.addLanguageListener("", this.endButton);
        this.endButton.setOnAction(actionEvent -> end());
        super.addController(this.endButton, 0, 0);
    }

    private void end(){
        this.matrixDisplay.setSquareText(0,0, "F");
        this.matrixDisplay.setSquareText(0, this.input.length() - 1, "L");
        this.matrixDisplay.setSquareColor(0,0, Color.BLUE);
        this.matrixDisplay.highlightSquare(0,0);
        this.matrixDisplay.setSquareColor(0, this.input.length() - 1, Color.RED);
        this.matrixDisplay.highlightSquare(0,this.input.length() - 1);

        this.rotations.sort(Comparator.naturalOrder());
        StringBuilder stringBuilderOutput = new StringBuilder();
        for(int i = 0; i<this.input.length(); i++){
            for(int j = 0;j<this.input.length();j++){
                this.matrixDisplay.setSquareText(i + 1, j, this.rotations.get(i).charAt(j));
            }
            this.matrixDisplay.setSquareColor(i + 1, 0, Color.BLUE);
            this.matrixDisplay.highlightSquare(i + 1, 0);
            this.matrixDisplay.setSquareColor(i + 1, this.input.length() - 1, Color.RED);
            this.matrixDisplay.highlightSquare(i + 1, this.input.length() - 1);
            stringBuilderOutput.append(this.rotations.get(i).charAt(input.length()-1));
        }
        super.removeController(this.endButton);
        this.output = stringBuilderOutput.toString();
        //todo - popis alg

        Button retryButton = new Button();
        retryButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.BWTEncode));
        LanguageListenerAdder.addLanguageListener("", retryButton);
        super.addController(retryButton, 0,0);

        Button decodeButton = new Button();
        decodeButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.BWTDecode, this.output));
        LanguageListenerAdder.addLanguageListener("", decodeButton);
        super.addController(decodeButton, 1,0);

        Button bwtButton = new Button();
        bwtButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.BWT));
        LanguageListenerAdder.addLanguageListener("", bwtButton);
        super.addController(bwtButton, 2,0);

        //todo - uvodna obrazovka
    }

}
