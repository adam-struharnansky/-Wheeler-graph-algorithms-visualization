package com.example.demo2.algorithms.bwt;

import com.example.demo2.algorithmDisplays.*;
import com.example.demo2.algorithmDisplays.animatableNodes.DisplayType;
import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.algorithmManager.AlgorithmType;
import com.example.demo2.algorithms.Algorithm;
import com.example.demo2.multilingualism.LanguageListenerAdder;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Comparator;

public class BWTFromSAAlgorithm extends Algorithm {

    private final AlgorithmManager algorithmManager;

    private String input;

    private TextField inputTextField;
    private Button startButton;
    private final boolean fromTextField;

    private final MatrixDisplay bwtDisplay = (MatrixDisplay) WindowManager.addDisplay(DisplayType.Matrix, "", 3);;
    private final MatrixDisplay saDisplay = (MatrixDisplay) WindowManager.addDisplay(DisplayType.Matrix, "", 3);;
    private final MatrixDisplay relationDisplay = (MatrixDisplay) WindowManager.addDisplay(DisplayType.Matrix, "", 2);;
    private final CodeDisplay codeDisplay = (CodeDisplay) WindowManager.addDisplay(DisplayType.Code, "", 3);;
    private final TextDisplay textDisplay = (TextDisplay) WindowManager.addDisplay(DisplayType.Text, "", 3);;

    private static final int iColumn = 0;
    private static final int saColumn = 1;
    private static final int sColumn = 2;
    private static final int bwtColumn = 3;

    private int iterator;
    private ArrayList<String> rotations;
    private int n;
    private char [] lV;
    private int [] saV;

    public BWTFromSAAlgorithm(AlgorithmManager algorithmManager) {
        super(algorithmManager);
        this.algorithmManager = algorithmManager;
        setCode();

        this.inputTextField = new TextField();
        WindowManager.addController(this.inputTextField, 0,0);
        this.startButton = new Button();
        WindowManager.addController(this.startButton, 0,1);
        this.fromTextField = true;

        LanguageListenerAdder.addLanguageListener("startAlgorithm", this.startButton);
        this.startButton.setOnAction(actionEvent -> preStart(this.inputTextField.getText()));
    }

    public BWTFromSAAlgorithm(AlgorithmManager algorithmManager, String input){
        super(algorithmManager);
        this.algorithmManager = algorithmManager;
        setCode();

        this.fromTextField = false;
        preStart(input);
    }

    private void preStart(String input){
        if(this.fromTextField){
            WindowManager.removeController(this.startButton);
            WindowManager.removeController(this.inputTextField);
        }
        this.input = input.endsWith("$")?input:input+"$";
        this.n  = this.input.length();
        this.bwtDisplay.setMatrixSize(this.n + 2, this.n + 1);
        this.saDisplay.setMatrixSize(this.n + 2, this.n + 1);
        this.saDisplay.setSquareText(0,0, "SA");
        this.relationDisplay.setMatrixSize(this.n + 1, 4);
        this.relationDisplay.setSquareText(0, iColumn, "i");
        this.relationDisplay.setSquareText(0, saColumn , "SA[i]");
        this.relationDisplay.setSquareText(0, sColumn, "S");
        this.relationDisplay.setSquareText(0, bwtColumn,"BWT[i]");

        start();
    }

