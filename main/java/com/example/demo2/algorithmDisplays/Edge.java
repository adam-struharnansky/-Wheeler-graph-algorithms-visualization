package com.example.demo2.algorithmDisplays;

import com.example.demo2.animations.MoveAnimatable;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.util.Pair;


public class Edge {

    protected final Label label;
    protected final Line line;

    protected final Vertex vertexFrom;
    protected final Vertex vertexTo;
    protected GraphDisplay graphDisplay;


    Edge(GraphDisplay graphDisplay, Vertex vertexFrom, Vertex vertexTo){
        this.graphDisplay = graphDisplay;
        this.line = new Line();
        this.graphDisplay.getPane().getChildren().add(this.line);
        this.vertexFrom = vertexFrom;
        this.vertexTo = vertexTo;
        this.label = new Label();
        this.label.setStyle("-fx-font-weight: bold");
        this.graphDisplay.getPane().getChildren().add(this.label);

        redraw();
    }

    public void setText(String text){
        this.label.setText(text);
    }

    public void delete(){
        this.graphDisplay.getPane().getChildren().remove(this.line);
    }

    public void redraw(){
        Pair<Double, Double> directionVector = new Pair<>(
                this.vertexFrom.getPosition().getKey()-this.vertexTo.getPosition().getKey(),
                this.vertexFrom.getPosition().getValue()-this.vertexTo.getPosition().getValue());
        double directionVectorSize = Math.sqrt(directionVector.getKey()*directionVector.getKey() +
                directionVector.getValue()*directionVector.getValue());
        if(directionVectorSize == 0.0){
            directionVectorSize = 0.1;//todo - skontrolovat toto, asi to cele prepisat, mozno ked to bude
            //az take male, ze to nebude ani vidno, bude kratsi ako radius*2, tak to nevykreslit
        }
        directionVector = new Pair<>(this.graphDisplay.vertexRadius*directionVector.getKey()/directionVectorSize,
                this.graphDisplay.vertexRadius*directionVector.getValue()/directionVectorSize);
        this.line.setStartX(this.vertexFrom.getPosition().getKey() - directionVector.getKey());
        this.line.setStartY(this.vertexFrom.getPosition().getValue() - directionVector.getValue());
        this.line.setEndX(this.vertexTo.getPosition().getKey() + directionVector.getKey());
        this.line.setEndY(this.vertexTo.getPosition().getValue() + directionVector.getValue());
        this.label.setLayoutX((this.vertexFrom.getPosition().getKey()+this.vertexTo.getPosition().getKey())/2);
        this.label.setLayoutY((this.vertexFrom.getPosition().getValue()+this.vertexTo.getPosition().getValue())/2);
    }
}

class EdgeFactory{
    public Edge newEdge(GraphDisplay graphDisplay, Vertex vertexFrom, Vertex vertexTo) {
        return new Edge(graphDisplay, vertexFrom, vertexTo);
    }
}
