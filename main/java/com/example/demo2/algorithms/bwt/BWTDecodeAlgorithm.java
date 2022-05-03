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
import java.util.Arrays;
import java.util.Comparator;

public class BWTDecodeAlgorithm extends Algorithm {

    private final AlgorithmManager algorithmManager;

    private final MatrixDisplay matrixDisplay;
    private final CodeDisplay codeDisplay;
    private final TextDisplay textDisplay;

    private final static int cColumn = 0;
    private final static int ccColumn = 1;
    private final static int iColumn = 2;
    private final static int lfColumn = 3;
    private final static int fColumn = 4;
    private final static int lColumn = 5;
    private final static int sColumn = 6;

    private final ArrayList<Character> alphabet = new ArrayList<>();
    private String input;
    String sortedInput;

    private final TextField inputTextField = new TextField("annb$aa");
    private final Button startButton = new Button();

    public BWTDecodeAlgorithm(AlgorithmManager algorithmManager) {
        super(algorithmManager);
        this.matrixDisplay = (MatrixDisplay) WindowManager.addDisplay(DisplayType.Matrix, "", 1);
        this.codeDisplay = (CodeDisplay) WindowManager.addDisplay(DisplayType.Code, "", 1);
        setCode();
        this.textDisplay = (TextDisplay) WindowManager.addDisplay(DisplayType.Text, "", 1);
        this.algorithmManager = algorithmManager;
        this.steps = new ArrayList<>();

        WindowManager.addController(this.inputTextField, 0,0);

        LanguageListenerAdder.addLanguageListener("startAlgorithm", this.startButton);
        this.startButton.setOnAction(actionEvent -> manualStart(this.inputTextField.getText()));
        WindowManager.addController(this.startButton, 1,0);
    }

    public BWTDecodeAlgorithm(AlgorithmManager algorithmManager, String input) {
        super(algorithmManager);
        this.matrixDisplay = (MatrixDisplay) WindowManager.addDisplay(DisplayType.Matrix, "", 1);
        this.codeDisplay = (CodeDisplay) WindowManager.addDisplay(DisplayType.Code, "", 1);
        setCode();
        this.textDisplay = (TextDisplay) WindowManager.addDisplay(DisplayType.Text, "", 1);
        this.algorithmManager = algorithmManager;
        this.steps = new ArrayList<>();
        start(input);
    }

    private void setCode(){
        this.codeDisplay.addLine("TODO: C = [sigma]");
        this.codeDisplay.addLine("for i := 0 to n - 1 do");
        this.codeDisplay.addLine("    C[L[i]] := C[L[i]] + 1");
        this.codeDisplay.addLine("sum := 0");
        this.codeDisplay.addLine("for i := 0 to sigma - 1");
        this.codeDisplay.addLine("    cnt := C[i]");
        this.codeDisplay.addLine("    C[i] := sum");
        this.codeDisplay.addLine("    sum := sum + cnt");
        this.codeDisplay.addLine("");
        this.codeDisplay.addLine("for i := 0 to n - 1 do");
        this.codeDisplay.addLine("    C[L[i]] := C[L[i]] + 1");
        this.codeDisplay.addLine("    LF[i] := C[L[i]] - 1");
        this.codeDisplay.addLine("");
        this.codeDisplay.addLine("S[n - 1] := $");
        this.codeDisplay.addLine("j  := 0");
        this.codeDisplay.addLine("for i := n - 2 to 0 do");
        this.codeDisplay.addLine("    S[i] := L[j]");
        this.codeDisplay.addLine("    j := LF[j]");
    }

    private void manualStart(String input){
        WindowManager.removeController(this.inputTextField);
        WindowManager.removeController(this.startButton);
        start(input);
    }

