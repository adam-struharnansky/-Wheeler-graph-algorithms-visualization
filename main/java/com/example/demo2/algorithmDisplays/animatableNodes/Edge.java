package com.example.demo2.algorithmDisplays.animatableNodes;


import com.example.demo2.algorithmDisplays.GraphDisplay;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.util.Pair;

import java.util.ArrayList;

/*
treba zistit kde su vsetky vrcholy
ak ma niekde hrana nejaku vec, tak tu nechceme
chceme vlozit teda krivku, ktora bude mat body, a medzi nimi
 */

public class Edge implements AppearAnimatable, ColorAnimatable{

    protected final Pane graphPane;
    protected String text;
    protected final Label label;
    //protected final Line line;

    protected final ArrayList<CubicCurve> curves = new ArrayList<>();
    protected final ArrayList<Integer> curveIds = new ArrayList<>();
    protected double width = 1.5;
    protected double gap = 2.0;

    protected final Vertex vertexFrom;
    protected final Vertex vertexTo;
    protected GraphDisplay graphDisplay;

    protected boolean visible;
    protected double opacity;

    protected Color color;

    Edge(GraphDisplay graphDisplay, Pane graphPane, Vertex vertexFrom, Vertex vertexTo){
        this.graphDisplay = graphDisplay;
        this.graphPane = graphPane;
        //this.line = new Line();
        //this.graphPane.getChildren().add(this.line);
        this.vertexFrom = vertexFrom;
        this.vertexTo = vertexTo;
        this.label = new Label();
        this.label.setStyle("-fx-font-weight: bold");
        this.graphPane.getChildren().add(this.label);
        this.color = Color.BLACK;

        addWay(0, Color.BLACK);
        addWay(1, Color.RED);
        addWay(2, Color.GREEN);
        addWay(3, Color.BLUE);

        redraw();
    }

    public String getText(){
        return this.text;
    }

    public void setText(String text){
        this.label.setText(text);
        this.text = text;
    }

    public void setText(int text){
        setText(text + "");
    }

    public void setText(char text){
        setText(text+"");
    }

    public void delete(){
        //this.graphPane.getChildren().remove(this.line);
        for(CubicCurve curve:this.curves){
            this.graphPane.getChildren().remove(curve);
        }
    }

    public void addWay(int id, Color color){
        if(!this.curveIds.contains(id)){
            this.curveIds.add(id);
            CubicCurve curve = new CubicCurve();
            curve.setFill(Color.TRANSPARENT);
            curve.setStroke(color);
            curve.setStrokeWidth(width);
            this.curves.add(curve);
            this.graphPane.getChildren().add(curve);
            redraw();
        }
    }

