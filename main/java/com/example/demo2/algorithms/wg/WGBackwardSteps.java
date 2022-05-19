package com.example.demo2.algorithms.wg;

import com.example.demo2.Step.StepManager;
import com.example.demo2.algorithmDisplays.*;
import com.example.demo2.algorithmDisplays.animatableNodes.DirectedEdge;
import com.example.demo2.algorithmDisplays.animatableNodes.DirectedVertex;
import com.example.demo2.algorithmDisplays.animatableNodes.Edge;
import com.example.demo2.algorithmDisplays.animatableNodes.Vertex;
import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.algorithms.Algorithm;
import com.example.demo2.animations.Animation;
import com.example.demo2.animations.AnimationManager;
import com.example.demo2.auxiliary.Colors;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class WGBackwardSteps extends Algorithm {

    private final AlgorithmManager algorithmManager;
    private final AnimationManager animationManager = new AnimationManager();
    private final StepManager stepManager = new StepManager();

    private final GraphDisplay graphDisplay = (GraphDisplay) WindowManager.addDisplay(DisplayType.DirectedGraph, "", 3);
    private final MatrixDisplay matrixDisplay = (MatrixDisplay) WindowManager.addDisplay(DisplayType.Matrix, "", 1);
    private final CodeDisplay codeDisplay = (CodeDisplay) WindowManager.addDisplay(DisplayType.Code, "", 2);
    private final TextDisplay textDisplay = (TextDisplay) WindowManager.addDisplay(DisplayType.Text, "", 2);

    private final ArrayList<DirectedVertex> vertices = new ArrayList<>();
    private final ArrayList<DirectedEdge> edges = new ArrayList<>();


    private final static int searchRow = 0;
    private final static int exampleRow = 1;
    private final static int changeRow = 2;

    private final static int iColumn = 0;
    private final static int lColumn = 1;
    private final static int outColumn = 2;
    private final static int inColumn = 3;
    private final static int fColumn = 4;
    private final static int srColumn = 5;


    private final Button startButton = new Button();
    private final ArrayList<RadioButton> exampleRadioButtons = new ArrayList<>();
    private final Button setExampleGraph = new Button();

    public WGBackwardSteps(AlgorithmManager algorithmManager) {
        super(algorithmManager);
        this.algorithmManager = algorithmManager;
        //todo - pomocou pokusnych grafov
        startButton.setOnAction(actionEvent -> preStart());
    }

    private final ArrayList<Character> l = new ArrayList<>();
    private final ArrayList<Integer> in = new ArrayList<>();
    private final ArrayList<Integer> out = new ArrayList<>();
    private final ArrayList<Character> f = new ArrayList<>();
    private final ArrayList<Character> sr = new ArrayList<>();
    private final ArrayList<Character> alphabet = new ArrayList<>();
    private final ArrayList<Integer> c = new ArrayList<>();


    public WGBackwardSteps(AlgorithmManager algorithmManager, ArrayList<Character> l, ArrayList<Integer> in,
                           ArrayList<Integer> out){
        super(algorithmManager);
        this.algorithmManager = algorithmManager;
        this.in.addAll(in);
        this.out.addAll(out);
        this.l.addAll(l);
        this.f.addAll(l);
        this.f.sort(Comparator.naturalOrder());
        for(Character character:this.f){
            if(!this.alphabet.contains(character)){
                this.alphabet.add(character);
                this.c.add(0);
            }
        }
        for(Character character:this.l){
            this.c.set(this.alphabet.indexOf(character), this.c.get(this.alphabet.indexOf(character)) + 1);
        }
        int sum = 0;
        for(int i = 0;i<this.c.size();i++){
            int tmp = c.get(i);
            c.set(i, sum);
            sum += tmp;
        }
        System.out.println(alphabet+" "+c+" "+f);
        start();
    }

    private void setMatrix(){
        this.matrixDisplay.setMatrixSize(this.in.size() + 1, 6);
        this.matrixDisplay.setRowText(0, "i", "L[i]", "out[i]", "in[i]", "F[i]", "SR[i]");
        this.matrixDisplay.setSquareTextColor(0, fColumn, Color.GRAY);
        for(int i = 0;i<this.in.size();i++){
            this.matrixDisplay.setSquareText(i + 1, iColumn, i);
            if(i<this.l.size()) {
                this.matrixDisplay.setSquareText(i + 1, lColumn, this.l.get(i));
            }
            this.matrixDisplay.setSquareText(i + 1, outColumn, this.out.get(i));
            this.matrixDisplay.setSquareText(i + 1, inColumn, this.in.get(i));
            if(i<this.f.size()) {
                this.matrixDisplay.setSquareText(i + 1, fColumn, this.f.get(i));
                this.matrixDisplay.setSquareTextColor(i + 1, fColumn, Color.GRAY);
            }
        }
    }

    private void setCode(){
        this.codeDisplay.addLine("i = L.select($, 0)");
        this.codeDisplay.addLine("offset = 0");
        this.codeDisplay.addLine("for(int k = 0;k<n;k++)");
        this.codeDisplay.addLine("    SR[k] = L[i]");
        this.codeDisplay.addLine("    i = L.C(L[i]) + L.rank(L[i], i)");
        this.codeDisplay.addLine("    nodeRank = in.rank(1, i + 1) - 1");
        this.codeDisplay.addLine("    if(in[i] = 0 || in[i + 1] = 0)");
        this.codeDisplay.addLine("        offset = i - in.select(1, nodeRank)");
        this.codeDisplay.addLine("    i = out.select(1, nodeRank)");
        this.codeDisplay.addLine("    if(out[i + 1] = 0)");
        this.codeDisplay.addLine("        i = i + offset");
        this.codeDisplay.addLine("        offset = 0");
    }

    private void setGraph(){
        int verticesNumber = -1;
        for (Integer integer : this.in) {
            verticesNumber += integer;
        }
        Random random = new Random();
        for(int i = 0;i<verticesNumber;i++){
            Vertex vertex = this.graphDisplay.addVertex();
            vertex.setValue(i);

            vertex.setRelativePosition(0.45 + 0.1*random.nextDouble(),0.45 + 0.1*random.nextDouble());
            this.vertices.add((DirectedVertex) vertex);
        }
        int [] occ = new int[this.alphabet.size()];

        for(int i = 0;i<this.l.size();i++){
            int from = -1;
            for(int j = 0;j<=i;j++){
                from +=this.out.get(j);
            }
            char label = l.get(i);
            int to = -1;
            int o = 0;
            for(int j = 0;j<this.in.size();j++){
                to += this.in.get(j);
                if(f.get(j) == label && o == occ[alphabet.indexOf(label)]){
                    break;
                }
                if(f.get(j) == label){
                    o++;
                }
            }
            DirectedEdge edge = (DirectedEdge) this.graphDisplay.addEdge(this.vertices.get(from), this.vertices.get(to));
            edge.setText(label);
            this.edges.add(edge);
            occ[alphabet.indexOf(label)]++;
        }
        this.graphDisplay.beautify(500);
    }

    private void preStart(){
        //todo
        start();
    }

    private void start(){
        setCode();
        setMatrix();
        setGraph();

        this.step = 0;
        this.highestStep = -1;
        this.currentLineNumber = 1;

        super.addBasicControls(1, 0);
    }

    boolean firstTime3 = true;
    int currentLineNumber;
    int step, highestStep, endInt = -1;
    int iV, kV, offsetV, nodeRankV;

    private Vertex highlightedVertex = null;

    @Override
    protected void nextStep(boolean animate){
        Animation animation = this.animationManager.getAnimation(step);

        if(step == highestStep) {
            this.codeDisplay.unhighlightEverything(animation);
            this.matrixDisplay.unhighlightBackgrounds(animation);
            this.graphDisplay.unhighlightEverything();
            if(this.highlightedVertex != null){
                this.stepManager.addSetHighlightVertex(step, this.highlightedVertex, false);
                this.highlightedVertex = null;
            }

            this.codeDisplay.highlightLine(animation, this.currentLineNumber);
            switch (this.currentLineNumber) {
                case 1 -> {
                    iV = 0;
                    for(int i = 0;i<this.l.size();i++){
                        if(l.get(i) == '$'){
                            iV = i;
                            this.matrixDisplay.highlightBackground(animation, iV + 1, lColumn);
                            this.matrixDisplay.highlightBackground(animation, iV + 1, iColumn);
                            break;
                        }
                    }
                    this.codeDisplay.addVariable(animation, "i", iV);
                    this.currentLineNumber = 2;
                }
                case 2 -> {
                    offsetV = 0;
                    this.codeDisplay.addVariable(animation, "offset", 0);
                    this.currentLineNumber = 3;
                }
                case 3 -> {
                    if(this.firstTime3){
                        kV = -1;
                        this.codeDisplay.addVariable(animation, "k", 0);
                        this.firstTime3 = false;
                        this.stepManager.addSetVariableValue(step, this.codeDisplay, "k", "", (kV+1)+"");
                    }
                    else {
                        this.stepManager.addSetVariableValue(step, this.codeDisplay, "k", kV + "", (kV + 1) + "");
                        this.codeDisplay.removeVariable(animation, "nodeRank");
                    }
                    kV++;
                    if(kV == 15){//todo
                        this.currentLineNumber = 13;
                    }
                    else{
                        this.currentLineNumber = 4;
                    }
                }
                case 4 -> {
                    this.sr.add(l.get(iV));
                    System.out.println("sr: "+this.sr);
                    this.matrixDisplay.highlightBackground(animation, iV + 1, lColumn);
                    this.stepManager.addSetSquareText(step, this.matrixDisplay, kV + 1, srColumn, "", l.get(iV)+"");
                    this.currentLineNumber = 5;
                }
                case 5 -> {
                    int tmp = 0;
                    for(int i = 0;i<iV;i++){//rank
                        if(l.get(i) == l.get(iV)){
                            tmp++;
                        }
                    }
                    tmp += c.get(alphabet.indexOf(l.get(iV)));
                    int from = -1;
                    int to = -1;
                    for(int i = 0;i<iV + 1;i++){
                        from += out.get(i);
                    }
                    for(int i = 0;i<tmp + 1;i++){
                        to += in.get(i);
                    }
                    System.out.println(from+" "+to+" "+l.get(iV));
                    for(Edge edge:this.edges){
                        if(edge.getText().charAt(0) == l.get(iV) && Integer.parseInt(edge.getVertexFrom().getValue()) == from
                                && Integer.parseInt(edge.getVertexTo().getValue()) == to){
                            this.stepManager.addWay(step, edge, kV + 1, Colors.getTransitionColor(kV, 15));
                        }
                    }

                    this.stepManager.addSetVariableValue(step, this.codeDisplay, "i", iV+"", tmp+"");
                    iV = tmp;
                    this.currentLineNumber = 6;
                }
                case 6 -> {
                    int tmp = -1;
                    for(int i = 0;i<iV + 1;i++){
                        tmp += in.get(i);
                    }
                    System.out.println("tmp "+tmp);
                    this.stepManager.addSetHighlightVertex(step, this.vertices.get(tmp), true);
                    this.highlightedVertex = this.vertices.get(tmp);
                    this.codeDisplay.addVariable(animation, "nodeRank", tmp+"");
                    this.stepManager.addSetVariableValue(step, this.codeDisplay, "nodeRank", "", tmp+"");
                    nodeRankV = tmp;
                    this.currentLineNumber = 7;
                }
                case 7 -> {
                    this.matrixDisplay.highlightBackground(animation, iV + 1, inColumn);
                    this.matrixDisplay.highlightBackground(animation, iV + 2, inColumn);
                    if(in.get(iV) == 0 || in.get(iV + 1) == 0){
                        this.currentLineNumber = 8;
                    }
                    else{
                        this.currentLineNumber = 9;
                    }
                }
                case 8 -> {
                    int select = 0;
                    int occ = -1;
                    for(int i = 0;i<this.in.size();i++){
                        occ += this.in.get(i);
                        if(occ == nodeRankV){
                            select = i;
                            break;
                        }
                    }
                    //todo - zvyraznit, kde sa nachadza , povedat, ze co to znamena mat offset
                    this.stepManager.addSetVariableValue(step, this.codeDisplay, "offset", offsetV+"", (iV - select)+"");
                    this.offsetV = iV - select;
                    this.currentLineNumber = 9;
                }
                case 9 -> {
                    int select = 0;
                    int occ = -1;
                    for(int i = 0;i<this.out.size();i++){
                        occ += this.out.get(i);
                        if(occ == nodeRankV){
                            select = i;
                            break;
                        }
                    }
                    this.stepManager.addSetVariableValue(step, this.codeDisplay, "i", iV+"", select+"");
                    this.iV = select;
                    this.currentLineNumber = 10;
                }
                case 10 -> {
                    this.matrixDisplay.highlightBackground(animation, iV + 2, outColumn);
                    if(out.get(iV + 1) == 0){
                        this.currentLineNumber = 11;
                    }
                    else{
                        this.currentLineNumber = 3;
                    }
                }
                case 11 -> {
                    this.stepManager.addSetVariableValue(step, this.codeDisplay, "i", iV+"", (iV + offsetV)+"");
                    iV += offsetV;
                    this.currentLineNumber = 12;
                }
                case 12 -> {
                    this.stepManager.addSetVariableValue(step, this.codeDisplay, "offset", offsetV+"", "0");
                    offsetV = 0;
                    this.currentLineNumber = 3;
                }
                case 13 -> {
                    this.endInt = step;
                }
            }
        }

        if(animate){
            animationManager.executeAnimation(step, true);
        }
        else{
            animation.setForward(true);
            animation.endAnimation();
        }
        this.stepManager.forwardStep(step);
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
            this.stepManager.backStep(step);
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


    private void  end(){
        super.nextStepButton.setDisable(true);
    }
}
