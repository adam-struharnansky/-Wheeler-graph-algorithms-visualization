package com.example.demo2.algorithmDisplays;

import javafx.util.Pair;

import java.util.ArrayList;

public class DirectedVertex extends Vertex{

    private final ArrayList<Edge> incoming;
    private final ArrayList<Edge> outgoing;

    DirectedVertex(GraphDisplay graphDisplay){
        super(graphDisplay);

        this.incoming = new ArrayList<>();
        this.outgoing = new ArrayList<>();
    }

    @Override
    public void setPosition(double x, double y){
        /*
        ako ma toto fungovat, algoritmus nevie, ze sme niekde... s tymto asi nerobit.. mozno iba
        tak, ze budeme chciet zmenit poziciu iba vtedy, ked ich budeme spajat.. inak
        mozme davat na zaciatku
         */
        if( x < 0 || y < 0 || x > this.graphDisplay.getWidth() || y > this.graphDisplay.getHeight()){
            return;
        }
        this.group.setLayoutX(x);
        this.group.setLayoutY(y);
        for(Edge edge: this.outgoing){
            edge.redraw();
        }
        for(Edge edge:this.incoming){
            edge.redraw();
        }
    }

    public String getValue(){
        return this.text.getText();
    }

    @Override
    public void addIncomingEdge(Edge edge){
        if (!this.incoming.contains(edge)) {
            this.incoming.add(edge);
        }
    }

    @Override
    public void addOutgoingEdge(Edge edge){
        if(!this.outgoing.contains(edge)){
            this.outgoing.add(edge);
        }
    }

    @Override
    public void removeEdge(Edge edge){
        this.outgoing.remove(edge);
        this.incoming.remove(edge);
    }

    @Override
    public void redraw(){
        super.redraw();
        this.outgoing.forEach(Edge::redraw);
        this.incoming.forEach(Edge::redraw);
    }
}

class DirectedVertexFactory extends VertexFactory {

    @Override
    public Vertex newVertex(GraphDisplay graphDisplay) {
        return new DirectedVertex(graphDisplay);
    }
}