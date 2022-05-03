package com.example.demo2.algorithms.bwt;

import com.example.demo2.algorithmDisplays.CodeDisplay;
import com.example.demo2.algorithmDisplays.WindowManager;
import com.example.demo2.algorithmDisplays.MatrixDisplay;
import com.example.demo2.algorithmDisplays.TextDisplay;
import com.example.demo2.algorithmDisplays.animatableNodes.DisplayType;
import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.algorithmManager.AlgorithmType;
import com.example.demo2.algorithms.Algorithm;
import com.example.demo2.animations.Animation;
import com.example.demo2.animations.AnimationManager;
import com.example.demo2.auxiliary.Algorithms;
import com.example.demo2.multilingualism.LanguageListenerAdder;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BWTSearch extends Algorithm {

    private final AlgorithmManager algorithmManager;
    private final AnimationManager animationManager = new AnimationManager();

    private final Button retryButton = new Button();
    private final Button retryWithInput = new Button();
    private final Button bwtButton = new Button();

    private final Label inputLabel = new Label();
    private final TextField inputTextField = new TextField();
    private final Label patternLabel = new Label();
    private final TextField patternTextField = new TextField();
    private final Button startButton = new Button();
    private final boolean withInputGiven;

    private final MatrixDisplay bwtDisplay = (MatrixDisplay) WindowManager.addDisplay(DisplayType.Matrix, "BWTMatrix", 3);
    private final MatrixDisplay matrixDisplay = (MatrixDisplay) WindowManager.addDisplay(DisplayType.Matrix, "", 1);;
    private final CodeDisplay codeDisplay = (CodeDisplay) WindowManager.addDisplay(DisplayType.Code, "", 2);;
    private final TextDisplay textDisplay = (TextDisplay) WindowManager.addDisplay(DisplayType.Text, "", 2);;

    private String input;
    private String pattern;
    private ArrayList<Pair<Character, Integer>> c = new ArrayList<>();
    private char[] l;

    private static final int iColumn = 0;
    private static final int inputColumn = 1;
    private static final int saColumn = 2;

    private final ArrayList<String> bwt = new ArrayList<>();
    private int[] suffixArray;

    public BWTSearch(AlgorithmManager algorithmManager) {
        super(algorithmManager);
        this.algorithmManager = algorithmManager;
        this.withInputGiven = false;

        WindowManager.addController(this.inputLabel, 0,0);
        LanguageListenerAdder.addLanguageListener("string", this.inputLabel);

        this.inputTextField.setText("banana");
        WindowManager.addController(this.inputTextField, 0,1);
        this.inputTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(!newValue.contains("\\$")){
                this.inputTextField.setText(newValue.replaceAll("\\$", ""));
            }
        }));

        WindowManager.addController(this.patternLabel, 1,0);
        LanguageListenerAdder.addLanguageListener("pattern", this.patternLabel);

        this.patternTextField.setText("na");
        WindowManager.addController(this.patternTextField, 1, 1);

        WindowManager.addController(this.startButton, 2,0);
        LanguageListenerAdder.addLanguageListener("startAlgorithm", this.startButton);
        this.startButton.setOnAction(actionEvent -> preStart(this.inputTextField.getText(), this.patternTextField.getText()));

        setCode();
    }

    public BWTSearch(AlgorithmManager algorithmManager, String input){
        super(algorithmManager);
        this.algorithmManager = algorithmManager;
        this.withInputGiven = true;

        WindowManager.addController(this.inputLabel, 0,0);
        LanguageListenerAdder.addLanguageListener("string", this.inputLabel);

        this.inputTextField.setText(input);
        this.inputTextField.setDisable(true);

        WindowManager.addController(this.patternLabel, 1,0);
        LanguageListenerAdder.addLanguageListener("pattern", this.patternLabel);

        this.patternTextField.setText("");
        WindowManager.addController(this.patternTextField, 1, 1);

        WindowManager.addController(this.startButton, 2,0);
        LanguageListenerAdder.addLanguageListener("startAlgorithm", this.startButton);
        this.startButton.setOnAction(actionEvent -> preStart(input, this.patternTextField.getText()));

        this.input = (input.endsWith("$"))? input : input + "$";
        setCode();
        setMatrices();
    }

    private void preStart(String input, String pattern){

        this.inputTextField.setDisable(true);
        this.patternTextField.setDisable(true);

        WindowManager.removeController(this.startButton);

        this.input = (input.endsWith("$"))? input : input + "$";
        this.pattern = pattern;

        setMatrices();
        start();
    }

    private void setMatrices(){
        //bwtDisplay:
        this.bwtDisplay.setMatrixSize(this.input.length() + 1, this.input.length() + 1);
        this.bwtDisplay.setSquareText(0,1, "F");
        this.bwtDisplay.setSquareText(0, input.length(), "L");

        StringBuilder start = new StringBuilder(this.input);
        StringBuilder end = new StringBuilder();
        for(int i = 0; i<this.input.length(); i++){
            this.bwt.add(String.valueOf(start) + end);
            end.append(start.charAt(0));
            start.deleteCharAt(0);
        }
        this.bwt.sort(Comparator.naturalOrder());
        for(int i = 0; i<this.input.length(); i++){
            this.bwtDisplay.setSquareText(i + 1, 0, i);
            for(int j = 0;j<this.input.length();j++){
                this.bwtDisplay.setSquareText(i + 1, j + 1, this.bwt.get(i).charAt(j));
                if(j != this.input.length() - 1){
                    this.bwtDisplay.setSquareTextColor(i + 1, j + 1, Color.GRAY);
                }
            }
        }
        //matrixDisplay:
        this.matrixDisplay.setMatrixSize(this.input.length() + 1, 3);
        this.matrixDisplay.setRowText(0, "i", "S[i]", "SA[i]");
        this.suffixArray = Algorithms.suffixArray(this.input);
        for(int i = 0;i<this.input.length();i++){
            this.matrixDisplay.setSquareText(i + 1, iColumn, i);
            this.matrixDisplay.setSquareText(i + 1, inputColumn, this.input.charAt(i));
            this.matrixDisplay.setSquareText(i + 1, saColumn, this.suffixArray[i]);
        }
    }

    private void setCode(){
        this.codeDisplay.addLine("top := 0");
        this.codeDisplay.addLine("bottom := n - 1");
        this.codeDisplay.addLine("for it := m - 1 to 0 do");
        this.codeDisplay.addLine("    c := P[k]");
        this.codeDisplay.addLine("    top := L.C(c) + L.rank(c, top)");
        this.codeDisplay.addLine("    bottom := L.C(c) + L.rank(c, bottom)");
        this.codeDisplay.addLine("    if top > bottom");
        this.codeDisplay.addLine("        return");
        this.codeDisplay.addLine("return [SA[i], SA[i+1], ..., SA[j]]");
    }

    private void start(){
        for(int i = 0;i<11;i++){
            firstTime[i] = true;
        }
        for(int i = 0;i<input.length();i++){
            boolean contains = false;
            for(Pair<Character, Integer> pair:this.c){
                if(pair.getKey() == input.charAt(i)){
                    contains = true;
                    break;
                }
            }
            if(!contains){
                c.add(new Pair<>(input.charAt(i), 0));
            }
        }
        c.sort(Comparator.comparing(Pair::getKey));
        for(int i = 0;i<input.length();i++){
            for(int j = 0;j<this.c.size();j++){
                if(this.c.get(j).getKey() > this.input.charAt(i)){
                    this.c.set(j, new Pair<>(this.c.get(j).getKey(), this.c.get(j).getValue() + 1));
                }
            }
        }
        l = new char[input.length()];
        for(int i = 0;i<this.input.length();i++){
            l[i] = bwt.get(i).charAt(this.input.length() - 1);
        }
        System.out.println(c);
        System.out.println(Arrays.toString(l));
        super.addNextBackAnimateControls(3,1,3,0,3,2);
        super.backStepButton.setDisable(true);
    }

    private boolean [] firstTime = new boolean[11];
    private int step = 0;
    private int highestStep = 0;
    private int kt;
    private int top;
    private int bottom;
    private char ct;
    private int endInt = -1;
    private int currentLineNumber = 1;

    @Override
    protected void nextStep(boolean animate){
        Animation animation = this.animationManager.getAnimation(step);

        if(highestStep == step) {
            this.matrixDisplay.unhighlightEverything(animation);
            this.bwtDisplay.unhighlightEverything(animation);
            this.codeDisplay.unhighlightEverything(animation);
            this.codeDisplay.highlightLine(animation, this.currentLineNumber);

            switch (this.currentLineNumber){
                case 1 ->{
                    this.codeDisplay.addVariable(animation, "top", 0);
                    this.top = 0;
                    this.currentLineNumber = 2;
                }
                case 2 ->{
                    this.bottom = input.length() - 1;
                    this.codeDisplay.addVariable(animation, "bottom", bottom);
                    this.currentLineNumber = 3;
                }
                case 3 ->{
                    if(this.firstTime[3]){
                        kt = this.pattern.length();
                        this.codeDisplay.addVariable(animation,"k", kt);
                        this.firstTime[3] = false;
                    }
                    else{
                        this.codeDisplay.removeVariable(animation,"c");
                    }
                    kt--;
                    this.codeDisplay.setVariableValue("k", kt);
                    if(kt == -1){
                        this.currentLineNumber = 9;
                    }
                    else{
                        this.currentLineNumber = 4;
                    }
                }
                case 4 ->{
                    this.ct = this.pattern.charAt(kt);
                    this.codeDisplay.removeVariable("c");
                    this.codeDisplay.addVariable(animation, "c", this.pattern.charAt(kt));
                    this.currentLineNumber = 5;
                }
                case 5 ->{
                    int tmp = 0;
                    for(Pair<Character, Integer> pair:this.c){
                        if(pair.getKey() == ct){
                            tmp = pair.getValue();
                        }
                    }
                    for(int i = 0; i<= top; i++){
                        if(l[i] == ct){
                            tmp++;
                        }
                    }
                    top = tmp - 1;
                    this.codeDisplay.setVariableValue("top", top);
                    this.codeDisplay.highlightVariable(animation, "top");
                    this.currentLineNumber = 6;
                }
                case 6 ->{
                    int tmp = 0;
                    for(Pair<Character, Integer> pair:this.c){
                        if(pair.getKey() == ct){
                            tmp = pair.getValue();
                        }
                    }
                    for(int i = 0; i<= bottom; i++){
                        if(l[i] == ct){
                            tmp++;
                        }
                    }
                    bottom = tmp - 1;
                    this.codeDisplay.setVariableValue("bottom", bottom);
                    this.codeDisplay.highlightVariable(animation, "bottom");
                    this.currentLineNumber = 7;
                }
                case 7 ->{
                    for(int i = top;i<=bottom;i++){
                        for(int j = 0;j<this.pattern.length() - kt;j++){
                            this.bwtDisplay.highlightBackground(animation, i + 1, j + 1);
                        }
                    }
                    this.codeDisplay.highlightVariable(animation, "top");
                    this.codeDisplay.highlightVariable(animation, "bottom");
                    if(top > bottom){
                        this.currentLineNumber = 8;
                    }
                    else{
                        this.currentLineNumber = 3;
                    }
                }
                case 8 ->{
                    //todo vypis ze nic nenaslo
                    //vlozit to textu, ze sa nic nenaslo
                    end();
                }
                case 9 ->{
                    for(int i = top; i<= bottom; i++){
                        this.matrixDisplay.highlightBackground(animation, i + 1, saColumn);
                    }
                    //todo. mozno to dat to cyklu, pri vypise
                    //vlozit do textu, co sa naslo, a k comu to pasuje
                }
                case 10 -> end();
            }




            //animation.addAnimatable(AnimationType.AppearAnimation, edge);

        }

        if(animate){
            animationManager.executeAnimation(step, true);
        }
        else{
            animation.setForward(true);
            animation.endAnimation();
        }
        if(step == endInt){
            end();
        }
        step++;
        highestStep = Math.max(highestStep, step);

        super.backStepButton.setDisable(false);
    }

    @Override
    protected void backStep(boolean animate){
        if(step > 0) {
            step--;
            if (animate) {
                animationManager.executeAnimation(step, false);
            } else {
                animationManager.endAnimation(step, false);
            }
        }
        if(step == 0){
            super.backStepButton.setDisable(true);
        }
        super.nextStepButton.setDisable(false);
    }

    /*
    private int currentLineNumber;
    private String l;
    private int[] cL;
    private ArrayList<Boolean> firstTime = new ArrayList<>();
    private int k;
    private int iV;
    private int jV;


    public void nextStep(boolean animatable){

        this.codeDisplay.unhighlightEverything();
        this.codeDisplay.highlightLine(this.currentLineNumber);
        this.matrixDisplay.unhighlightEverything();

        switch (this.currentLineNumber){
            case 1 ->{
                this.codeDisplay.addVariable("i", 0);
                this.currentLineNumber = 2;
            }
            case 2 ->{
                this.codeDisplay.addVariable("j", 0);
                this.currentLineNumber = 3;
            }
            case 3 ->{
                if(this.firstTime.get(3)){
                    k = this.pattern.length();
                    this.codeDisplay.addVariable("k", k);
                }
                else{
                    this.codeDisplay.removeVariable("c");
                }
                k--;
                this.codeDisplay.setVariableValue("k", k);
                //this.matrixDisplay.highlightBackground(k + 1, jColumn);
                if(k == -1){
                    this.currentLineNumber = 9;
                }
                else{
                    this.currentLineNumber = 4;
                }
            }
            case 4 ->{
                this.codeDisplay.addVariable("c", this.pattern.charAt(k));
                //this.matrixDisplay.highlightBackground(k + 1, jColumn);
                //this.matrixDisplay.highlightBackground(k + 1, pColumn);
                this.currentLineNumber = 5;
            }
            case 5 ->{
                //todo iV
                this.currentLineNumber = 6;
            }
            case 6 ->{
                //todo jV
                this.currentLineNumber = 7;
            }
            case 7 ->{
                if(iV > jV){
                    this.currentLineNumber = 8;
                }
                else{
                    this.currentLineNumber = 3;
                }
            }
            case 8 ->{
                //todo vypis ze nic nenaslo
                end();
            }
            case 9 ->{
                //todo. mozno to dat to cyklu, pri vypise
            }
            case 10 -> end();
        }
    }
*/

    private void end(){



        retryButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.BWTSearch));
        LanguageListenerAdder.addLanguageListener("retry", retryButton);
        WindowManager.addController(retryButton, 4,0);

        retryWithInput.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.BWTSearch, this.input));
        LanguageListenerAdder.addLanguageListener("retryWithInput", retryWithInput);
        WindowManager.addController(retryWithInput, 4,1);

        bwtButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.BWT));
        LanguageListenerAdder.addLanguageListener("returnToBWT", bwtButton);
        WindowManager.addController(bwtButton, 4,2);
    }
}
