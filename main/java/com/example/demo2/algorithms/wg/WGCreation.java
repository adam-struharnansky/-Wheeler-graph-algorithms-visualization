package com.example.demo2.algorithms.wg;

import com.example.demo2.algorithmDisplays.TextDisplay;
import com.example.demo2.algorithmDisplays.WindowManager;
import com.example.demo2.algorithmDisplays.GraphDisplay;
import com.example.demo2.algorithmDisplays.animatableNodes.DirectedVertex;
import com.example.demo2.algorithmDisplays.animatableNodes.DisplayType;
import com.example.demo2.algorithmDisplays.animatableNodes.Edge;
import com.example.demo2.algorithmDisplays.animatableNodes.Vertex;
import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.algorithmManager.AlgorithmType;
import com.example.demo2.algorithms.Algorithm;
import com.example.demo2.animations.Animation;
import com.example.demo2.animations.AnimationType;
import com.example.demo2.multilingualism.LanguageListenerAdder;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class WGCreation extends Algorithm {

    private final GraphDisplay graphDisplay;
    private final TextDisplay textDisplay; //- kde bude ulozene, ako je vhodne ulozeny?
    private final ArrayList<DirectedVertex> vertices = new ArrayList<>();
    private final ArrayList<Edge> edges = new ArrayList<>();

    private final Animation animation = new Animation();

    public WGCreation(AlgorithmManager algorithmManager) {
        super(algorithmManager);
        this.graphDisplay = (GraphDisplay) WindowManager.addDisplay(DisplayType.DirectedGraph, "", 4);
        this.textDisplay = (TextDisplay) WindowManager.addDisplay(DisplayType.Text, "succinctRepresentation", 1);

        addButtons(algorithmManager);
    }

    public WGCreation(AlgorithmManager algorithmManager, ArrayList<DirectedVertex> vertices){
        super(algorithmManager);
        this.graphDisplay = (GraphDisplay) WindowManager.addDisplay(DisplayType.DirectedGraph, "", 4);
        this.textDisplay = (TextDisplay) WindowManager.addDisplay(DisplayType.Text, "succinctRepresentation", 1);

        for(DirectedVertex oldVertex:vertices){
            DirectedVertex vertex = (DirectedVertex) this.graphDisplay.addVertex();
            vertex.setValue(oldVertex.getValue());
            vertex.setRelativePosition(oldVertex.getRelativePosition());
            this.vertices.add(vertex);
        }

        for(DirectedVertex oldVertex:vertices){
            for(Edge edge:oldVertex.getOutgoing()){
                addEdge(edge.getText(), edge.getVertexFrom().getValue(), edge.getVertexTo().getValue(), false);
            }
        }

        rewriteSuccinctRepresentation();
        addButtons(algorithmManager);
    }

    private void addButtons(AlgorithmManager algorithmManager){


        int addVertexRow = 0, addEdgeRow = 1, changeAlgorithmRow = 2;

        CheckBox animateCheckBox = new CheckBox();
        animateCheckBox.setSelected(true);
        LanguageListenerAdder.addLanguageListener("animate", animateCheckBox);
        WindowManager.addController(animateCheckBox, addVertexRow, 2);

        Label vertexFromLabel = new Label();
        LanguageListenerAdder.addLanguageListener("vertexFrom", vertexFromLabel);
        WindowManager.addController(vertexFromLabel, addEdgeRow,0);
        Label vertexToLabel = new Label();
        LanguageListenerAdder.addLanguageListener("vertexTo", vertexToLabel);
        WindowManager.addController(vertexToLabel, addEdgeRow, 2);
        Label edgeValueLabel = new Label();
        LanguageListenerAdder.addLanguageListener("edgeValue", edgeValueLabel);
        WindowManager.addController(edgeValueLabel, addEdgeRow, 4);

        TextField edgeFromTextField = new TextField();
        edgeFromTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                edgeFromTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        WindowManager.addController(edgeFromTextField, addEdgeRow, 1);
        TextField edgeToTextField = new TextField();
        edgeToTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                edgeToTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        WindowManager.addController(edgeToTextField, addEdgeRow, 3);
        TextField edgeValueTextField = new TextField();
        WindowManager.addController(edgeValueTextField, addEdgeRow, 5);

        Button addVertexButton = new Button();
        addVertexButton.setOnAction(actionEvent -> addVertex(animateCheckBox.isSelected()));
        LanguageListenerAdder.addLanguageListener("addVertex", addVertexButton);
        WindowManager.addController(addVertexButton, addVertexRow, 1);

        Button addEdgeButton = new Button();
        addEdgeButton.setOnAction(actionEvent ->
                addEdge(edgeValueTextField.getText(), edgeFromTextField.getText(),edgeToTextField.getText(), animateCheckBox.isSelected()));
        LanguageListenerAdder.addLanguageListener("addEdge", addEdgeButton);
        WindowManager.addController(addEdgeButton, addEdgeRow, 6);

        Button checkButton = new Button();
        checkButton.setOnAction(actionEvent -> checkWheelerGraph());
        LanguageListenerAdder.addLanguageListener("checkWG", checkButton);
        WindowManager.addController(checkButton, changeAlgorithmRow, 0);

        Button returnButton = new Button();
        returnButton.setOnAction(actionEvent -> algorithmManager.changeAlgorithm(AlgorithmType.WG));
        LanguageListenerAdder.addLanguageListener("returnToWG", returnButton);
        WindowManager.addController(returnButton, changeAlgorithmRow, 1);

        Button searchButton = new Button();
        searchButton.setOnAction(actionEvent -> {
            if(checkWheelerGraph()) {
                algorithmManager.changeAlgorithm(AlgorithmType.WGSearch);
            }
        });
        LanguageListenerAdder.addLanguageListener("searchInGraph", searchButton);
        WindowManager.addController(searchButton, changeAlgorithmRow, 2);

        Button tunnelingButton = new Button();
        tunnelingButton.setOnAction(actionEvent -> {
            if(checkWheelerGraph()) {
                algorithmManager.changeAlgorithm(AlgorithmType.WGTunneling);
            }
        });
        LanguageListenerAdder.addLanguageListener("tunnelGraph", tunnelingButton);
        WindowManager.addController(tunnelingButton, changeAlgorithmRow, 3);
    }

    public void rewriteSuccinctRepresentation(){
        this.textDisplay.clear();
        boolean correctForm = true;
        for(DirectedVertex vertex:this.vertices){
            if(vertex.getIncoming().isEmpty() || vertex.getOutgoing().isEmpty()){
                correctForm = false;
                break;
            }
        }
        if(correctForm){
            StringBuilder l = new StringBuilder();
            StringBuilder out = new StringBuilder();
            StringBuilder in = new StringBuilder();
            StringBuilder f = new StringBuilder();
            for(int i = 1;i<=this.vertices.size();i++){
                for(DirectedVertex vertex:this.vertices){
                    if(vertex.getValue().compareTo(i+"") == 0){
                        out.append(1);
                        in.append(1);
                        for(int j = 0;j<vertex.getOutgoing().size();j++){
                            l.append(vertex.getOutgoing().get(j).getText());
                            if(j>=1){
                                out.append(0);
                            }
                        }
                        for(int j = 0;j<vertex.getIncoming().size();j++){
                            f.append(vertex.getIncoming().get(j).getText());
                            if(j>=1){
                                in.append(0);
                            }
                        }
                    }
                }
            }
            this.textDisplay.addString("L: "+l+"\n","", false);
            this.textDisplay.addString("O: "+out+"\n","", false);
            this.textDisplay.addString("I: "+in+"\n","", false);
            this.textDisplay.addString("F: "+f+"\n","", false);
        }
        else{
            //todo - asi to dat do text displayu, kde sa to da dat do riadkov, a nebude to treba prekladat
            this.textDisplay.addString("vertexWithNoIncomingOrOutgoingEdgesExists", "", true);
        }
    }

    public void addVertex(boolean animate){
        this.animation.endAnimation();
        this.animation.clear();
        for(Vertex vertex:this.vertices){
            if(vertex.getValue().compareTo(vertices.size()+"") == 0){//toto by namalo nikdy nastat
                this.animation.addAnimatable(AnimationType.ColorAnimation, vertex, Color.RED, Color.AQUA);
                this.animation.startAnimation();
                //todo - vypisat, ze sa rovnaju
                return;
            }
        }
        DirectedVertex vertex = (DirectedVertex) this.graphDisplay.addVertex();
        vertex.setValue(vertices.size());
        Random random = new Random();
        vertex.setRelativePosition(0.5 + random.nextDouble()/8,0.5 + random.nextDouble()/8);
        this.vertices.add(vertex);
        this.animation.addAnimatable(AnimationType.AppearAnimation, vertex);
        this.graphDisplay.beautify(animation);
        if(animate) {
            this.animation.startAnimation();
        }
        else {
            this.animation.endAnimation();
        }
        rewriteSuccinctRepresentation();
    }

    public void addEdge(String value, String from, String to, boolean animate){
        this.animation.endAnimation();
        this.animation.clear();
        if(from.compareTo(to) == 0){
            //todo - dorobit slucky
            return;
        }
        Vertex vertexFrom = null, vertexTo = null;
        for(Vertex vertex:this.vertices){
            if(vertex.getValue().compareTo(from) == 0){
                vertexFrom = vertex;
            }
            if(vertex.getValue().compareTo(to) == 0){
                vertexTo = vertex;
            }
        }
        if(vertexFrom != null && vertexTo != null){
            Edge edge = graphDisplay.addEdge(vertexFrom, vertexTo);
            edge.setText(value);
            this.edges.add(edge);
            this.animation.addAnimatable(AnimationType.AppearAnimation, edge);
            this.graphDisplay.beautify(animation);
            this.animation.startAnimation();
            rewriteSuccinctRepresentation();
        }
    }

    public boolean checkWheelerGraph(){
        this.animation.endAnimation();
        this.animation.clear();
        for(int i = 0;i<this.edges.size();i++){
            for(int j = i+1;j<this.edges.size();j++){
                int cmpE = this.edges.get(i).getText().compareTo(this.edges.get(j).getText());
                int cmpU = Integer.compare(Integer.parseInt(this.edges.get(i).getVertexFrom().getValue()),
                        Integer.parseInt(this.edges.get(j).getVertexFrom().getValue()));
                int cmpV = Integer.compare(Integer.parseInt(this.edges.get(i).getVertexTo().getValue()),
                        Integer.parseInt(this.edges.get(j).getVertexTo().getValue()));
                boolean correctPair = true;
                if(cmpE < 0 && cmpV > 0){
                    correctPair = false;
                }
                else if(cmpE > 0 && cmpV < 0){
                    correctPair = false;
                }
                else if(cmpE == 0 && cmpV < 0 && cmpU > 0){
                    correctPair = false;
                }
                else if(cmpE == 0 && cmpV > 0 && cmpU < 0){
                    correctPair = false;
                }
                if(!correctPair){
                    this.animation.addAnimatable(AnimationType.ColorAnimation, edges.get(i), Color.RED, Color.BLACK);
                    this.animation.addAnimatable(AnimationType.ColorAnimation, edges.get(j), Color.RED, Color.BLACK);
                    this.animation.addAnimatable(AnimationType.ColorAnimation, edges.get(i).getVertexFrom(), Color.RED, Color.AQUA);
                    this.animation.addAnimatable(AnimationType.ColorAnimation, edges.get(i).getVertexTo(), Color.RED, Color.AQUA);
                    this.animation.addAnimatable(AnimationType.ColorAnimation, edges.get(j).getVertexFrom(), Color.RED, Color.AQUA);
                    this.animation.addAnimatable(AnimationType.ColorAnimation, edges.get(j).getVertexTo(), Color.RED, Color.AQUA);
                    this.animation.startAnimation();
                    return false;
                }
            }
        }
        return true;
    }





    /*
    pridat vec na pridanie vrchola - da sa pridat iba vrchol s dalsim poradovim cislom

    ak sa to da, tak to vypise, ze ako je to ulozene
    ak to bude privelmi dlhe, tak to vlozi do riadkov

    prida tu button na tunelovanie


     */
}
