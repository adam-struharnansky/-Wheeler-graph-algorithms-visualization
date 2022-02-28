package com.example.demo2.algorithmController;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class AlgorithmController {

    public interface Invoker{
        void invoke();
    }

    private HBox container;
    //nieco, kde si ulozime co vsekto to ma obsahovat
    public AlgorithmController(HBox container){//todo, asi zmenit to, ze ej to HBOX za nieco lepsie, asi mriezku
        //inicializacia
        this.container = container;
    }

    public void add(String type, Invoker invoker){
        //mozno bude lepsie na to vytvorit dalsie triedy, aby sa na to dalo odkaozvat
        if(type.equals("NextStep")){
            //treba pridat tlacidlo, ale asi aj funkciu, ktoru to ma vykonavat
            Button button = new Button("Next Step");//todo treba to zabezpecit pomocuo slovnika
            button.setOnAction(actionEvent -> invoker.invoke());
            this.container.getChildren().add(button);
        }
    }
    //chceme tu nieco vlozit, pricom bude potrebne poslat mu nieco, co bude toto volat
    //nieco ako tam
}
