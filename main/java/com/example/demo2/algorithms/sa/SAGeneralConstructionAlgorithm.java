package com.example.demo2.algorithms.sa;

import com.example.demo2.algorithmDisplays.DisplayManager;
import com.example.demo2.algorithmDisplays.MatrixDisplay;
import com.example.demo2.algorithmDisplays.TextDisplay;
import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.algorithms.Algorithm;
import com.example.demo2.multilingualism.LanguageListenerAdder;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Comparator;

public class SAGeneralConstructionAlgorithm extends Algorithm {

    private final AlgorithmManager algorithmManager;
    private final TextDisplay textDisplay;
    private final MatrixDisplay matrixDisplay;

    private String input;
    private ArrayList<String> suffixes;

    private final TextField inputTextField;
    private final Button startButton;
    private Button endButton;

    public SAGeneralConstructionAlgorithm(AlgorithmManager algorithmManager) {
        super(algorithmManager);
        this.algorithmManager = algorithmManager;
        this.matrixDisplay = (MatrixDisplay) super.addDisplay(DisplayManager.DisplayType.Matrix, "", 1);
        this.textDisplay = (TextDisplay) super.addDisplay(DisplayManager.DisplayType.Text, "", 1);

        this.inputTextField = new TextField();
        super.addController(this.inputTextField, 0,0);

        this.startButton = new Button();
        LanguageListenerAdder.addLanguageListener("startAlgorithm", this.startButton);
        //todo - povedat cloveku pripadne, ze vsetky tieto boli odstranene
        this.startButton.setOnAction(actionEvent -> start(this.inputTextField.getText().replaceAll("\\$", "")));
        super.addController(this.startButton, 1,0);
    }

    private void start(String input){
        this.input = input + '$';
        this.suffixes = new ArrayList<>();
        this.matrixDisplay.setMatrixSize(this.input.length() + 1, 4);
        this.matrixDisplay.setSquareText(0,1, "i");
        this.matrixDisplay.setSquareText(0,2, "S[i,n]");
        StringBuilder end = new StringBuilder();
        for(int i = this.input.length() - 1; i>=0; i--){
            end.insert(0, this.input.charAt(i));
            this.suffixes.add(end.toString());
        }
        for(int i = 0;i<this.input.length();i++){
            this.matrixDisplay.setSquareText(i + 1, 1, i);
            this.matrixDisplay.setSquareText(i + 1, 2, suffixes.get(i));
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
        this.matrixDisplay.setSquareText(0,0, "i");
        this.matrixDisplay.setSquareText(0,1, "LCS[i]");
        this.matrixDisplay.setSquareText(0,2, "SA[i]");
        this.matrixDisplay.setSquareText(0,3, "S[SA[i], n-1]");
        this.suffixes.sort(Comparator.naturalOrder());
        for(int i = 0; i<this.input.length(); i++){
            this.matrixDisplay.setSquareText(i + 1, 0, i);
            if(i != 0){
                int lcs = 0;
                while(lcs < this.suffixes.get(i).length() && lcs < this.suffixes.get(i - 1).length()){
                    if(this.suffixes.get(i).charAt(lcs) == this.suffixes.get(i - 1).charAt(lcs)){
                        lcs++;
                    }
                    else{
                        break;
                    }
                }
                this.matrixDisplay.setSquareText(i + 1, 1, lcs);
            }
            else{
                this.matrixDisplay.setSquareText(i + 1, 1, "0");
            }
            this.matrixDisplay.setSquareText(i + 1, 2, this.input.length() - this.suffixes.get(i).length());
            this.matrixDisplay.setSquareText(i + 1, 3, this.suffixes.get(i));
        }
        super.removeController(this.endButton);

        //todo - pripisat dalsie veci, co mozme po tomto robit
        //alebo mozme celu vec hodit do ejdneho riadku, toto nemsui byt ako matica.. nic sa s tymto nebude robit
    }

}
