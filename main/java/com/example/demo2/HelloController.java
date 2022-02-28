package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

import com.example.demo2.algorithms.*;

public class HelloController {

    @FXML
    private HBox algorithmController;

    @FXML
    private HBox algorithmDisplay;

    private Algorithm algorithm = null;

    @FXML
    protected void changeLanguageToSlovak(){
        //todo
    }

    @FXML
    protected void changeLanguageToEnglish(){
        //todo
    }

    private void clear(){
        this.algorithmController.getChildren().clear();
        this.algorithmDisplay.getChildren().clear();
    }

    @FXML
    protected void setAlgorithmToTest(){
        clear();
        this.algorithm = new TestAlgorithm(this.algorithmController, this.algorithmDisplay);
    }

    @FXML
    protected void setAlgorithmToBWT(){
        clear();

    }

    @FXML
    protected void fxmlTest(){
        System.out.println("fxmlTest");
    }
}