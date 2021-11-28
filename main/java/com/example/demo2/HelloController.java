package com.example.demo2;

import com.example.demo2.geographicalGraph.GeographicalGraph;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;

import com.example.demo2.algorithms.*;

import java.util.ArrayList;

public class HelloController {

    @FXML
    private Label main_label;

    @FXML
    private  Pane graph_pane;

    @FXML
    private Pane matrix_pane;

    @FXML
    private TextField textToGraph;

    private Algorithm algorithm = null;
    private GeographicalGraph geographicalGraph;
    private ArrayList<String> bwtMatrix;

    @FXML
    protected void onNextStepClick(){
        if(algorithm == null){
            //todo - respektive by mal byt dany button neaktivny/nestacitelny, ak je algoritmus == null
            return;
        }
        if(algorithm.hasNext()){
            main_label.setText(algorithm.nextStep());
        }
        else{
            //ukoncenie algoritmu
            main_label.setText("End of algorithm");
            algorithm = null;
            //todo
            //nastavit to tak, ze sa da hybat s vrcholmi grafu
            //nie, ostane to tak, ze sa nebude robit nic
            //treba si asi vypitat od algoritmu ako vyzera graf,
            //a nejak to vymysliet
            //alebo zobrat si to od niekho ineho, ako to maju urobene oni
        }
    }

    @FXML
    protected void onGraphPaneClick(){
        //todo iba ak nie je spusteny algoritmus
        if(this.algorithm == null) {
            graph_pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                        //geographicalGraph.addVertex(mouseEvent.getX(), mouseEvent.getY(), 1);
                    }
                }
            });
        }
    }

    @FXML
    protected void onGraphFromStringClick(){
        this.algorithm = new CreateGraphFromString(this.graph_pane, this.matrix_pane, this.textToGraph.getText());
        this.textToGraph.setText("");
    }
    //chceme niečo, kde sa dá zadať slovo
    //a toto vytvorí graf, podla textu na zaciatku

    //pridat buttony na vlozenie grafu
    //asi to urboit zatial pre hociktory graf, potom to pojde aj pre button
    //teraz bude ten graf niekde ulozeny
    //nech je to ulozene pomocou nejakej triedy
    //asi bude ulozeny ako list hran
    //potom budeme chciet, aby sa nejak vypocitali suradnice
    //to by mohlo byt celkom lahke
    //chceme tlacidla, ktore zmenia typ -> zatial bez animacie
    //treba nejaky canvas, na ktory sa to cele nahodi
    //3 veci - nejaky nahodny graf
    // - kruhovy - tneto pojde ako prvy, treba tu nejak dobre ukazat oznacenie hran
    // - dvojity - tiez na zaciatku
    // a nejak to otestovat
}