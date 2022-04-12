package com.example.demo2.algorithmDisplays;

import com.example.demo2.animations.MoveAnimatable;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;

/*
Undirected Vertex
 */
public class Vertex implements MoveAnimatable {

    protected final Group group;
    protected final Circle circle;//todo - nemoze byt lokalny, ak budeme chciet zmenit jeho velkost dynamicky - zoom
    protected final Text text;

    private double relativeX;
    private double relativeY;

    private final ArrayList<Edge> edges;

    protected GraphDisplay graphDisplay;

    Vertex(GraphDisplay graphDisplay){
        this.graphDisplay = graphDisplay;

        this.group = new Group();
        this.circle = new Circle();
        this.circle.setFill(Color.AQUA);
        this.circle.setRadius(15.0);//todo - ako to urobit aby sa to dalo menit vseobecne
        //mozno to menit tak, ze iba zmensime velkost kruhov, a skratime velkosti hran v nejakom pomere.
        //-> to znamena vsetko dat pomerne dostredu. Podla toho vieme menit priblizenie
        this.text = new Text();
        this.group.getChildren().add(this.circle);
        this.group.getChildren().add(this.text);

        this.edges = new ArrayList<>();

        this.group.setOnMouseDragged(mouseEvent ->
        {
            setPosition(mouseEvent.getX(), mouseEvent.getY());



        });
        this.graphDisplay.getPane().getChildren().add(this.group);
    }

    public void setValue(String value){
        this.text.setText(value);
    }

    public void setValue(int value){
        setValue(value+"");
    }

    public void setValue(char value){
        setValue(value+"");
    }

    @Override
    public void setPosition(double x, double y){
        if( x < 0 || y < 0 || x > this.graphDisplay.getWidth() || y > this.graphDisplay.getHeight()){
            return;
        }
        this.group.setLayoutX(x);
        this.group.setLayoutY(y);
        this.edges.forEach(Edge::redraw);
    }

    public void setRelativePosition(double relativeX, double relativeY){
        if( relativeX < 0 || relativeY < 0 || relativeX > 1 || relativeY > 1){
            return;
        }
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.group.setLayoutX(this.graphDisplay.getWidth()*relativeX);
        this.group.setLayoutY(this.graphDisplay.getHeight()*relativeY);
        this.edges.forEach(Edge::redraw);
    }

    public double getRelativeX(){
        return this.relativeX;
    }

    public double getRelativeY() {
        return this.relativeY;
    }

    //todo - pridat tu to, aby sme mohli zmenit miesta, kde sa daju hrany
    //teda getX(Edge e), a to vypluje nieco, aby neboli tak nakope
    //hlvane dolezite pri directed

    public double getX(){
        return this.group.getLayoutX();
    }

    public double getY(){
        return this.group.getLayoutY();
    }

    @Override
    public Pair<Double, Double> getPosition(){
        return new Pair<>(this.getX(), this.getY());
    }

    void addIncomingEdge(Edge edge){
        if(!this.edges.contains(edge)){
            this.edges.add(edge);
        }
    }

    void addOutgoingEdge(Edge edge){
        if(!this.edges.contains(edge)){
            this.edges.add(edge);
        }
    }

    public void redraw(){
        setRelativePosition(this.relativeX, this.relativeY);
    }

    void removeEdge(Edge edge){
        this.edges.remove(edge);
    }

    void delete(){
        this.graphDisplay.getPane().getChildren().remove(this.group);
    }

    public void setColor(Color color){
        this.circle.setFill(color);
    }

    public void highlight(){
        //todo ?
        this.circle.setStrokeWidth(2.0);
        this.text.setStyle("-fx-font-weight: bold");
    }

    public void unhighlight(){
        //todo
        this.circle.setStrokeWidth(1.0);
        this.text.setStyle("-fx-font-weight: normal");

    }

}

class VertexFactory{
    public Vertex newVertex(GraphDisplay graphDisplay){
        return new Vertex(graphDisplay);
    }
}
