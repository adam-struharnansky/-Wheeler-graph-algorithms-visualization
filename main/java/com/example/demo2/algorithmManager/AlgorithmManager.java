package com.example.demo2.algorithmManager;

//todo - nevlozit ho ku ostanym algoritmom? kvoli importom

import com.example.demo2.algorithmDisplays.Display;
import com.example.demo2.algorithms.Algorithm;
import com.example.demo2.algorithms.StartScreen;
import com.example.demo2.algorithms.TestAlgorithm;
import com.example.demo2.algorithms.bwt.BWTDecodeAlgorithm;
import com.example.demo2.algorithms.bwt.BWTDivisionAlgorithm;
import com.example.demo2.algorithms.bwt.BWTFromSAAlgorithm;
import com.example.demo2.algorithms.bwt.BWTGeneralAlgorithm;
import com.example.demo2.algorithms.sa.SAGeneralConstructionAlgorithm;
import com.example.demo2.algorithms.wg.WGFromBWT;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class AlgorithmManager {

    //todo? toto urobit static? a potom mat moznost na zaciatku vlozit nejaky algoritmus?
    //alebo nejak ina, aby nieco bolo na zaciatocnej obraozvke

    public final Pane algorithmController;
    public final HBox algorithmDisplay;
    private Algorithm algorithm;

    public AlgorithmManager(Pane algorithmController, HBox algorithmDisplay){
        this.algorithmController = algorithmController;
        this.algorithmDisplay = algorithmDisplay;
        //todo? - nastavit to na nejaky prvotny algoritmus - uvodnu obrazovku?
        //vlozit tu aj vec na nieco mimo, kde by bol napisany ktory algoritmus sa deje?
    }

    public void changeAlgorithm(AlgorithmType algorithmType){
        this.algorithmController.getChildren().clear();
        this.algorithmDisplay.getChildren().clear();
        switch (algorithmType){
            case Test -> this.algorithm = new TestAlgorithm(this);
            case BWT -> this.algorithm = new BWTDivisionAlgorithm(this);
            case BWTDecode -> this.algorithm = new BWTDecodeAlgorithm(this);
            case BWTEncode -> this.algorithm = new BWTGeneralAlgorithm(this);
            case Start -> this.algorithm = new StartScreen(this);
            case SAIntroduction -> this.algorithm = new SAGeneralConstructionAlgorithm(this);
            case BWTFromSA -> this.algorithm = new BWTFromSAAlgorithm(this);
            case WGFromBWT -> this.algorithm = new WGFromBWT(this);
        }
    }

    //todo - toto bude asi ako array list, a kazdy algoritmus bude vediet, ako sa da zacat
    //mozno pre viacerre algoritmy tu budu viacere vstupy, a teda mozno aj si budu vkladat vlastne struktury
    public void changeAlgorithm(AlgorithmType algorithmType, Display d){
        //
    }

    public void changeAlgorithm(AlgorithmType algorithmType, String input){
        this.algorithmController.getChildren().clear();
        this.algorithmDisplay.getChildren().clear();
        switch (algorithmType){
            case BWTDecode -> this.algorithm = new BWTDecodeAlgorithm(this, input);
            case BWTFromSA -> this.algorithm = new BWTFromSAAlgorithm(this, input);
        }
    }

}
