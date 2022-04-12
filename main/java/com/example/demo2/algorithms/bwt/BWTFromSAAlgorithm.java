package com.example.demo2.algorithms.bwt;

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

public class BWTFromSAAlgorithm extends Algorithm {

    private final AlgorithmManager algorithmManager;

    private String input;
    private ArrayList<Integer> suffixArray;

    private TextField inputTextField;
    private Button startButton;

    private MatrixDisplay bwtDisplay;
    private MatrixDisplay saDisplay;
    private MatrixDisplay relationDisplay;
    private TextDisplay textDisplay;

    private int iterator;
    private ArrayList<String> rotations;

    public BWTFromSAAlgorithm(AlgorithmManager algorithmManager) {
        super(algorithmManager);
        this.algorithmManager = algorithmManager;

        this.inputTextField = new TextField();
        super.addController(this.inputTextField, 2,0);
        this.startButton = new Button();
        super.addController(this.startButton, 3,0);

        LanguageListenerAdder.addLanguageListener("startAlgorithm", this.startButton);
        this.startButton.setOnAction(actionEvent -> start(this.inputTextField.getText()));
    }

    public BWTFromSAAlgorithm(AlgorithmManager algorithmManager, String input){
        super(algorithmManager);
        this.algorithmManager = algorithmManager;
        start(input);
    }

    private void start(String input){
        this.input = input.endsWith("$")?input:input+"$";
        this.rotations = new ArrayList<>();
        this.suffixArray = new ArrayList<>();
        StringBuilder start = new StringBuilder(this.input);
        StringBuilder end = new StringBuilder();
        for(int i = 0; i<this.input.length(); i++){
            this.rotations.add(String.valueOf(start) + end);
            end.append(start.charAt(0));
            start.deleteCharAt(0);
        }
        this.rotations.sort(Comparator.naturalOrder());

        this.iterator = 0;

        this.bwtDisplay = (MatrixDisplay) super.addDisplay(DisplayManager.DisplayType.Matrix, "", 2);
        this.bwtDisplay.setMatrixSize(input.length() + 1, input.length() + 1);
        this.saDisplay = (MatrixDisplay) super.addDisplay(DisplayManager.DisplayType.Matrix, "", 2);
        this.saDisplay.setMatrixSize(input.length() + 2, input.length() + 2);
        this.saDisplay.setSquareText(0,0, "SA");

        this.relationDisplay = (MatrixDisplay) super.addDisplay(DisplayManager.DisplayType.Matrix, "", 1);
        this.relationDisplay.setMatrixSize(this.input.length() + 1, 4);
        this.relationDisplay.setSquareText(0,0, "i");
        this.relationDisplay.setSquareText(0, 1 , "SA[i]");
        this.relationDisplay.setSquareText(0, 2, "S");
        this.relationDisplay.setSquareText(0,3,"BWT[i]");

        this.textDisplay = (TextDisplay) super.addDisplay(DisplayManager.DisplayType.Text, "", 2);

        for(int i = 0;i<this.input.length();i++){
            boolean suffix = true;
            for(int j= 0;j<this.input.length(); j++){
                if(suffix){
                    this.saDisplay.setSquareText(i + 1, j + 1, this.rotations.get(i).charAt(j));
                }
                if(this.rotations.get(i).charAt(j) == '$'){
                    this.saDisplay.setSquareText(i + 1, 0, (this.input.length() - j - 1));
                    this.suffixArray.add((this.input.length() - j - 1));
                    this.relationDisplay.setSquareText(i + 1,1 ,(this.input.length() - j - 1));
                    suffix = false;
                }
                this.bwtDisplay.setSquareText(i, j, this.rotations.get(i).charAt(j));
                this.relationDisplay.setSquareText(i + 1, 0, i);
                this.relationDisplay.setSquareText(i + 1, 2, this.input.charAt(i));
            }
        }

        super.removeController(this.inputTextField);
        super.removeController(this.startButton);
        super.addNextStepButton(0,0);
    }

    public void nextStep(){
        System.out.println(this.suffixArray);
        if(suffixArray.get(iterator) == 0){
            this.relationDisplay.setSquareText(this.iterator + 1, 3,'$' );
        }
        else {
            this.relationDisplay.setSquareText(this.iterator + 1, 3,
                    this.input.charAt(this.suffixArray.get(this.iterator) - 1) );
        }
        iterator++;
        if(this.iterator == this.input.length()){
            System.out.println("koniec");
        }
        /*
        chceme asi dve matice, a este mat
        niekde zobrazeny text
        kde budu naraz

        chceme zobrazit dve matice, kde budeme
        a potom treba este niekde zobrazit text, co moze
        byt nad BWT, prva druha zobrazit to, co
        je, a potom ukazat, co znamena posunut
        sa o jednu poziciu dolava

        alebo mozme mat este jednu, kde bude iba
        slovo, a indexy, a tiez to, ako bude vyzerat bwt
        a postupne to budeme zobrazovat
        treba urobit este oddiely, a asi to trochu prepocitat
        tak, aby to sedelo
         */
    }
}
