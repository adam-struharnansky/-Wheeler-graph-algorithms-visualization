package com.example.demo2.algorithms;

import com.example.demo2.geographicalGraph.GeographicalGraph;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Matching implements GraphAlgorithm, MatrixAlgorithm{

    private final GeographicalGraph geographicalGraph;
    private final MatchingOnGraph matchingOnGraph;
    //todo - urobit aj MatchingOnTexts - potom to dorobit na spravne miesta

    public Matching(Pane graph_pane, Pane text_pane, String pattern, GeographicalGraph geographicalGraph){
        this.geographicalGraph = geographicalGraph;
        this.matchingOnGraph = new MatchingOnGraph(graph_pane, pattern, geographicalGraph);
    }

    @Override
    public boolean hasNext() {
        return this.matchingOnGraph.hasNext();
    }

    @Override
    public String nextStep() {
        return this.matchingOnGraph.nextStep();
    }

    @Override
    public GeographicalGraph getGeographicalGraph() {
        return this.geographicalGraph;
    }

    @Override
    public ArrayList<String> getMatrix() {
        return null;
    }
}
