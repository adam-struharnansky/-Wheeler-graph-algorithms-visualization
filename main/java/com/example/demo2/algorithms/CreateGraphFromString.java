package com.example.demo2.algorithms;

import com.example.demo2.geographicalGraph.GeographicalGraph;
import com.example.demo2.geographicalGraph.TopologicalVertex;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class CreateGraphFromString implements MatrixAlgorithm, GraphAlgorithm{

    private final BWTMatrixGraphical bwtMatrixGraphical;
    private final CreateGraphFromStringGraphical createGraphFromStringGraphical;
    private boolean first;

    public CreateGraphFromString(Pane graph_pane, Pane matrix_pane, String text){
        graph_pane.getChildren().clear();
        matrix_pane.getChildren().clear();
        BWT bwt = new BWT(text);
        this.bwtMatrixGraphical = new BWTMatrixGraphical(matrix_pane, bwt, text.length());
        this.createGraphFromStringGraphical = new CreateGraphFromStringGraphical(graph_pane, bwt, text + '$');
        this.first = true;
    }

    @Override
    public boolean hasNext() {
        //treba sa vobec pytat aj grafovej casti? mali by by rovnake
        return this.bwtMatrixGraphical.hasNext();
    }

    @Override
    public String nextStep() {
        //co robi toto?
        this.bwtMatrixGraphical.nextStep();
        if(!first) {
            this.createGraphFromStringGraphical.nextStep();
        }
        else{
            first = false;
        }
        return null;
    }
/*
    @Override
    public ArrayList<TopologicalVertex> getGraph() {
        return this.createGraphFromStringGraphical.getGraph();
    }
*/

    @Override
    public GeographicalGraph getGeographicalGraph() {
        return this.createGraphFromStringGraphical.getGeographicalGraph();
    }

    @Override
    public ArrayList<String> getMatrix() {
        return this.bwtMatrixGraphical.getMatrix();
    }

}
