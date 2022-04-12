package com.example.demo2.algorithms;

import com.example.demo2.algorithmDisplays.*;
import com.example.demo2.algorithmDisplays.Display;
import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.animations.Animation;
import com.example.demo2.animations.MoveAnimatable;
import com.example.demo2.multilingualism.LanguageListenerAdder;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Algorithm {

    private final GridPane gridPane;
    private final Animation animation;

    private Button nextStepButton;
    private final ArrayList<Labeled> controllers;

    public Algorithm(AlgorithmManager algorithmManager){
        DisplayManager.clearDisplays();
        this.gridPane = new GridPane();
        algorithmManager.algorithmController.getChildren().add(this.gridPane);
        this.controllers = new ArrayList<>();
        this.animation = new Animation();
    }

    //todo - zmenit toto, aby to mohol byt next step pre viacero veci
    protected void addNextStepButton(int column, int row){
        if(nextStepButton != null){
            return;//todo - toto asi inak
        }
        this.nextStepButton = new Button();
        LanguageListenerAdder.addLanguageListener("nextStep", this.nextStepButton);
        this.nextStepButton.setOnAction(actionEvent -> nextStep());
        this.gridPane.add(this.nextStepButton, column, row);
    }

    protected void removeNextStepButton(){
        this.gridPane.getChildren().remove(this.nextStepButton);
    }

    public void nextStep(){}

    protected void addController(Labeled labeled, int column, int row){
        this.gridPane.add(labeled, column, row);
        this.controllers.add(labeled);
        centreController();
    }

    protected void addController(TextField textField, int column, int row){
        this.gridPane.add(textField, column, row);
        centreController();
    }

    protected void removeController(Node node){
        this.gridPane.getChildren().remove(node);
    }

    private void centreController(){
        //todo - vycentrovat vsetky veci v controleri, aby to bolo co na stred
        this.gridPane.setAlignment(Pos.CENTER);//toto nefunguje tak, ako chcem
        this.gridPane.setHgap(30.0);
        this.gridPane.setVgap(30.0);
        //alebo, vytvorit si to vlastne.
        //mozme si
    }

    protected Display addDisplay(DisplayManager.DisplayType type, String name, int sizeRatio){
        return DisplayManager.addDisplay(type, name, sizeRatio);
    }

    protected void disableAllControls(){
        disableDisplaysControls();
        disableControllersControls();
    }

    public void enableAllControls(){
        enableDisplaysControls();
        enableControllersControls();
    }

    protected void disableDisplaysControls(){
        DisplayManager.disableDisplaysControls();
    }

    protected void enableDisplaysControls(){
        DisplayManager.enableDisplaysControls();
    }

    protected void disableControllersControls(){
        this.controllers.forEach((labeled -> labeled.setDisable(true)));
    }

    protected void enableControllersControls(){
        this.controllers.forEach((labeled -> labeled.setDisable(false)));
    }


    //su tu dva druhy kontroliek: tie ktore su na vrchu displaya, a tie ktore su v kontroleri
    //oboje sa bude robit inym sposobom?

    //ak tu vlozime aj algdisplay, potom vieme vsetko dis/enabled pomocou jednej funckie
    //to sa asi vyplati

    //todo

    //chceme tu button na ukoncenie, ktory da iba prazdnu stranku
    //ako urobit toto? To treba vliezt na dany priestor, a dat algorithm = null
    //tiez by bolo fajn ho ukoncit, ale nechat tam dane veci
    //len to bude asi kazdy algoritmus chciet robit sam
    //po skoncenie moze vytvorit, ze cim chce uzivatel pokracovat

    //nebude lepsie mat tu ulozene veci, tykajuce sa controlera?
    //potom sa aj bude lahsie tu vkladat veci

    //a mozem to urbit pomocou protected veci
    //a iba si budu moct vypytat veci odtialto
    //respektive, moze to byt urobene bez toho, aby konkretny algoritmus vedel
    //nieco s tym robit.
    //vieme to tu vladat, ako aj v resizery
    //toto moze byt nieco podobne
    //a v tejto triede sa bude pracovat s tym, ako sa to aj usporiada
    //ked sa to niekedy bude chciet usporiadavat

    //bolo by fajn to mat centrovane - grid pane to dva na kraje
    //vymysliet nieco, aby to davalo celkom pekne, a hlavne na stred

    //mozno aj menit pridavaciu funkciu - podla toho ako sa tu budu pridavat veci



    //problem je, ako tu vkladat vsetky mozne veci
    //button
    //label/text - nieco na vypis textu
    //check box
    //choice box
    //radio button
    //toggle button

/*
vymysliet, ako to urobit, aby mohol zastavit vec, ktora prave ide. To asi nie je dobry napad
 */

/*
vlozit vsetky animacne veci tu.
 */

    /*
    urob novu animaciu - stara sa hned dokonci, ak nebola null
    pridaj do animacie nieco
    spusti animaciu
    na zaciatku zakazat robit nieco zo vstupnymi buttonmi, ked tam dohodime vsetko, tak v tomto sa povoli dalsi krok
    //treba vymysliet, ako to urobit pri viacerych dalsich krokoch, kde by sme chceli preskocit nejaku vec


     */

    protected void renewAnimation(){
        this.animation.endAnimation();
        //asi tu zakazat robit veci s animaciami
    }

    protected void addAnimatable(MoveAnimatable animatable, double x, double y){
        this.animation.addAnimatable(animatable, x, y);
    }

    protected void startAnimation(){
        //todo schovat vsetky veci, ktore uz nemaju byt menitelne
        //todo - hned bude dobre, aby mohol vnutorny nechat niektore buttony aby mohli byt stale stacitelne
        //mozno to nie je velmi treba, ked hned v prvom cykle dostaneme kontrolu naspat
        disableAllControls();
        this.animation.animate(this::enableAllControls);
    }


}
