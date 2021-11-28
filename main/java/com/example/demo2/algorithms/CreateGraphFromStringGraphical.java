package com.example.demo2.algorithms;

import com.example.demo2.geographicalGraph.SimpleGeographicalGraph;
import com.example.demo2.geographicalGraph.TopologicalVertex;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class CreateGraphFromStringGraphical implements GraphAlgorithm{
    private int numberOfNamedVertices;
    private final SimpleGeographicalGraph simpleGeographicalGraph;

    public CreateGraphFromStringGraphical(Pane pane, BWT bwt, String text){
        //todo, vyriesit velkosti
        double dI, dSize = text.length();
        pane.setPrefHeight(dSize*45);
        pane.setPrefWidth(dSize*45);
        pane.setMinSize(dSize*25, dSize*25);

        this.simpleGeographicalGraph = new SimpleGeographicalGraph(pane);
        //todo
        //vytvorit graf ktory bude mat vrcholy/hrany podla size
        //hranam da nazvy asi az v druhom kole, to sa este upresni

        ArrayList<TopologicalVertex> topologicalVertices = new ArrayList<>();
        for(int i = 0;i<text.length();i++){
            dI = i;
            topologicalVertices.add(new TopologicalVertex());
            this.simpleGeographicalGraph.addVertex(topologicalVertices.get(i));
            this.simpleGeographicalGraph.moveVertex(topologicalVertices.get(i),
                    Math.sin((2*Math.PI*dI)/dSize)* 15*text.length() + 15*dSize + 15,
                    -Math.cos((2*Math.PI*dI)/dSize)* 15*text.length() + 15*dSize + 15);
        }
        for(int i = 0;i<text.length();i++){
            int startEdge = i % text.length();
            int endEdge = (i+1) % text.length();
            System.out.println(this.simpleGeographicalGraph.addEdge(topologicalVertices.get(startEdge),
                    topologicalVertices.get(endEdge), text.charAt(i)+""));
        }
        //todo - pridaavt spravne hrany
    }


    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public String nextStep() {
        return null;
    }

    @Override
    public ArrayList<TopologicalVertex> getGraph() {
        return null;
    }

}
