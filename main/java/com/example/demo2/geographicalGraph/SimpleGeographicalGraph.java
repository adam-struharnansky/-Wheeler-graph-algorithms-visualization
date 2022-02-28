package com.example.demo2.geographicalGraph;

import com.example.demo2.auxiliary.AuxiliaryMath;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.HashSet;

public class SimpleGeographicalGraph implements GeographicalGraph{
    private double vertexRadius = 15.0;
    //private double vectorSize = 1;//chceme vsetko nasobit touto konstantou, ked niekto nieco urobi?
    private int beautifyNumber = 3;
    //todo - pridat moznost menit konstatny velkosti - pismo, a velkost utvarov


    //nejakym sposobom dat hranam farby
    //bolo by fajn mat nejakú mimo triedu, s ktorou by sme vedli ho premenit na dvojity
    //alebo by to mohlo byt zo ho vytvori takyto?
    //alebo podla toho co bude na zaciatku naskladane v triede
    //mohli by sme to zmenit tak, aby to dokzazal oiba pridat niektore vrhcholy navyse

    private boolean isBeingModifyByAlgorithm;
    private final HashSet<TopologicalVertex> vertices;
    private final HashSet<SimpleGeographicalVertex> simpleGeographicalVertices;
    private final HashSet<SimpleGeographicalEdge> simpleGeographicalEdges;
    private final Pane pane;

    public SimpleGeographicalGraph(Pane pane){
        this.pane = pane;
        this.vertices = new HashSet<>();
        this.simpleGeographicalEdges = new HashSet<>();
        this.simpleGeographicalVertices = new HashSet<>();
    }

