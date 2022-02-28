package com.example.demo2.algorithmDisplays;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TextDisplay extends Display{

    private final Pane pane;
    private final Label label;

    public TextDisplay(VBox container, String name, int ratio, AlgorithmDisplay.Resizer resizer) {
        super(container, name, ratio, resizer);
        this.pane = super.getPane();

        this.label = new Label();
        this.pane.getChildren().add(label);
    }

    @Override
    public void centre(){
        //todo
    }

    public void setText(String text){
        this.label.setText(text);
        //todo - asi to bude dobre trochu upratat
    }

    //todo - pridat veci tak, aby sa mohli niektore casti textu zmenit (farba, zvyraznenie, a pod)


}
