package com.example.demo2.algorithmDisplays;

import javafx.scene.layout.*;

import java.util.ArrayList;

public class AlgorithmDisplay {

    public enum DisplayType{
        Matrix, OrientedMarkedGraph, Text
    }

    private final HBox hbox;
    private final ArrayList<Display> displays;

    public AlgorithmDisplay(HBox hbox){
        this.hbox = hbox;
        this.displays = new ArrayList<>();
    }

    public void resize(){
        double usableWidth = this.hbox.getWidth();
        double allRations = 0;
        for(Display display:this.displays){
            if(display.isMinimized()){
                usableWidth -= display.getWidth();//todo - skotrolovat, ci to nebude dobre urobit inak
            }
            else{
                allRations += display.getRatio();
            }
        }
        for(Display display:this.displays){
            if(!display.isMinimized()){
                display.setPreferredWidth(usableWidth*(display.getRatio()/allRations));
            }
        }
    }


    protected class Resizer{
        protected void resize(){
            AlgorithmDisplay.this.resize();
        }
    }

    public Display addDisplay(DisplayType displayType, String name, int sizeRatio){
        VBox vBox = new VBox();
        switch (displayType) {
            case Matrix -> {
                this.hbox.getChildren().add(vBox);
                this.displays.add(new MatrixDisplay(vBox, name, sizeRatio, new Resizer()));
                resize();
                return this.displays.get(this.displays.size() - 1);
            }
            case OrientedMarkedGraph -> {
                this.hbox.getChildren().add(vBox);
                resize();
                //todo
                return null;
            }
            case Text -> {
                this.hbox.getChildren().add(vBox);
                this.displays.add(new TextDisplay(vBox, name, sizeRatio, new Resizer()));
                resize();
                return this.displays.get(this.displays.size() - 1);
            }
        }
        return null;
    }
}
