package com.example.demo2.algorithmDisplays;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class OrientedGraphDisplay extends GraphDisplay{


    private final Pane pane;
    private final ArrayList<DirectedVertex> vertices;
    private final ArrayList<DirectedEdge> edges;

    private double zoom = 1;
    private double vertexRadius = 15.0;

    public OrientedGraphDisplay(VBox container, String name, int ratio) {
        super(container, name, ratio);
        this.pane = super.getPane();
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    @Override
    public void centre(){
        //todo - nastavit, aby sa zmenil zoom na
        //todo - urobit to mozno o cosi lepsie
        double mostLeft = Double.POSITIVE_INFINITY, mostRight = Double.NEGATIVE_INFINITY,
                mostTop = Double.POSITIVE_INFINITY, mostDown = Double.NEGATIVE_INFINITY;
        for(Vertex vertex:this.vertices){
            if(vertex.getX() < mostLeft){
                mostLeft = vertex.getX();
            }
            if(vertex.getX() > mostRight){
                mostRight = vertex.getX();
            }
            if(vertex.getY() < mostTop){
                mostTop = vertex.getY();
            }
            if(vertex.getY() > mostDown){
                mostDown = vertex.getY();
            }
        }
        double horizontalGap = super.getWidth()/(this.vertices.size() + 1),
                verticalGap = super.getHeight()/(this.vertices.size() + 1);
        double newHorizontalSize = super.getWidth() - 2*horizontalGap + 0.1, oldHorizontalSize = mostRight - mostLeft + 0.1,
                newVerticalSize = super.getHeight() - 2*verticalGap + 0.1, oldVerticalSize = mostDown - mostTop + 0.1;
        for(Vertex vertex:this.vertices){
            //musi platit trojclenka
            //vertex.getx - mL : oHS
            //nvertex.getx - hG : nHS
            //(vertex.getx - mL) / oHS = (nvertex.getx - hG) / nHS
            //nHS*(vertex.getx - mL) / oHS + hG= nvertex.getx
            vertex.setPosition(horizontalGap + newHorizontalSize*(vertex.getX() - mostLeft)/oldHorizontalSize,
                    verticalGap + newVerticalSize*(vertex.getY() - mostTop)/oldVerticalSize);

        }
    }

    @Override
    public void resize() {
        this.vertices.forEach(Vertex::redraw);
    }

    @Override
    public void beautify(){
        //todo
        System.out.println("beautify oriented graph display");
    }

    @Override
    public void zoom(){
        //todo
        //zvacsit vsetky vrcholy
        //oddialit ich od stredu,
        //ak by ale mal ist za hrnaicu, tak ostane na tom istom mieste
    }

    @Override
    public void unzoom(){
        //todo
    }

    @Override
    public DirectedVertex addVertex(){
        DirectedVertex vertex = new DirectedVertex(this);
        this.vertices.add(vertex);
        return vertex;
    }

    public void setVertexValue(Vertex vertex, String value){
        if(this.vertices.contains(vertex)){//je tu vobec podmienka potrebna?
            vertex.setValue(value);
        }
    }

    @Override
    public DirectedEdge addEdge(Vertex from, Vertex to){
        //todo - asi aj skontrolovat, ci tam nie su nasobne hrany, toto treba nejako urobit
        DirectedEdge edge = new DirectedEdge(this, from, to);
        this.edges.add(edge);
        from.addOutgoingEdge(edge);
        to.addIncomingEdge(edge);
        return edge;
    }
}