    public void removeWay(int id){

    }
    public void redraw(){
        Point2D startPoint = vertexFrom.edgeEnd(this);
        Point2D startControl = new Point2D(
                startPoint.getX() + (startPoint.getX() - vertexFrom.x),
                startPoint.getY() + (startPoint.getY() - vertexFrom.y));
        Point2D endPoint = vertexTo.edgeEnd(this);
        Point2D endControl = new Point2D(
                endPoint.getX() + (endPoint.getX() - vertexTo.x),
                endPoint.getY() + (endPoint.getY() - vertexTo.y));


        Point2D spv, scv, epv, ecv;
        spv = new Point2D(-(startPoint.getY() - startControl.getY())/(startPoint.distance(startControl)),
                (startPoint.getX() - startControl.getX())/(startPoint.distance(startControl)));
        epv = new Point2D((endPoint.getY() - endControl.getY())/(endPoint.distance(endControl)),
                -(endPoint.getX() - endControl.getX())/(endPoint.distance(endControl)));

        if(true){//S
            scv = new Point2D(-(startPoint.getX() - vertexFrom.x)/vertexFrom.radius,
                    -(startPoint.getY() - vertexFrom.y)/vertexFrom.radius);
            ecv = new Point2D((endPoint.getX() - vertexTo.x)/vertexTo.radius,
                    (endPoint.getY() - vertexTo.y)/vertexTo.radius);


        }
        else{//U
            scv = new Point2D((startPoint.getX() - vertexFrom.x)/vertexFrom.radius,
                    (startPoint.getY() - vertexFrom.y)/vertexFrom.radius);
            ecv = new Point2D((endPoint.getX() - vertexTo.x)/vertexTo.radius,
                    (endPoint.getY() - vertexTo.y)/vertexTo.radius);


        }
        /*
        pre vektory spv a ecv plati, ze jeden je v smere hodinovych ruciciek, druhy je oproti
        zavisia iba od stredu, okraja

        controlne su iba suctom jednotlyvych vektorov, pri U a S je ale rozdiel
         */


        for(int i = 0;i<this.curves.size();i++){
            this.curves.get(i).setStartX(startPoint.getX() + i*spv.getX()*width*gap);
            this.curves.get(i).setStartY(startPoint.getY() + i*spv.getY()*width*gap);
            this.curves.get(i).setControlX1(startControl.getX() + i*spv.getX()*width*gap + i*scv.getX()*width*gap);
            this.curves.get(i).setControlY1(startControl.getY() + i*spv.getY()*width*gap + i*scv.getY()*width*gap);
            this.curves.get(i).setControlX2(endControl.getX() + i*epv.getX()*width*gap + i*ecv.getX()*width*gap);
            this.curves.get(i).setControlY2(endControl.getY() + i*epv.getY()*width*gap + i*ecv.getY()*width*gap);
            this.curves.get(i).setEndX(endPoint.getX() + i*epv.getX()*width*gap);
            this.curves.get(i).setEndY(endPoint.getY() + i*epv.getY()*width*gap);
        }

        this.label.setLayoutX((startControl.getX() + endControl.getX())/2);
        this.label.setLayoutY((startControl.getY() + endControl.getY())/2);


/*
        this.curve.setStartX(startPoint.getX());
        this.curve.setStartY(startPoint.getY());
        this.curve.setControlX1(startControl.getX());
        this.curve.setControlY1(startControl.getY());
        this.curve.setControlX2(endControl.getX());
        this.curve.setControlY2(endControl.getY());
        this.curve.setEndX(endPoint.getX());
        this.curve.setEndY(endPoint.getY());
*/

        /*
        Pair<Double, Double> directionVector = new Pair<>(
                this.vertexFrom.getPosition().getKey()-this.vertexTo.getPosition().getKey(),
                this.vertexFrom.getPosition().getValue()-this.vertexTo.getPosition().getValue());
        double directionVectorSize = Math.sqrt(directionVector.getKey()*directionVector.getKey() +
                directionVector.getValue()*directionVector.getValue());
        if(directionVectorSize == 0.0){
            directionVectorSize = 0.1;//todo - skontrolovat toto, asi to cele prepisat, mozno ked to bude
            //az take male, ze to nebude ani vidno, bude kratsi ako radius*2, tak to nevykreslit
        }
        directionVector = new Pair<>(this.graphDisplay.getVertexRadius()*directionVector.getKey()/directionVectorSize,
                this.graphDisplay.getVertexRadius()*directionVector.getValue()/directionVectorSize);
        this.line.setStartX(this.vertexFrom.getPosition().getKey() - directionVector.getKey());
        this.line.setStartY(this.vertexFrom.getPosition().getValue() - directionVector.getValue());
        this.line.setEndX(this.vertexTo.getPosition().getKey() + directionVector.getKey());
        this.line.setEndY(this.vertexTo.getPosition().getValue() + directionVector.getValue());
        this.label.setLayoutX((this.vertexFrom.getPosition().getKey()+this.vertexTo.getPosition().getKey())/2);
        this.label.setLayoutY((this.vertexFrom.getPosition().getValue()+this.vertexTo.getPosition().getValue())/2);

         */
    }

    public Vertex getVertexFrom() {
        return this.vertexFrom;
    }

    public Vertex getVertexTo(){
        return this.vertexTo;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
        //this.line.setVisible(visible);
        for(CubicCurve curve:this.curves){
            curve.setVisible(visible);
        }
        this.label.setVisible(visible);
    }

    public void incidentVertexVisibilityChanged(){
        //todo, skontrolovat toto, mozno to zmenit
        setVisible(this.vertexFrom.isVisible() && this.vertexTo.isVisible());
    }

    @Override
    public void appear() {
        setVisible(true);
    }

    @Override
    public void disappear() {
        setVisible(false);
    }


    @Override
    public double getOpacity(){
        return this.opacity;
    }

    @Override
    public void setOpacity(double opacity){
        this.opacity = opacity;
        this.label.setOpacity(opacity);
        //this.line.setOpacity(opacity);
        for(CubicCurve curve:this.curves){
            curve.setOpacity(opacity);
        }
    }

    public boolean isVisible() {
        return this.visible;
    }


    @Override
    public void setColor(Color color) {
        this.color = color;
        this.label.setTextFill(color);
    }

    @Override
    public Color getColor() {
        return this.color;
    }
}

