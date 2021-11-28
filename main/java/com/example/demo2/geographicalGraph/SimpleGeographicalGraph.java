package com.example.demo2.geographicalGraph;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;

public class SimpleGeographicalGraph implements GeographicalGraph{
    private boolean isBeingModifyByAlgorithm;
    private ArrayList<TopologicalVertex> vertices;
    private ArrayList<SimpleGeographicalVertex> simpleGeographicalVertices;
    private ArrayList<SimpleGeographicalEdge> simpleGeographicalEdges;
    private Pane pane;

    public SimpleGeographicalGraph(Pane pane){
        this.pane = pane;
        this.vertices = new ArrayList<>();
        this.simpleGeographicalEdges = new ArrayList<>();
        this.simpleGeographicalVertices = new ArrayList<>();
    }
    public SimpleGeographicalGraph(Pane pane, ArrayList<TopologicalVertex> vertices){
        this.pane = pane;
        this.vertices = vertices;
        //todo - naplnit spravne geoveci
    }
    //todo - vytvorit konstruktor, ktory bude mat vsetky veci, aj SGVs,SGEs, a iba to priradi, pripadne skontroluje

    private class SimpleGeographicalVertex{
        Group g;
        int value;
        TopologicalVertex topologicalVertex;
        private void BasicConstructor(double x, double y){
            this.g = new Group();
            this.g.setLayoutX(x);
            this.g.setLayoutY(y);
            this.g.setOnMouseDragged(mouseEvent -> {
                setPosition(mouseEvent.getSceneX() - pane.getLayoutX(),
                        mouseEvent.getSceneY() - pane.getLayoutY());
            });
            this.g.setOnMouseClicked(mouseEvent -> {
                //todo vytvorit nove okno, iba vtedy, ked to bude mozne tj nebude v nejakom algoritme
            });
            Circle c = new Circle(15);
            c.setFill(Color.AQUA);
            this.g.getChildren().add(c);
            pane.getChildren().add(g);
        }
        SimpleGeographicalVertex(TopologicalVertex topologicalVertex, double x, double y, int value){
            this.topologicalVertex = topologicalVertex;
            this.value = value;
            BasicConstructor(x,y);
            Text t = new Text(""+value);
            this.g.getChildren().add(t);
        }
        SimpleGeographicalVertex(TopologicalVertex topologicalVertex, double x, double y){
            this.topologicalVertex = topologicalVertex;
            BasicConstructor(x,y);
            Text t = new Text("");
            this.g.getChildren().add(t);
        }

        void setValue(int value) {
            //todo - nastavit to aj tak, aby sa dala value odstranit
            this.value = value;
            this.topologicalVertex.setValue(value);
            //todo - pozrit sa ci to vzdy funguje s tou 1kou, alebo tu dat nejaky cyklus, aby to overilo ci to je text
            ((Text)this.g.getChildren().get(1)).setText(""+value);
        }
        void setPosition(double x, double y){
            this.g.setLayoutX(x);
            this.g.setLayoutY(y);
            for(SimpleGeographicalEdge simpleGeographicalEdge: simpleGeographicalEdges){
                if(simpleGeographicalEdge.from.topologicalVertex == this.topologicalVertex){
                    simpleGeographicalEdge.redrawEdge();
                }
                if(simpleGeographicalEdge.to.topologicalVertex == this.topologicalVertex){
                    simpleGeographicalEdge.redrawEdge();
                }
            }
        }
        Pair<Double, Double> getPosition(){
            return new Pair<>(this.g.getLayoutX(), this.g.getLayoutY());
        }

    }

    private class SimpleGeographicalEdge{
        //todo - vymysliet ako ma to cele fungovat, Line je prilis neflexibilna, nejake krivky, tiez vymysliet spojenie s textom
        Line l;
        String text;
        Text textGraphical;
        SimpleGeographicalVertex from;
        SimpleGeographicalVertex to;
        SimpleGeographicalEdge(SimpleGeographicalVertex from, SimpleGeographicalVertex to, String text){
            this.l = new Line();
            pane.getChildren().add(this.l);
            this.from = from;
            this.to = to;
            this.text = text;
            this.textGraphical = new Text(text);
            pane.getChildren().add(this.textGraphical);
            redrawEdge();
        }
        SimpleGeographicalEdge(SimpleGeographicalVertex from, SimpleGeographicalVertex to){
            this.l = new Line();
            pane.getChildren().add(this.l);
            this.from = from;
            this.to = to;
            this.text = "";//todo, porozmyslat ci je toto najlepsie riesenie
            this.textGraphical = new Text();
            pane.getChildren().add(this.textGraphical);
            redrawEdge();
        }

        //chcmee zatial iba lajny
        //musi platit, ze nechce krizoat ziaden vrchol, ziaden text hrany, a ziaden zlom inej hrany
        //chceme, aby krizovala hrany s nejakym uhlom
        //a aby bola co najkrtsia
        //urobime najskor priamku  medzi danymi vrcholmi
        //zistime ci to plati, a ak nie, tak zacneme nad niecim rozmyslat
        //alebo v podstate sa to da nejakym prehladavanim
        //mozme si z toho urobit plochu, na ktorej si vytorime mieste, kde nemoze byt.
        //a po tomto ju prbehneme
        //urobit ich iba 8-uhlove?
        //tiez by bolo fajn mat pre kazdy znak vlastnu farbu - ale aby bola tmava, a rozlisitelna
        //tiez by bolo fajn, ze aby nesli blizko seba, bolo by to malo prahladne

