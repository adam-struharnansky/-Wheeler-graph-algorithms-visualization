package com.example.demo2.algorithmDisplays;

import com.example.demo2.auxiliary.AuxiliaryMath;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;

import java.util.Objects;

public class DirectedEdge extends Edge{

    private final Polygon triangle;

    DirectedEdge(GraphDisplay graphDisplay, Vertex vertexFrom, Vertex vertexTo){
        super(graphDisplay, vertexFrom, vertexTo);
        this.triangle = new Polygon(4.0, 0.0, 0.0, 8.0, 8.0, 8.0);//todo - dynamicke menenie velksoti
        this.graphDisplay.getPane().getChildren().add(this.triangle);
    }

    @Override
    public void delete() {
        super.delete();
        this.graphDisplay.getPane().getChildren().remove(this.triangle);
    }

    @Override
    public void redraw() {
        super.redraw();
        if(this.triangle == null){//ked je volany od konstruktora od super
            return;
        }

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

        this.triangle.setLayoutX(this.vertexTo.getPosition().getKey() + directionVector.getKey() - 4.0);//todo - pri zmene to je inde
        this.triangle.setLayoutY(this.vertexTo.getPosition().getValue() + directionVector.getValue() - 4.0);//todo dynamicka zmena

        double rotationAngle = 0;
        //todo - skontorlovat ci toto funguje, je tu zmena == na equals, pozriet dokumentaciu
        if(Objects.equals(this.vertexFrom.getPosition().getKey(), this.vertexTo.getPosition().getKey())){
            if(this.vertexFrom.getPosition().getValue() > this.vertexTo.getPosition().getValue()){
                rotationAngle = Math.PI;
            }
        }
        else{
            Pair<Double, Double> p = new Pair<>(this.vertexFrom.getPosition().getKey(), this.vertexFrom.getPosition().getValue() - 40);
            double a = AuxiliaryMath.vectorSize(this.vertexFrom.getPosition(), this.vertexTo.getPosition());
            double b = 40.0; // vectorSize(this.from.getPosition(), p) = 40.0
            double c = AuxiliaryMath.vectorSize(this.vertexTo.getPosition(), p);
            rotationAngle = Math.acos(-(c*c - a*a - b*b)/(2*a*b));
            if(this.vertexFrom.getPosition().getKey() > this.vertexTo.getPosition().getKey()){
                rotationAngle = 2*Math.PI - rotationAngle;
            }
        }
        this.triangle.setRotate(rotationAngle*57.2957795);
    }
}

class DirectedEdgeFactory extends EdgeFactory{

    @Override
    public Edge newEdge(GraphDisplay graphDisplay, Vertex vertexFrom, Vertex vertexTo) {
        return new DirectedEdge(graphDisplay, vertexFrom, vertexTo);
    }
}
