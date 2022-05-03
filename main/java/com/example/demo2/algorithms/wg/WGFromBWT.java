package com.example.demo2.algorithms.wg;

import com.example.demo2.algorithmDisplays.*;
import com.example.demo2.algorithmDisplays.animatableNodes.DirectedEdge;
import com.example.demo2.algorithmDisplays.animatableNodes.DirectedVertex;
import com.example.demo2.algorithmDisplays.animatableNodes.DisplayType;
import com.example.demo2.algorithmDisplays.animatableNodes.Edge;
import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.algorithmManager.AlgorithmType;
import com.example.demo2.algorithms.Algorithm;
import com.example.demo2.animations.Animation;
import com.example.demo2.animations.AnimationManager;
import com.example.demo2.animations.AnimationType;
import com.example.demo2.auxiliary.Algorithms;
import com.example.demo2.multilingualism.LanguageListenerAdder;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Comparator;

public class WGFromBWT extends Algorithm {

    private final AlgorithmManager algorithmManager;

    private final GraphDisplay graphDisplay = (GraphDisplay) WindowManager.addDisplay(DisplayType.DirectedGraph, "", 2);
    private final MatrixDisplay matrixDisplay = (MatrixDisplay) WindowManager.addDisplay(DisplayType.Matrix, "", 1);
    private final TextDisplay textDisplay = (TextDisplay) WindowManager.addDisplay(DisplayType.Text, "", 1);

    private Label startLabel;
    private TextField inputTextField;
    private Button startButton;
    private final boolean fromTextField;
    private final Button changeButton = new Button();
    private final Button searchButton = new Button();
    private final Button tunnelButton = new Button();

    private static final int iColumn = 0;
    private static final int lfColumn = 1;
    private static final int fColumn = 2;
    private static final int lColumn = 3;

    private final AnimationManager animationManager = new AnimationManager();

    private String input;
    private String lV;
    private String fV;
    private int[] lfV;
    private ArrayList<String> rotations;
    private ArrayList<DirectedVertex> vertices;
    private ArrayList<DirectedEdge> edges;

    public WGFromBWT(AlgorithmManager algorithmManager) {
        super(algorithmManager);
        this.algorithmManager = algorithmManager;

        this.startLabel = new Label();
        LanguageListenerAdder.addLanguageListener("inputText", this.startLabel);
        this.inputTextField = new TextField("abrakadabra");
        WindowManager.addController(this.inputTextField, 0,0);
        this.startButton = new Button();
        LanguageListenerAdder.addLanguageListener("start", this.startButton);
        WindowManager.addController(this.startButton, 0,1);
        this.fromTextField = true;

        this.startButton.setOnAction(actionEvent -> preStart(this.inputTextField.getText()));
    }

    public WGFromBWT(AlgorithmManager algorithmManager, String input){
        super(algorithmManager);
        this.algorithmManager = algorithmManager;
        this.fromTextField = false;
        preStart(input);
    }

    private void preStart(String input){

        changeButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.WGCreation, this.vertices));
        LanguageListenerAdder.addLanguageListener("transformGraph", changeButton);
        searchButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.WGSearch, this.vertices));
        LanguageListenerAdder.addLanguageListener("searchInGraph", searchButton);
        tunnelButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.WGTunneling, this.vertices));
        LanguageListenerAdder.addLanguageListener("tunnelGraph", tunnelButton);

        this.input = (input.endsWith("$"))?input:input+"$";
        if(this.fromTextField){
            WindowManager.removeController(this.startButton);
            WindowManager.removeController(this.inputTextField);
        }
        this.matrixDisplay.setMatrixSize(this.input.length() + 1, 4);
        this.matrixDisplay.setRowText(0, "i", "LF[i]", "F[i]", "L[i]");
        start();
    }

    private void start(){
        this.rotations = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();

        StringBuilder start = new StringBuilder(this.input);
        StringBuilder end = new StringBuilder();
        for(int i = 0; i<this.input.length(); i++){
            this.rotations.add(String.valueOf(start) + end);
            end.append(start.charAt(0));
            start.deleteCharAt(0);
        }
        rotations.sort(Comparator.naturalOrder());
        lV = "";
        fV = "";
        for (String rotation : this.rotations) {
            lV = lV + rotation.charAt(rotation.length() - 1);
            fV = fV + rotation.charAt(0);
        }
        lfV = Algorithms.lfMapping(lV);
        for(int i = 0;i<this.input.length();i++){
            this.matrixDisplay.setSquareText(i + 1, iColumn, i );
            this.matrixDisplay.setSquareText(i + 1, lfColumn, lfV[i]);
            this.matrixDisplay.setSquareText(i + 1, fColumn, fV.charAt(i));
            this.matrixDisplay.setSquareText(i + 1, lColumn, lV.charAt(i));
        }

        for(int i = 0; i<this.input.length();i++){
            DirectedVertex vertex = (DirectedVertex) this.graphDisplay.addVertex();
            vertex.setRelativePosition(0.4*Math.cos((2*Math.PI/this.input.length())*i) + 0.5,
                    0.4*Math.sin((2*Math.PI/this.input.length())*i) + 0.5);
            vertex.setValue(i);
            this.vertices.add(vertex);
        }

        super.addNextBackAnimateControls(0,1,0,0,0,2);
        super.backStepButton.setDisable(true);
    }

    private int step = 0;
    private int highestStep = 0;
    private int it = 0;
    private int endInt = -1;
    @Override
    protected void nextStep(boolean animate){
        Animation animation = this.animationManager.getAnimation(step);

        if(highestStep == step) {
            this.matrixDisplay.unhighlightEverything(animation);
            this.matrixDisplay.clearMatrixArrows(animation);

            int jt = lfV[it];

            this.matrixDisplay.highlightBackground(animation, it + 1, iColumn);
            this.matrixDisplay.highlightBackground(animation, it + 1, lfColumn);
            this.matrixDisplay.highlightBackground(animation, it + 1, lColumn);

            DirectedEdge edge = (DirectedEdge) this.graphDisplay.addEdge(this.vertices.get(it), this.vertices.get(jt));
            edge.setText(lV.charAt(it));
            animation.addAnimatable(AnimationType.AppearAnimation, edge);
            if (lV.charAt(it) == '$') {
                endInt = step;
                end();
            }
            it = jt;
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
        changeButton.setDisable(true);
        searchButton.setDisable(true);
        tunnelButton.setDisable(true);
    }

    private void end(){
        super.nextStepButton.setDisable(true);

        for(DirectedVertex vertex:this.vertices){
            for(Edge edge:vertex.getOutgoing()){
                System.out.println(edge.getText());
            }
        }
        changeButton.setDisable(false);
        WindowManager.addController(changeButton, 1, 0);

        searchButton.setDisable(false);
        WindowManager.addController(searchButton, 1, 1);

        tunnelButton.setDisable(false);
        WindowManager.addController(tunnelButton, 1, 2);
    }
}
