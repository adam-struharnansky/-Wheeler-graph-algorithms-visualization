package com.example.demo2.algorithms.wg;

import com.example.demo2.algorithmDisplays.*;
import com.example.demo2.algorithmDisplays.animatableNodes.DirectedVertex;
import com.example.demo2.algorithmDisplays.animatableNodes.DisplayType;
import com.example.demo2.algorithmDisplays.animatableNodes.Edge;
import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.algorithms.Algorithm;
import com.example.demo2.auxiliary.WheelerGraphExamples;
import com.example.demo2.multilingualism.LanguageListenerAdder;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.util.ArrayList;

public class WGTunneling extends Algorithm {


    private final GraphDisplay graphDisplay = (GraphDisplay) WindowManager.addDisplay(DisplayType.DirectedGraph, "", 3);
    private final MatrixDisplay matrixDisplay = (MatrixDisplay) WindowManager.addDisplay(DisplayType.Matrix, "", 1);
    private final CodeDisplay codeDisplay = (CodeDisplay) WindowManager.addDisplay(DisplayType.Code, "", 2);
    private final TextDisplay textDisplay = (TextDisplay) WindowManager.addDisplay(DisplayType.Text, "", 2);

    private final ArrayList<DirectedVertex> vertices = new ArrayList<>();

    private final static int tunnelingRow = 0;
    private final static int exampleRow = 1;
    private final static int changeRow = 2;

    private final Button startButton = new Button();
    private final ArrayList<RadioButton> exampleRadioButtons = new ArrayList<>();
    private final Button setExampleGraph = new Button();

    public WGTunneling(AlgorithmManager algorithmManager) {
        super(algorithmManager);
        addButtons(algorithmManager);
    }

    public WGTunneling(AlgorithmManager algorithmManager, ArrayList<DirectedVertex> vertices){
        super(algorithmManager);
        addButtons(algorithmManager);
        setGraph(vertices);
    }

    private void setGraph(ArrayList<DirectedVertex> vertices){
        //todo - odstranit vsetko, co je tu teraz
        for(DirectedVertex oldVertex:vertices){
            DirectedVertex vertex = (DirectedVertex) this.graphDisplay.addVertex();
            vertex.setValue(oldVertex.getValue());
            vertex.setRelativePosition(oldVertex.getRelativePosition());
            this.vertices.add(vertex);
        }

        for(DirectedVertex oldVertex:vertices){
            for(Edge edge:oldVertex.getOutgoing()){
                //todo - pridat hranu
                System.out.println("edge "+edge.getText());
            }
        }
        rewriteSuccinctRepresentation();
    }

    private void addButtons(AlgorithmManager algorithmManager){

        this.startButton.setOnAction(actionEvent -> start());
        LanguageListenerAdder.addLanguageListener("tunnelGraph", this.startButton);
        WindowManager.addController(this.startButton, tunnelingRow,0);

        ToggleGroup examples = new ToggleGroup();

        for(int i = 0;i<5;i++) {
            RadioButton radioButton = new RadioButton();
            radioButton.setToggleGroup(examples);
            LanguageListenerAdder.addLanguageListener("example"+i, radioButton);
            WindowManager.addController(radioButton, exampleRow, i);
            this.exampleRadioButtons.add(radioButton);
        }

        this.setExampleGraph.setOnAction(actionEvent -> {
            for(int i = 0;i<this.exampleRadioButtons.size();i++){
                if(examples.getSelectedToggle() == this.exampleRadioButtons.get(i)){
                    setGraph(WheelerGraphExamples.vertices(i));
                }
            }
        });
        LanguageListenerAdder.addLanguageListener("setExampleGraph", this.setExampleGraph);
        WindowManager.addController(this.setExampleGraph, exampleRow, 5);

        //todo - pridat change algorithm buttony
    }

    private void rewriteSuccinctRepresentation(){

    }

    private void start(){
        //todo - odstranit startButton a pridat back/next/animate
        //todo - zakazat sa v tomto case presunut na ine algoritmy
    }
}
