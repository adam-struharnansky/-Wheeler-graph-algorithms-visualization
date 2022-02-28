package com.example.demo2.algorithms;

import com.example.demo2.auxiliary.Colors;
import com.example.demo2.geographicalGraph.GeographicalGraph;
import com.example.demo2.geographicalGraph.TopologicalVertex;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.*;

public class MatchingOnGraph implements GraphAlgorithm{

    //private Pane pane;
    private String pattern;
    private GeographicalGraph geographicalGraph;
    private HashSet<TopologicalVertex> vertices;
    private ArrayList<ArrayList<TopologicalVertex>> possiblePaths;
    private Colors colors;
    private int iterator;

    public MatchingOnGraph(Pane pane, String pattern, GeographicalGraph geographicalGraph){
        //this.pane = pane;
        this.pattern = pattern;
        //todo - rozmysliet, ci by sa to nedalo zmenit tak, aby tu musel byt vzdy jednoduchy graf
        this.geographicalGraph = geographicalGraph;
        this.iterator = 0;
        this.vertices = geographicalGraph.getGraph();
        this.colors = new Colors(pattern.length());
        this.possiblePaths = new ArrayList<>();
        for(TopologicalVertex vertex:this.vertices){
            this.possiblePaths.add(new ArrayList<>(List.of(vertex)));
        }
    }

    @Override
    public boolean hasNext() {
        return this.pattern.length() > this.iterator;
    }

    @Override
    public String nextStep() {
        for(TopologicalVertex vertex: this.vertices){
            for(Pair<String, TopologicalVertex> edge : vertex.getOutgoing()){

            }
        }
        ArrayList<Integer> endValues = new ArrayList<>();
        ArrayList<ArrayList<TopologicalVertex>> extendedPaths = new ArrayList<>();
        for(ArrayList<TopologicalVertex> path: this.possiblePaths){
            for(Pair<String, TopologicalVertex> edge: path.get(this.iterator).getOutgoing()){
                if(edge.getKey().equals(this.pattern.substring(this.iterator, this.iterator + 1))){
                    ArrayList extendedPath = new ArrayList(path);
                    path.add(edge.getValue());
                    extendedPaths.add(path);
                    endValues.add(edge.getValue().getValue());
                }
            }
        }
        endValues.sort(Integer::compare);
        return "Paths end in "+ endValues.toString();
    }

    @Override
    public GeographicalGraph getGeographicalGraph() {
        return null;
    }
}