    public SimpleGeographicalGraph(Pane pane, HashSet<TopologicalVertex> vertices){
        this.pane = pane;
        this.vertices = vertices;
        this.simpleGeographicalEdges = new HashSet<>();
        this.simpleGeographicalVertices = new HashSet<>();
        //todo - naplnit SGEs SGVs podla vertices

    }

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
            this.value = value;
            this.topologicalVertex.setValue(value);
            //todo - skontrolovat ci bude vzdy fungovat zobranie 1-veho prvku z G, ci to vzdy bude text
            if(value == -1){
                ((Text)this.g.getChildren().get(1)).setText("");
            }
            else{
                ((Text)this.g.getChildren().get(1)).setText(""+value);
            }
        }

        void setPosition(double x, double y){
            x = Math.max(x, 0);
            x = Math.min(x, pane.getWidth());
            y = Math.max(0, y);
            y = Math.min(y, pane.getHeight());
            this.g.setLayoutX(x);
            this.g.setLayoutY(y);
            for(SimpleGeographicalEdge simpleGeographicalEdge: simpleGeographicalEdges){
                if(simpleGeographicalEdge.from.topologicalVertex == this.topologicalVertex){
                    simpleGeographicalEdge.redraw();
                }
                if(simpleGeographicalEdge.to.topologicalVertex == this.topologicalVertex){
                    simpleGeographicalEdge.redraw();
                }
            }
        }

        void erase(){
            pane.getChildren().remove(this.g);
        }

        Pair<Double, Double> getPosition(){
            return new Pair<>(this.g.getLayoutX(), this.g.getLayoutY());
        }

        void highlight(){
            //todo
        }

        void unhighlight(){
            //todo
        }
    }

    private class SimpleGeographicalEdge{
        //todo - porozmyslat, ci nezmenit Line na nieco ine, je to trochu neflexibilne, a pri nasobnych hranach to je zle
        //todo - tiez to treba nejako vymysliet ako ma vyzerat slucka
        //todo - porozmyslat, ako by sa dala znazornit viacfarebna hrana, mozno posunut jednu suranicu jednym smerom
        Line l;
        Polygon triangle;
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
            //this.textGraphical.setStyle("-fx-font-weight: bold");
            pane.getChildren().add(this.textGraphical);
            this.triangle = new Polygon(4.0, 0.0, 0.0, 8.0, 8.0, 8.0);
            pane.getChildren().add(this.triangle);
            redraw();
        }

        void redraw(){
            Pair<Double, Double> directionVector = new Pair<>(
                    this.from.getPosition().getKey()-this.to.getPosition().getKey(),
                    this.from.getPosition().getValue()-this.to.getPosition().getValue());
            Double directionVectorSize = Math.sqrt(directionVector.getKey()*directionVector.getKey() +
                    directionVector.getValue()*directionVector.getValue());
            if(directionVectorSize == 0.0){
                directionVectorSize = 0.1;//todo - skontrolovat toto, asi to cele prepisat, mozno ked to bude
                //az take male, ze to nebude ani vidno, bude kratsi ako radius*2, tak to nevykreslit
            }
            directionVector = new Pair<Double, Double>(vertexRadius*directionVector.getKey()/directionVectorSize,
                    vertexRadius*directionVector.getValue()/directionVectorSize);
            this.l.setStartX(this.from.getPosition().getKey() - directionVector.getKey());
            this.l.setStartY(this.from.getPosition().getValue() - directionVector.getValue());
            this.l.setEndX(this.to.getPosition().getKey() + directionVector.getKey());
            this.l.setEndY(this.to.getPosition().getValue() + directionVector.getValue());
            this.textGraphical.setLayoutX((this.from.getPosition().getKey()+this.to.getPosition().getKey())/2);
            this.textGraphical.setLayoutY((this.from.getPosition().getValue()+this.to.getPosition().getValue())/2);
            this.triangle.setLayoutX(this.to.getPosition().getKey() + directionVector.getKey() - 4.0);
            this.triangle.setLayoutY(this.to.getPosition().getValue() + directionVector.getValue() - 4.0);

            double rotationAngle = 0;
            if(this.from.getPosition().getKey() == this.to.getPosition().getKey()){
                if(this.from.getPosition().getValue() > this.to.getPosition().getValue()){
                    rotationAngle = Math.PI;
                }
            }
            else{
                Pair<Double, Double> p = new Pair<>(this.from.getPosition().getKey(), this.from.getPosition().getValue() - 40);
                double a = AuxiliaryMath.vectorSize(this.from.getPosition(), this.to.getPosition());
                double b = 40.0; // vectorSize(this.from.getPosition(), p) = 40.0
                double c = AuxiliaryMath.vectorSize(this.to.getPosition(), p);
                rotationAngle = Math.acos(-(c*c - a*a - b*b)/(2*a*b));
                if(this.from.getPosition().getKey() > this.to.getPosition().getKey()){
                    rotationAngle = 2*Math.PI - rotationAngle;
                }
            }
            this.triangle.setRotate(rotationAngle*57.2957795);
        }

        void highlight(){
            this.triangle.setScaleX(2);
            this.triangle.setScaleY(2);
            this.l.setStrokeWidth(3);
        }

        void unhighlight(){
            this.triangle.setScaleX(1);
            this.triangle.setScaleY(1);
            this.l.setStrokeWidth(1);
        }

        void erase(){
            pane.getChildren().remove(this.triangle);
            pane.getChildren().remove(this.l);
            pane.getChildren().remove(this.textGraphical);
        }
    }

    @Override
    public boolean highlightVertex(TopologicalVertex topologicalVertex){
        for(SimpleGeographicalVertex vertex: this.simpleGeographicalVertices){
            if(vertex.topologicalVertex == topologicalVertex){
                vertex.highlight();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean unhighlightVertex(TopologicalVertex topologicalVertex){
        for(SimpleGeographicalVertex vertex: this.simpleGeographicalVertices){
            if(vertex.topologicalVertex == topologicalVertex){
                vertex.unhighlight();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean highlightEdge(TopologicalVertex tvFrom, TopologicalVertex tvTo, String text){
        //todo porozmysalt ako by to fungovalo pri nasobnych hrananch, co prechadzat
       for(SimpleGeographicalEdge edge: this.simpleGeographicalEdges){
           if(edge.from.topologicalVertex == tvFrom && edge.to.topologicalVertex == tvTo && edge.text.equals(text)){
               edge.highlight();
               return true;
           }
       }
        return false;
    }

    @Override
    public boolean unhighlightEdge(TopologicalVertex tvFrom, TopologicalVertex tvTo, String text){
        //todo porozmysalt ako by to fungovalo pri nasobnych hrananch, co prechadzat
        for(SimpleGeographicalEdge edge: this.simpleGeographicalEdges){
            if(edge.from.topologicalVertex == tvFrom && edge.to.topologicalVertex == tvTo && edge.text.equals(text)){
                edge.unhighlight();
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param topologicalVertex - vertex to be added
     * @return true if topologicalVertex was added; false otherwise
     */
    @Override
    public boolean addVertex(TopologicalVertex topologicalVertex) {
        if(this.vertices.contains(topologicalVertex)){
            return false;
        }
        this.vertices.add(topologicalVertex);
        if(!this.simpleGeographicalVertices.add(new SimpleGeographicalVertex(topologicalVertex, 0 , 0))){
            this.vertices.remove(topologicalVertex);
            return false;
        }
        return true;
    }

    /**
     *
     * @param vFrom
     * @param vTo
     * @param text
     * @return
     */
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
            return false; //this should never happen
        }
        return(this.simpleGeographicalEdges.add(
                new SimpleGeographicalEdge(simpleGeographicalVertexFrom, simpleGeographicalVertexTo, text)));
    }

    /**
     *
     * @param topologicalVertex
     * @param x
     * @param y
     * @return
     */
    public boolean moveVertex(TopologicalVertex topologicalVertex, double x, double y){
        for(SimpleGeographicalVertex simpleGeographicalVertex:this.simpleGeographicalVertices){
            if(simpleGeographicalVertex.topologicalVertex == topologicalVertex){
                simpleGeographicalVertex.setPosition(x,y);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove vertex from inner structures and erase it from pane/screen.
     * @param topologicalVertex vertex to be removed
     * @return false if not present, true otherwise
     */
    @Override
    public boolean removeVertex(TopologicalVertex topologicalVertex) {
        //Check whether present
        if(!this.vertices.contains(topologicalVertex)){
            return false;
        }
        //Remove edges incident with given vertex from screen and structures
        for(SimpleGeographicalEdge edge: this.simpleGeographicalEdges){
            if(edge.from.topologicalVertex == topologicalVertex || edge.to.topologicalVertex == topologicalVertex){
                edge.erase();
                this.simpleGeographicalEdges.remove(edge);
            }
        }
        //Remove given vertex from screen and structures
        for(SimpleGeographicalVertex vertex: this.simpleGeographicalVertices){
            if(vertex.topologicalVertex == topologicalVertex){
                vertex.erase();
                this.simpleGeographicalVertices.remove(vertex);
                break;
            }
        }
        this.vertices.remove(topologicalVertex);
        return true;
    }

    //todo - nastavit aby to boli iba outgoing edges, treba este aj na inych miestahch par veci zmenit
    @Override
    public boolean removeEdge(TopologicalVertex topologicalVertex, TopologicalVertex topologicalVertex2, String text) {

        //skontrolovat toto ju odstrani odkial
        //

        //todo
        //treba zistit ci sa najskor nachadza v edges
        //potom odstranit z jej koncovych vertexov danych susedov
        //nakoniec ju odstranit z SGEs, potom aj z E, a vratit ci sa to cele podarilo
        return false;
    }

    @Override
    public boolean rewriteEdgeText(TopologicalVertex tvFrom, TopologicalVertex tvTo, String originalText, String newText) {
        for(SimpleGeographicalEdge edge: this.simpleGeographicalEdges){
            if(edge.from.topologicalVertex == tvFrom && edge.to.topologicalVertex == tvTo && edge.text.equals(originalText)){
                edge.text = newText;
                edge.textGraphical.setText(newText);
                edge.redraw();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean rewriteVertexValue(TopologicalVertex topologicalVertex, int value) {
        for(SimpleGeographicalVertex vertex : this.simpleGeographicalVertices){
            if(vertex.topologicalVertex == topologicalVertex){
                vertex.setValue(value);
                //todo - porozmyslat ci je toto spravne, ci to nerobit mimo funkcie ten kto to vola
                topologicalVertex.setValue(value);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean uniteVertices(TopologicalVertex tv1, TopologicalVertex tv2) {
        if(!this.vertices.contains(tv1) || !this.vertices.contains(tv2)){
            return false;
        }
        SimpleGeographicalVertex sgv1, sgv2;
        for(SimpleGeographicalVertex vertex:this.simpleGeographicalVertices){
            if(vertex.topologicalVertex == tv1){
                //todo
            }
        }
        return false;
    }

    @Override
    public void beautify(){
        //todo - vlastn eto urobit aby to fungovalo
        for(int i = 0;i<this.beautifyNumber;i++){
            for(SimpleGeographicalVertex sgv: this.simpleGeographicalVertices){
                double negX = 0, negY = 0, posX = 0, posY = 0, neighbours = 1;
                for(SimpleGeographicalVertex other: this.simpleGeographicalVertices){
                    //todo -zistit ich vzdialenost, zobrat nejaku 1/x funckiu, a tak vypocitat aky bude dany vektor
                    //inak to robi velke blbosti
                    negX += other.getPosition().getKey() - sgv.getPosition().getKey();
                    negY += other.getPosition().getValue() - sgv.getPosition().getValue();
                }
                for(SimpleGeographicalEdge sge:this.simpleGeographicalEdges){
                    if(sge.from == sgv){
                        posX += sge.to.getPosition().getKey() - sgv.getPosition().getKey();
                        posY += sge.to.getPosition().getValue() - sgv.getPosition().getValue();
                        neighbours += 1;
                    }
                }
                negX /= this.simpleGeographicalVertices.size();
                posX /= neighbours;
                negY /= this.simpleGeographicalVertices.size();
                posY /= neighbours;
                sgv.setPosition(sgv.getPosition().getKey() + posX - negX, sgv.getPosition().getValue() + posY - negY);
            }
        }
        //tak aby bolo vidno kazdy vrchol, nech su medzi nimi nejake vzdialensoti
        //a tiez aby bolo vidiet label edgeov
    }

    @Override
    public HashSet<TopologicalVertex> getGraph() {
        return this.vertices;
    }

}
