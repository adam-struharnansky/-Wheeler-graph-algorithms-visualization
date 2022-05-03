package com.example.demo2.algorithmDisplays.animatableNodes;

import com.example.demo2.algorithmDisplays.GraphDisplay;
import javafx.scene.layout.Pane;

public class EdgeFactory {
    private final GraphDisplay graphDisplay;
    private final Pane graphPane;
    private boolean directed = true;

    public EdgeFactory(GraphDisplay graphDisplay, Pane graphPane){
        this.graphDisplay = graphDisplay;
        this.graphPane = graphPane;
    }

    public EdgeFactory(GraphDisplay graphDisplay, Pane graphPane, boolean directed){
        this.graphDisplay = graphDisplay;
        this.graphPane = graphPane;
        this.directed = directed;
    }

    public Edge newEdge(Vertex from, Vertex to){
        if(this.directed){
            return new DirectedEdge(this.graphDisplay, this.graphPane, from, to);
        }
        else{
            return new Edge(this.graphDisplay, this.graphPane, from, to);
        }
    }

    public Edge newEdge(boolean directed, Vertex from, Vertex to){
        if(directed){
            return new DirectedEdge(this.graphDisplay, this.graphPane, from, to);
        }
        else{
            return new Edge(this.graphDisplay, this.graphPane, from, to);
        }
    }

}
