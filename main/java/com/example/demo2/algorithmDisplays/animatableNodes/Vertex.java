package com.example.demo2.algorithmDisplays.animatableNodes;

import com.example.demo2.algorithmDisplays.GraphDisplay;
import com.example.demo2.auxiliary.AuxiliaryMath;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/*
Undirected Vertex
 */
public class Vertex implements MoveAnimatable, ColorAnimatable, RelativeMoveAnimatable, AppearAnimatable {

    ArrayList<Circle> testCircles = new ArrayList<>();
    private void testCircle(double x, double y,Color color){
        Circle circle = new Circle();
        circle.setFill(color);
        circle.setRadius(1.5);
        circle.setLayoutY(y);
        circle.setLayoutX(x);
        this.pane.getChildren().add(circle);
        testCircles.add(circle);
    }

    private void testCircle(double x, double y){
        testCircle(x, y, Color.BLACK);
    }

    protected final Pane pane;
    protected final Group group;
    protected final Circle circle;//todo - nemoze byt lokalny, ak budeme chciet zmenit jeho velkost dynamicky - zoom
    protected final Text text;
    protected Color color = Color.AQUA;
    private boolean visible = true;
    protected double radius = 15.0;

    protected double relativeX;
    protected double relativeY;

    protected double x;
    protected double y;

    private final ArrayList<Edge> edges;

    protected GraphDisplay graphDisplay;

    Vertex(GraphDisplay graphDisplay, Pane pane){
        this.graphDisplay = graphDisplay;
        this.pane = pane;

        this.group = new Group();
        this.circle = new Circle();
        this.circle.setFill(this.color);
        this.circle.setRadius(this.radius);//todo - ako to urobit aby sa to dalo menit vseobecne
        //mozno to menit tak, ze iba zmensime velkost kruhov, a skratime velkosti hran v nejakom pomere.
        //-> to znamena vsetko dat pomerne dostredu. Podla toho vieme menit priblizenie
        this.circle.setStrokeWidth(3.0);
        this.circle.setStroke(Color.AQUA.brighter().brighter());
        this.circle.setStrokeDashOffset(10);
        this.text = new Text();
        this.group.getChildren().add(this.circle);
        this.group.getChildren().add(this.text);

        this.edges = new ArrayList<>();
        this.pane.getChildren().add(this.group);

        redraw();
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
        this.x = x;
        this.y = y;
        this.relativeX = x / (this.graphDisplay.getWidth() + 0.1);
        this.relativeY = y / (this.graphDisplay.getHeight() + 0.1);
        this.group.setLayoutX(x);
        this.group.setLayoutY(y);
        redraw();
    }

    @Override
    public void setPosition(Pair<Double, Double> position){
        setPosition(position.getKey(), position.getValue());
    }

    @Override
    public void setRelativePosition(double relativeX, double relativeY){
        if( relativeX < 0 || relativeY < 0 || relativeX > 1 || relativeY > 1){
            return;
        }
        this.x = relativeX * this.graphDisplay.getWidth();
        this.y = relativeY * this.graphDisplay.getHeight();
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.group.setLayoutX(this.x);
        this.group.setLayoutY(this.y);
        redraw();
    }

    @Override
    public void setRelativePosition(Pair<Double, Double> position){
        setRelativePosition(position.getKey(), position.getValue());
    }

    @Override
    public Pair<Double, Double> getRelativePosition(){
        return new Pair<>(this.relativeX, this.relativeY);
    }

    public double getRelativeX(){
        return this.relativeX;
    }

