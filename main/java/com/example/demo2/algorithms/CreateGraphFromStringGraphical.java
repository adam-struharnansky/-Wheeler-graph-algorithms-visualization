package com.example.demo2.algorithms;

import com.example.demo2.geographicalGraph.GeographicalGraph;
import com.example.demo2.geographicalGraph.SimpleGeographicalGraph;
import com.example.demo2.geographicalGraph.TopologicalVertex;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashSet;

public class CreateGraphFromStringGraphical implements GraphAlgorithm{
    private final SimpleGeographicalGraph simpleGeographicalGraph;
    private final BWT bwt;
    private final HashSet<TopologicalVertex> unnumberedVertices;

    public CreateGraphFromStringGraphical(Pane pane, BWT bwt, String text){
        this.bwt = bwt;
        //todo, vyriesit velkosti
        double dI, dSize = text.length();
        pane.setPrefHeight(dSize*45);
        pane.setPrefWidth(dSize*45);
        pane.setMinSize(dSize*25, dSize*25);

        this.simpleGeographicalGraph = new SimpleGeographicalGraph(pane);

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
            int endEdge = i % text.length();
            int startEdge = (i+1) % text.length();
            //todo skontrolovat
            topologicalVertices.get(startEdge).addOutgoingEdge(text.charAt(i)+"",topologicalVertices.get(endEdge));
            topologicalVertices.get(endEdge).addIncomingEdge(text.charAt(i)+"",topologicalVertices.get(startEdge));
            this.simpleGeographicalGraph.addEdge(topologicalVertices.get(startEdge),
                    topologicalVertices.get(endEdge), text.charAt(i)+"");
        }
        this.unnumberedVertices = new HashSet<>();
        this.unnumberedVertices.addAll(topologicalVertices);
    }


    @Override
    public boolean hasNext() {
        //treba to? aj tak toto nikdy nebude spustene, kontroluje sa podla bwt casti
        return !this.unnumberedVertices.isEmpty();
    }

    @Override
    public String nextStep() {
        int nextVertexNumber = this.bwt.getSortedRotations().size() - this.unnumberedVertices.size();
        String reverseLine = new StringBuilder(this.bwt.getSortedRotations().get(nextVertexNumber)).reverse().toString();
        for(TopologicalVertex vertex:this.unnumberedVertices){
            int i = 0;
            TopologicalVertex iteratorVertex = vertex;
            while(true){
                if(!iteratorVertex.getOutgoing().get(0).getKey().equals(reverseLine.charAt(i)+"")){
                    break;
                }
                if(iteratorVertex.getOutgoing().get(0).getKey().equals("$")){
                    this.simpleGeographicalGraph.rewriteVertexValue(vertex, nextVertexNumber);
                    this.unnumberedVertices.remove(vertex);
                    return null;
                }
                iteratorVertex = iteratorVertex.getOutgoing().get(0).getValue();
                i++;
            }
        }
        return "Error";
    }

    /*
    @Override
    public ArrayList<TopologicalVertex> getGraph() {
        return null;
    }

     */

    @Override
    public GeographicalGraph getGeographicalGraph() {
        return this.simpleGeographicalGraph;
    }

}