        //dobry napad - otocit psimeno v smere. tiez mozno nechat pismeno cierne, a iba hranu zafarbit
        //potom by hrana mohla ist priamo cez pismeno



        //zmeni svoju poziciu tak, aby stale ukazovala z from do to
        void redrawEdge(){
            //todo - zmenit toto vykreslenie zatial iba lajna
            //System.out.println("redraw "+this.from.getPosition()+" "+this.to.getPosition());
            this.l.setStartX(this.from.getPosition().getKey());
            this.l.setStartY(this.from.getPosition().getValue());
            this.l.setEndX(this.to.getPosition().getKey());
            this.l.setEndY(this.to.getPosition().getValue());
            this.textGraphical.setLayoutX((this.from.getPosition().getKey()+this.to.getPosition().getKey())/2);
            this.textGraphical.setLayoutY((this.from.getPosition().getValue()+this.to.getPosition().getValue())/2);
        }
    }

    @Override
    public boolean addVertex(TopologicalVertex topologicalVertex) {
        if(this.vertices.contains(topologicalVertex)){
            return false;
        }
        this.vertices.add(topologicalVertex);
        this.simpleGeographicalVertices.add(new SimpleGeographicalVertex(topologicalVertex, 0 , 0));
        //todo osetrit to tak, aby skontrolovalo, ci sa to tu vsetko spravne pridalo
        //treba pridat SGE, aj pre prichadzajuce aj pre odchadzjauce hrany
        //prechadzat cez vsekty vrcholy v grafe, a zistit ci uz je tento v niektorom


        //todo
        //treba skontorlovat ci tu uz nie je
        //treba ho pridat do vertices
        //treba vytvorit novy SGV, so spravnymi vecami
        //treba tento pridat to listu SGVs
        //treba skontrolovat ci sa vsetko pridalo - ak nie vsetko navratit a return false;
        //ak vsetko ok, vratit true
        return true;
    }

    @Override
    public boolean addEdge(TopologicalVertex vFrom, TopologicalVertex vTo, String text) {
        if(!this.vertices.contains(vTo) || !this.vertices.contains(vFrom)){
            return false;
        }
        SimpleGeographicalVertex simpleGeographicalVertexFrom = null, simpleGeographicalVertexTo = null;
        for(SimpleGeographicalVertex simpleGeographicalVertex:this.simpleGeographicalVertices){
            if(simpleGeographicalVertex.topologicalVertex == vFrom){
                simpleGeographicalVertexFrom = simpleGeographicalVertex;
            }
            if(simpleGeographicalVertex.topologicalVertex == vTo){
                simpleGeographicalVertexTo = simpleGeographicalVertex;
            }
        }
        if(simpleGeographicalVertexFrom == null || simpleGeographicalVertexTo == null){
            return false;
        }
        this.simpleGeographicalEdges.add(
                new SimpleGeographicalEdge(simpleGeographicalVertexFrom, simpleGeographicalVertexTo, text));
        //todo
        //treba skontorlovat ci tu uz nie je
        //treba ho pridat do edges
        //treba vytvorit novy SGE, so spravnymi vecami
        //treba tuto pridat to listu SGEs
        //treba skontrolovat ci sa vsetko pridalo - ak nie vsetko navratit a return false;
        //ak vsetko ok, vratit true
        return true;
    }

    public boolean moveVertex(TopologicalVertex topologicalVertex, double x, double y){
        for(SimpleGeographicalVertex simpleGeographicalVertex:this.simpleGeographicalVertices){
            if(simpleGeographicalVertex.topologicalVertex == topologicalVertex){
                simpleGeographicalVertex.setPosition(x,y);
                return true;
            }
        }
        //todo
        //zisti ci V aj SGV obsahuju dany vertex, a potom zmeni jeho suradnice
        //na zadane
        //todo - urobit z toho aj animaciu
        return false;
    }

    //todo - pridat aj funckie na spajanie vrcholov pri tunelovani

    @Override
    public boolean removeVertex(TopologicalVertex topologicalVertex) {
        //todo
        //treba prehladat ci sa najkor nachadza vo vertices - ak nie,vratit false
        //najst vsetky edge, ktore maju niektou vec dany vertex (aj SGE, aj E), a odstrnit ich
        //pre kazdy vertex odstranit z jeho susedov dany vertex
        //ak sa vsetko podarilo, vratit true, inak false
        return false;
    }

    @Override
    public boolean removeEdge(TopologicalVertex topologicalVertex, String text) {
        //todo
        //treba zistit ci sa najskor nachadza v edges
        //potom odstranit z jej koncovych vertexov danych susedov
        //nakoniec ju odstranit z SGEs, potom aj z E, a vratit ci sa to cele podarilo
        return false;
    }

    @Override
    public boolean rewriteEdgeText(TopologicalVertex topologicalVertex, String text) {
        //todo
        //zistit ci je v Es a SGEs, a potom zmenit jej prislusne hodnoty
        //funkcia v SGE musi prekreslit aj skutocny vyzor
        return false;
    }

    @Override
    public boolean rewriteVertexValue(TopologicalVertex topologicalVertex) {
        //todo
        //zistit ci je v Vs aj SGVs, a potom zmenit prislusne hodnoty
        //funkcia v SGV musi prepkreslit aj skutocny vyzor
        return false;
    }

    @Override
    public ArrayList<TopologicalVertex> getGraph() {
        return null; //this.vertices;
    }

}