    private void start(String input){
        //todo - pridat popis - co znamena kazde z tych poli, ako sa v nich odkazujeme, ...

        this.input = input;
        this.matrixDisplay.setMatrixSize(input.length() + 1, 7);
        this.matrixDisplay.setRowText(0, "c", "C[c]", "i", "", "", "L[i]", "");

        for(int i = 0;i<this.input.length();i++){
            boolean containsCharacter = false;
            for (Character character : this.alphabet) {
                if (character == this.input.charAt(i)) {
                    containsCharacter = true;
                    break;
                }
            }
            if(!containsCharacter){
                this.alphabet.add(this.input.charAt(i));
            }
        }
        this.alphabet.sort(Comparator.naturalOrder());
        for(int i = 0;i<this.alphabet.size();i++){
            this.matrixDisplay.setSquareText(i + 1, cColumn, this.alphabet.get(i));
            this.matrixDisplay.setSquareText(i + 1, ccColumn, 0);
        }

        char [] charArrayInput = input.toCharArray();
        Arrays.sort(charArrayInput);

        StringBuilder stringBuilderSorted = new StringBuilder();
        for(int i = 0; i < this.input.length(); i++){
            this.matrixDisplay.setSquareText(i + 1, iColumn, i);
            this.matrixDisplay.setSquareText(i + 1, lColumn, this.input.charAt(i));
            stringBuilderSorted.append(charArrayInput[i]);
        }
        this.sortedInput = stringBuilderSorted.toString();
        this.codeDisplay.highlightLine(1);
        this.currentLineNumber = 2;

        this.firstTime = new ArrayList<>();
        for(int i = 0;i<19;i++){
            this.firstTime.add(true);
        }
        this.cV = new int[alphabet.size()];
        this.lfV = new int[input.length()];
        this.sV = new char[input.length()];
        //todo - skontrolovat, ako to ma fungovat s tymi krokmi, kde zacat, a kde skoncit
        this.currentStep = 0;
        this.possibleNextStep = true;
        this.possibleBackStep = false;

        super.addNextBackAnimateControls(0,1,0,0,0,2);

        checkBoundaries();
    }

    private final ArrayList<Step> steps;
    private int currentStep;

    private ArrayList<Boolean> firstTime;
    //nastavit tieto hodnoty na -1
    private int it, sum, cnt, jt;
    private int[] cV;
    private int[] lfV;
    private char[] sV;
    private int currentLineNumber = 0;

    /*
    treba to ulozit na zaciatku
     */
    private class Step{
        private final int currentLineNumberS;
        private final ArrayList<Boolean> firstTimeS;
        private final int itS, sumS, cntS, jtS;
        private final int[] cVS;
        private final int[] lfVS;
        private final char[] sVS;
        private final MatrixDisplay.MatrixMemento matrixMemento;
        Step(){
            this.currentLineNumberS = currentLineNumber;
            this.matrixMemento = matrixDisplay.saveToMemento();
            this.firstTimeS = new ArrayList<>(firstTime);
            this.itS = it;
            this.sumS = sum;
            this.cntS = cnt;
            this.jtS = jt;
            this.cVS = new int[cV.length];
            System.arraycopy(cV, 0, this.cVS, 0, cV.length);
            this.lfVS = new int[lfV.length];
            System.arraycopy(lfV, 0, this.lfVS, 0, lfV.length);
            this.sVS = new char[sV.length];
            System.arraycopy(sV, 0, this.sVS, 0, sV.length);
        }
    }

    private boolean possibleNextStep, possibleBackStep;

    private void checkBoundaries(){
        if(currentStep <= 0){
            this.possibleBackStep = false;
            super.nextStepButton.setDisable(true);
        }
        else{
            this.possibleBackStep = true;
            super.backStepButton.setDisable(false);
        }
        if(this.possibleNextStep){
            super.nextStepButton.setDisable(false);
        }
        else{
            super.nextStepButton.setDisable(true);
            end();
        }
    }

    public void backStep(boolean animate){
        if(this.possibleBackStep) {
            this.currentStep--;
            executeStep(animate);
        }
        checkBoundaries();
    }

    public void nextStep(boolean animate){
        if(this.possibleNextStep) {
            this.currentStep++;
            executeStep(false);
        }
        checkBoundaries();
    }