    public double getRelativeY() {
        return this.relativeY;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    @Override
    public Pair<Double, Double> getPosition(){
        return new Pair<>(getX(), getY());
    }

    void addIncomingEdge(Edge edge){
        if(!this.edges.contains(edge)){
            this.edges.add(edge);
            redraw();
        }
    }

    void addOutgoingEdge(Edge edge){
        if(!this.edges.contains(edge)){
            this.edges.add(edge);
            redraw();
        }
    }

    double betaAngle(double aX, double aY, double bX, double bY, double cX, double cY){
        double b = AuxiliaryMath.distance(aX, aY, cX, cY);
        double c = AuxiliaryMath.distance(aX, aY, bX, bY);
        if(b == 0 || c == 0){
            return 0.0;
        }
        double a = AuxiliaryMath.distance(bX, bY, cX, cY);
        return Math.acos((a*a + c*c - b*b)/(b*c));
    }

    public void redraw(){
        testCircles.forEach(circle1 -> pane.getChildren().remove(circle1));
        testCircles.clear();

        this.x = this.relativeX*this.graphDisplay.getWidth();
        this.y = this.relativeY*this.graphDisplay.getHeight();
        this.group.setLayoutX(this.x);
        this.group.setLayoutY(this.y);

        ArrayList<Point2D> ends = new ArrayList<>();
        for(int i = 0; i<this.edges.size();i++){
            ends.add(new Point2D(
                    Math.cos(2*Math.PI*i/this.edges.size())*this.radius + this.x,
                    Math.sin(2*Math.PI*i/this.edges.size())*this.radius + this.y));
        }

        for(int i = 0;i<this.edges.size();i++){
            this.edgeEnds.put(this.edges.get(i), ends.get(i));
        }

        this.edges.forEach(Edge::redraw);
        /*
        ArrayList<Pair<Edge, Vertex>> neighbours = new ArrayList<>();
        for(Edge edge:this.edges){
            if(edge.getVertexFrom() != this){
                neighbours.add(new Pair<>(edge, edge.getVertexFrom()));
            }
            else{
                neighbours.add(new Pair<>(edge, edge.getVertexTo()));
            }
        }

        ArrayList<Integer> pointNeighborMap = new ArrayList<>();
        ends.forEach(end -> pointNeighborMap.add(-1));
        for(int pointIndex = 0;pointIndex<ends.size();pointIndex++){
            double bestAngle = Double.NEGATIVE_INFINITY;
            int best = -1;
            for(int edgeIndex = 0;edgeIndex<neighbours.size();edgeIndex++){
                if(!pointNeighborMap.contains(edgeIndex)){
                    double angle = betaAngle(this.x, this.y, ends.get(pointIndex).getX(), ends.get(pointIndex).getY(),
                            neighbours.get(edgeIndex).getValue().x, neighbours.get(edgeIndex).getValue().y);
                    if(angle > bestAngle){
                        bestAngle = angle;
                        best = edgeIndex;
                    }
                }
            }
            pointNeighborMap.set(pointIndex, best);
        }

        int bestRotation = -1;
        double bestWorstAngle = Double.POSITIVE_INFINITY;

        double numberOfRotations = 16;
        double rotationAngle = 0.39269908;

        for(int rotation = 0; rotation < numberOfRotations; rotation++){
            double worstAngle = Double.POSITIVE_INFINITY;
            for(int i = 0;i<this.edges.size();i++){
                double rotatedPointX = Math.cos(2*Math.PI*i/this.edges.size() + rotation*rotationAngle)*this.radius + this.x;
                double rotatedPointY = Math.sin(2*Math.PI*i/this.edges.size() + rotation*rotationAngle)*this.radius + this.y;
                double angle = betaAngle(this.x, this.y, rotatedPointX, rotatedPointY,
                        neighbours.get(i).getValue().x, neighbours.get(i).getValue().y);
                System.out.println(rotation + " "+ i+" "+angle);
                if(angle < worstAngle){
                    worstAngle = angle;
                }
            }
            if(worstAngle < bestWorstAngle){
                bestRotation = rotation;
                bestWorstAngle = worstAngle;
            }
        }
        System.out.println(bestRotation);

        for(int i = 0;i<this.edges.size();i++){
            this.edgeEnds.replace(neighbours.get(pointNeighborMap.get(i)).getKey(), new Point2D(
                Math.cos(2*Math.PI*i/this.edges.size() + bestRotation*rotationAngle)*this.radius + this.x,
                Math.sin(2*Math.PI*i/this.edges.size() + bestRotation*rotationAngle)*this.radius + this.y
            ));
            testCircle(
                    Math.cos(2*Math.PI*i/this.edges.size() + bestRotation*rotationAngle)*this.radius + this.x,
                    Math.sin(2*Math.PI*i/this.edges.size() + bestRotation*rotationAngle)*this.radius + this.y
            );
        }

        */
        /*
        teraz to bude treba pretocit, tak aby bol najvacsi uhol co najmensi


         */

        /*
        najskor vypocitat tieto body - len ich dat na okruh v rovnakych odsekoch
        potom kazdemu vypocitat, ktory volny vrchol je k nemu uhlovo najblizsie
        potom otocit do kazdej strany, a zistit, ktory uhol je najlepsi
        potom treba pre kazdu hranu vlozit spravny bod

        toto je trochu problem, bude to musiet byt v oboch

        alebo, pridavat v directedVertex veci aj tu, a nemusi byt ziaden problem
        a teda, vobec nie je potreby directed vertex - je, on to ma ulozene
         */
    }

    private final HashMap<Edge, Point2D> edgeEnds = new HashMap<>();

    protected Point2D edgeEnd(Edge edge){
        if(this.edgeEnds.containsKey(edge)) {
            return this.edgeEnds.get(edge);
        }
        return new Point2D(this.x, this.y);
    }

    public void removeEdge(Edge edge){
        this.edges.remove(edge);
    }

    public void delete(){
        this.edges.forEach(Edge::delete);
        this.pane.getChildren().remove(this.group);
    }

    public String getValue(){
        return this.text.getText();
    }


    @Override
    public void setColor(Color color){
        this.color = color;
        this.circle.setFill(this.color);
    }

    @Override
    public Color getColor(){
        return this.color;
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

    @Override
    public void appear() {
        this.visible = true;
        this.group.setVisible(true);
        this.graphDisplay.moveVertexToVisible(this);
        this.edges.forEach(Edge::incidentVertexVisibilityChanged);
    }

    @Override
    public void disappear() {
        this.visible = false;
        this.group.setVisible(false);
        this.graphDisplay.moveVertexToInvisible(this);
        this.edges.forEach(Edge::incidentVertexVisibilityChanged);
    }

    @Override
    public double getOpacity(){
        return this.color.getOpacity();
    }

    @Override
    public void setOpacity(double opacity){
        this.text.setFill(new Color(0.0,0.0,0.0,opacity));
        setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), opacity));
    }

    public boolean isVisible() {
        return this.visible;
    }

    public ArrayList<Edge> getEdges(){
        return this.edges;
    }
}