    private void start(){

        this.rotations = new ArrayList<>();
        this.saV = new int[this.n];
        this.lV = new char[this.n];

        StringBuilder start = new StringBuilder(this.input);
        StringBuilder end = new StringBuilder();
        for(int i = 0; i<this.input.length(); i++){
            this.rotations.add(String.valueOf(start) + end);
            end.append(start.charAt(0));
            start.deleteCharAt(0);
        }
        this.rotations.sort(Comparator.naturalOrder());

        for(int i = 0;i<this.input.length();i++){
            boolean suffix = true;
            for(int j= 0;j<this.input.length(); j++){
                if(suffix){
                    this.saDisplay.setSquareText(i + 1, j + 1, this.rotations.get(i).charAt(j));
                }
                if(this.rotations.get(i).charAt(j) == '$'){
                    this.saDisplay.setSquareText(i + 1, 0, (this.input.length() - j - 1));
                    this.saV[i] = (this.input.length() - j - 1);
                    this.relationDisplay.setSquareText(i + 1,saColumn ,(this.input.length() - j - 1));
                    suffix = false;
                }
                this.bwtDisplay.setSquareText(i + 1, j, this.rotations.get(i).charAt(j));
                this.relationDisplay.setSquareText(i + 1, iColumn, i);
                this.relationDisplay.setSquareText(i + 1, sColumn, this.input.charAt(i));
            }
        }

        super.addNextBackAnimateControls(0, 1, 0,0, 0,2);
    }

    private void setCode(){
        this.codeDisplay.addLine("for i := 0 to n - 1 do");
        this.codeDisplay.addLine("    if(SA[i] <> 0)");
        this.codeDisplay.addLine("        L[i] := S[SA[i] - 1]");
        this.codeDisplay.addLine("    else");
        this.codeDisplay.addLine("        L[i] := $");
    }

    private int currentLineNumber = 1;
    private boolean first = true;
    private int it;

    @Override
    protected void nextStep(boolean animate){
        this.codeDisplay.unhighlightEverything();
        this.codeDisplay.highlightLine(this.currentLineNumber);
        this.relationDisplay.unhighlightEverything();

        switch (this.currentLineNumber){
            case 1 -> {
                if(first){
                    it = -1;
                    this.codeDisplay.addVariable("i", it);
                    first = false;
                }
                it++;
                this.codeDisplay.setVariableValue("i", it);
                if(it == this.input.length()){
                    this.currentLineNumber = 6;
                }
                else{
                    this.currentLineNumber = 2;
                }
            }
            case 2 -> {
                this.relationDisplay.highlightBackground(it + 1, iColumn);
                this.relationDisplay.highlightBackground(it + 1, saColumn);
                if(saV[it] != 0){
                    this.currentLineNumber = 3;
                }
                else{
                    this.currentLineNumber = 5;
                }
            }
            case 3 -> {
                lV[it] = input.charAt(saV[it] - 1);
                this.relationDisplay.highlightBackground(it + 1, iColumn);
                this.relationDisplay.highlightBackground(it + 1, saColumn);
                this.relationDisplay.highlightBackground(saV[it], sColumn);
                this.relationDisplay.setSquareText(it + 1, bwtColumn, lV[it]);
                this.relationDisplay.highlightBackground(it + 1, bwtColumn);
                this.currentLineNumber = 1;
            }
            case 4 -> {}
            case 5 -> {
                lV[it] = '$';
                this.relationDisplay.highlightBackground(it + 1, iColumn);
                this.relationDisplay.highlightBackground(this.n, saColumn);
                this.relationDisplay.highlightBackground(n, sColumn);
                this.relationDisplay.setSquareText(it + 1, bwtColumn, lV[it]);
                this.relationDisplay.highlightBackground(it + 1, bwtColumn);
                this.currentLineNumber = 1;
            }
            case 6 -> end();
        }
    }

    private void end(){
        //todo alg popis
        this.codeDisplay.removeVariable("i");

        StringBuilder output = new StringBuilder();
        for (char c : this.lV) {
            output.append(c);
        }

        Button retryButton = new Button();
        retryButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.BWTFromSA));
        LanguageListenerAdder.addLanguageListener("retry", retryButton);
        WindowManager.addController(retryButton,  1 ,1);

        Button decodeButton = new Button();
        decodeButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.BWTDecode, output.toString()));
        LanguageListenerAdder.addLanguageListener("", decodeButton);
        WindowManager.addController(decodeButton, 1,2);

    }
}