    public void executeStep(boolean animate){
        this.codeDisplay.unhighlightEverything();
        this.matrixDisplay.unhighlightEverything();

        if(this.currentStep < this.steps.size()){
            this.currentLineNumber = this.steps.get(this.currentStep).currentLineNumberS;
            firstTime = new ArrayList<>(this.steps.get(this.currentStep).firstTimeS);
            it = this.steps.get(this.currentStep).itS;
            sum = this.steps.get(this.currentStep).sumS;
            cnt = this.steps.get(this.currentStep).cntS;
            jt = this.steps.get(this.currentStep).jtS;
            System.arraycopy(this.steps.get(this.currentStep).cVS, 0,cV , 0, cV.length);
            System.arraycopy(this.steps.get(this.currentStep).lfVS, 0, lfV, 0, lfV.length);
            System.arraycopy(this.steps.get(this.currentStep).sVS, 0, sV, 0, sV.length);
            this.matrixDisplay.restoreFromMemento(this.steps.get(this.currentStep).matrixMemento);
        }
        else{
            this.steps.add(new Step());
        }

        this.codeDisplay.highlightLine(this.currentLineNumber);

        switch (this.currentLineNumber){
            case 2 -> {//for i = 0; i<n-1
                if(this.firstTime.get(2)){
                    it = -1;
                    this.codeDisplay.addVariable("i", 0);
                    this.firstTime.set(2, false);
                }
                it++;
                this.codeDisplay.setVariableValue("i", it);
                if(it == this.input.length()){
                    this.currentLineNumber = 4;
                }
                else{
                    this.matrixDisplay.highlightBackground(it + 1, iColumn);
                    this.currentLineNumber = 3;
                }
            }
            case 3 -> {//C[L[i]] = C[L[i]] + 1
                this.matrixDisplay.highlightBackground(it + 1, iColumn);
                this.matrixDisplay.highlightBackground(it + 1, lColumn);
                for(int i = 0;i<this.alphabet.size();i++){
                    if(alphabet.get(i) == this.input.charAt(it)){
                        this.matrixDisplay.highlightBackground(i + 1, ccColumn);
                        cV[i] = cV[i] + 1;
                        this.matrixDisplay.setSquareText(i + 1, ccColumn, cV[i]);
                        this.matrixDisplay.highlightBackground(i + 1, cColumn);
                    }
                }
                this.currentLineNumber = 2;
            }
            case 4 -> {
                this.codeDisplay.removeVariable("i");
                this.codeDisplay.addVariable("sum", 0);
                sum = 0;
                currentLineNumber = 5;
            }
            case 5 -> {
                if(this.firstTime.get(5)){
                    it = -1;
                    this.codeDisplay.addVariable("i", 0);
                    this.firstTime.set(5, false);
                }
                it++;
                this.codeDisplay.setVariableValue("i", it);
                if(it == this.alphabet.size()){
                    this.currentLineNumber = 9;
                }
                else{
                    this.codeDisplay.removeVariable("cnt");
                    this.matrixDisplay.highlightBackground(it + 1, cColumn);
                    this.currentLineNumber = 6;
                }
            }
            case 6 -> {
                cnt = cV[it];
                this.codeDisplay.addVariable("cnt", cnt);
                this.matrixDisplay.highlightBackground(it + 1, cColumn);
                this.matrixDisplay.highlightBackground(it + 1, ccColumn);
                this.currentLineNumber = 7;
            }
            case 7 -> {
                cV[it] = sum;
                this.matrixDisplay.setSquareText(it + 1, ccColumn, cV[it]);
                this.matrixDisplay.highlightBackground(it + 1, cColumn);
                this.matrixDisplay.highlightBackground(it + 1, ccColumn);
                this.currentLineNumber = 8;
            }
            case 8 -> {
                sum = sum + cnt;
                this.codeDisplay.highlightVariable("sum");
                this.codeDisplay.setVariableValue("sum", sum);
                this.currentLineNumber = 5;
            }
            case 9 -> {
                this.matrixDisplay.setSquareText(0, lfColumn, "LF[i]");
                this.codeDisplay.removeAllVariables();
                this.currentLineNumber = 10;
            }
            case 10 -> {
                if(this.firstTime.get(10)){
                    it = -1;
                    this.codeDisplay.addVariable("i", 0);
                    this.firstTime.set(10, false);
                }
                it++;
                if(it == this.input.length()){
                    this.currentLineNumber = 13;
                }
                else{
                    this.matrixDisplay.highlightBackground(it + 1, iColumn);
                    this.currentLineNumber = 11;
                }
            }
            case 11 -> {//C[L[i]] = C[L[i]] + 1
                this.matrixDisplay.highlightBackground(it + 1, lColumn);
                this.matrixDisplay.highlightBackground(it + 1, iColumn);
                for(int i = 0;i<this.alphabet.size();i++){
                    if(alphabet.get(i) == this.input.charAt(it)){
                        this.matrixDisplay.highlightBackground(i + 1, ccColumn);
                        cV[i] = cV[i] + 1;
                        this.matrixDisplay.setSquareText(i + 1, ccColumn, cV[i]);
                        this.matrixDisplay.highlightBackground(i + 1, cColumn);
                    }
                }
                this.currentLineNumber = 12;
            }
            case 12 -> {
                this.matrixDisplay.highlightBackground(it + 1, lColumn);
                this.matrixDisplay.highlightBackground(it + 1, iColumn);
                for(int i = 0;i<this.alphabet.size();i++){
                    if(alphabet.get(i) == this.input.charAt(it)){
                        this.matrixDisplay.highlightBackground(i + 1, ccColumn);
                        this.matrixDisplay.highlightBackground(i + 1, cColumn);
                        lfV[it] = cV[i] - 1;
                        this.matrixDisplay.setSquareText(it + 1, lfColumn, lfV[it]);
                        this.matrixDisplay.highlightBackground(it + 1, lfColumn);
                    }
                }
                this.currentLineNumber = 10;
            }
            case 13 -> {
                this.matrixDisplay.setSquareText(0, sColumn, "S[i]");
                this.codeDisplay.removeVariable("i");
                sV = new char[this.input.length()];
                for(int i = 0;i<this.input.length();i++){
                    sV[i] = ' ';
                }
                this.currentLineNumber = 14;
            }
            case 14 -> {
                sV[sV.length - 1] = '$';
                this.matrixDisplay.setSquareText(sV.length, sColumn, '$');
                //todo - mozno teraz vlozit to toho indexy, a povedat
                //co vlastne zanmena to mapovanie
                this.currentLineNumber = 15;
            }
            case 15 -> {
                jt = 0;
                this.codeDisplay.addVariable("j", 0);
                this.currentLineNumber = 16;
            }
            case 16 -> {
                if(this.firstTime.get(16)){
                    it = this.input.length() - 1;
                    this.codeDisplay.addVariable("i", 0);
                    //todo - toto dat asi iba sivou
                    this.matrixDisplay.setSquareText(0, fColumn, "F[i]");
                    for(int i = 0;i<this.sortedInput.length();i++){
                        this.matrixDisplay.setSquareText(i + 1, fColumn, sortedInput.charAt(i));
                    }
                    int[] occurrences = new int[this.alphabet.size()];
                    for(int i = 0;i<this.sortedInput.length();i++){
                        for(int j = 0 ;j<this.alphabet.size();j++){
                            if(this.alphabet.get(j) == this.sortedInput.charAt(i)){
                                occurrences[j]++;
                                this.matrixDisplay.setSquareIndex(i + 1, fColumn, occurrences[j]);
                            }
                        }
                    }
                    occurrences = new int[this.alphabet.size()];
                    for(int i = 0;i<this.input.length();i++){
                        for(int j = 0 ;j<this.alphabet.size();j++){
                            if(this.alphabet.get(j) == this.input.charAt(i)){
                                occurrences[j]++;
                                this.matrixDisplay.setSquareIndex(i + 1, lColumn, occurrences[j]);
                            }
                        }
                    }
                    this.firstTime.set(16, false);
                }
                else {
                    this.matrixDisplay.removeMatrixArrow(jt + 1, fColumn, lfV[jt] + 1, lColumn);
                }
                it--;
                this.codeDisplay.setVariableValue("i", it);
                if(it == -1){
                    this.currentLineNumber = 19;
                }
                else{
                    //this.matrixDisplay.highlightBackground(it + 1, iColumn);
                    this.currentLineNumber = 17;
                }
            }
            case 17 -> {
                //todo - dat premennym viacere farby, a ukazat na obe miesta, co sa tam deje
                sV[it] = input.charAt(jt);
                this.matrixDisplay.setSquareText(it + 1, sColumn, sV[it]);
                this.matrixDisplay.highlightBackground(it + 1, sColumn);
                this.matrixDisplay.highlightBackground(jt + 1, lColumn);
                this.currentLineNumber = 18;
            }
            case 18 -> {
                jt = lfV[jt];
                this.matrixDisplay.highlightBackground(jt + 1, lfColumn);
                this.codeDisplay.setVariableValue("j",jt);
                //tuto asi pojdu tie sipky
                //this.matrixDisplay.addArrow( lfV[jt] + 1, lColumn, jt + 1, fColumn);
                this.currentLineNumber = 16;
            }
            case 19 -> end();
        }
    }

    private void end(){

        Button retryDecode = new Button();
        retryDecode.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.BWTDecode));
        LanguageListenerAdder.addLanguageListener("", retryDecode);
        WindowManager.addController(retryDecode, 0,1);

        Button bwtButton = new Button();
        bwtButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.BWT));
        LanguageListenerAdder.addLanguageListener("", bwtButton);
        WindowManager.addController(bwtButton, 1,1);
    }
}
