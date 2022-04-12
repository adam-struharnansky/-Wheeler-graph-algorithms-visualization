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

import java.util.Arrays;
import java.util.HashSet;

public class BWTDecodeAlgorithm extends Algorithm {

    private final AlgorithmManager algorithmManager;
    private final TextDisplay textDisplay;
    private final MatrixDisplay matrixDisplay;
    private String input;
    private int [] inputIndexes;
    private String sortedInput;
    private int [] sortedInputIndexes;

    private TextField inputTextField;
    private Button startButton;

    private boolean indexed = false;
    private int startIndex, endIndex, decoded;

    public BWTDecodeAlgorithm(AlgorithmManager algorithmManager) {
        super(algorithmManager);
        this.matrixDisplay = (MatrixDisplay) super.addDisplay(DisplayManager.DisplayType.Matrix, "", 1);
        this.textDisplay = (TextDisplay) super.addDisplay(DisplayManager.DisplayType.Text, "", 1);
        this.algorithmManager = algorithmManager;
        //todo - vyriesit, aby tu bolo maximalne jedno $
        this.inputTextField = new TextField();
        super.addController(this.inputTextField, 1,0);

        this.startButton = new Button();
        LanguageListenerAdder.addLanguageListener("startAlgorithm", this.startButton);
        this.startButton.setOnAction(actionEvent -> start(this.inputTextField.getText()));
        super.addController(this.startButton, 2,0);
    }

    public BWTDecodeAlgorithm(AlgorithmManager algorithmManager, String input) {
        super(algorithmManager);
        this.matrixDisplay = (MatrixDisplay) super.addDisplay(DisplayManager.DisplayType.Matrix, "", 1);
        this.textDisplay = (TextDisplay) super.addDisplay(DisplayManager.DisplayType.Text, "", 1);
        this.algorithmManager = algorithmManager;
        start(input);
    }

    private void start(String input){
        //todo - priadat popis
        if(!input.contains("$")){
            input = input + "$";
            //todo - napisat uzivatelovi, ze sme mu toto tam pridali, a vysledkom bude iba rotacia vstupu
        }
        this.matrixDisplay.setMatrixSize(input.length(), 2);
        this.input = input;
        this.sortedInput = "";
        char [] charArrayInput = input.toCharArray();
        Arrays.sort(charArrayInput);
        StringBuilder stringBuilderSorted = new StringBuilder();
        for(int i = 0;i<input.length();i++){
            this.matrixDisplay.setSquareText(i, 1, this.input.charAt(i));
            stringBuilderSorted.append(charArrayInput[i]);
            this.matrixDisplay.setSquareText(i, 0, charArrayInput[i]);
        }
        this.sortedInput = stringBuilderSorted.toString();
        //todo - pridat popis alg
        super.removeController(this.startButton);
        super.removeController(this.inputTextField);
        super.addNextStepButton(0,0);
    }

    @Override
    public void nextStep(){
        if(!this.indexed){
            //zaindexovat vsetky prvky v tejto matici - doteraz nemali ziaden index
            HashSet<Character> characters = new HashSet<>();
            for(int i = 0;i<this.input.length();i++){
                characters.add(this.input.charAt(i));
            }
            this.sortedInputIndexes = new int[this.sortedInput.length()];
            this.inputIndexes = new int[this.input.length()];
            for(Character character:characters){
                int index = 1, indexS = 1;
                for(int i = 0;i<this.input.length();i++){
                    if(this.input.charAt(i) == character){
                        this.matrixDisplay.setSquareIndex(i,1,index);
                        this.inputIndexes[i] = index;
                        index++;
                    }
                    if(this.sortedInput.charAt(i) == character){
                        this.matrixDisplay.setSquareIndex(i,0,indexS);
                        this.sortedInputIndexes[i] = indexS;
                        indexS++;
                    }
                }
            }
            this.indexed = true;
            this.decoded = 0;
            this.startIndex = -1;
            return;
        }
        if(this.startIndex == -1){
            for(int i = 0;i<this.input.length();i++){
                if(this.input.charAt(i) == '$'){
                    this.startIndex = i;
                    this.endIndex = i;
                    break;
                }
            }
            this.matrixDisplay.setSquareColor(this.startIndex, 1, Color.RED);
            return;
        }
        this.matrixDisplay.setSquareColor(this.startIndex, 1, Color.BLACK);
        this.matrixDisplay.setSquareColor(this.startIndex, 0, Color.BLACK);
        this.matrixDisplay.setSquareColor(this.endIndex, 1, Color.BLACK);

        this.matrixDisplay.removeArrow(this.startIndex, 1, this.startIndex, 0);
        this.matrixDisplay.removeArrow(this.startIndex, 0, this.endIndex, 1);

        this.startIndex = this.endIndex;
        for(int i = 0;i<this.input.length();i++){
            if(this.input.charAt(i) == this.sortedInput.charAt(this.startIndex)
                    && this.inputIndexes[i] == this.sortedInputIndexes[this.startIndex]){
                this.endIndex = i;
            }
        }
        if(this.decoded == this.input.length()){
            this.matrixDisplay.addArrow(this.startIndex, 1, this.startIndex, 0);
            end();
        }
        this.matrixDisplay.addArrow(this.startIndex, 0, this.endIndex, 1);
        this.matrixDisplay.setArrowColor(this.startIndex, 0, this.endIndex, 1, Color.BLUE);
        this.matrixDisplay.setSquareColor(this.startIndex, 0, Color.BLUE);
        this.matrixDisplay.setSquareColor(this.endIndex, 1, Color.BLUE);
        this.matrixDisplay.addArrow(this.startIndex, 1, this.startIndex, 0);
        this.matrixDisplay.setArrowColor(this.startIndex, 1, this.startIndex, 0, Color.RED);
        this.matrixDisplay.setSquareColor(this.startIndex, 1, Color.RED);
        this.decoded++;
    }

    private void end(){
        //todo - popis alg
        super.removeNextStepButton();

        Button retryDecode = new Button();
        retryDecode.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.BWTDecode));
        LanguageListenerAdder.addLanguageListener("", retryDecode);
        super.addController(retryDecode, 0,0);

        Button bwtButton = new Button();
        bwtButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.BWT));
        LanguageListenerAdder.addLanguageListener("", bwtButton);
        super.addController(bwtButton, 1,0);

        //todo - urobit uvodnu obrazovku, a odkazat sa tam
    }
}
