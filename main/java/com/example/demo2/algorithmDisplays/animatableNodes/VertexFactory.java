package com.example.demo2.algorithmDisplays.animatableNodes;

import com.example.demo2.algorithmDisplays.GraphDisplay;
import javafx.scene.layout.Pane;

public class VertexFactory {

    private final GraphDisplay graphDisplay;
    private final Pane graphPane;
    private boolean directed = true;

    public VertexFactory(GraphDisplay graphDisplay, Pane graphPane){
        this.graphDisplay = graphDisplay;
        this.graphPane = graphPane;
    }

    public VertexFactory(GraphDisplay graphDisplay, Pane graphPane, boolean directed){
        this.graphDisplay = graphDisplay;
        this.graphPane = graphPane;
        this.directed = directed;
    }

    public Vertex newVertex(){
        if(this.directed){
            return new DirectedVertex(this.graphDisplay, this.graphPane);
        }
        else{
            return new Vertex(this.graphDisplay, this.graphPane);
        }
    }

    public Vertex newVertex(boolean directed){
        if(directed){
            return new DirectedVertex(this.graphDisplay, this.graphPane);
        }
        else{
            return new Vertex(this.graphDisplay, this.graphPane);
        }
    }
}
