package com.example.demo2.algorithmDisplays;

import javafx.scene.layout.*;

import java.util.ArrayList;

public class DisplayManager {

    public enum DisplayType{
        Matrix, Text, DirectedGraph, UndirectedGraph, Code, Selector, Tree,
    }

    private static double width = 0.0;
    private static HBox hbox = null;
    private static final ArrayList<Display> displays = new ArrayList<>();

    public static void changeWidth(double width){
        DisplayManager.width = width;
        resize();
    }

    public static void changeHeight(double height){
        //todo - porozmyslat, ako to vymysliet, kedze tu ide aj o zmenu controllera a menu
    }


    public static void resize(){
        double usableWidth = (DisplayManager.width == 0.0)?DisplayManager.hbox.getWidth():DisplayManager.width;
        double allRations = 0.0;
        for(Display display: displays){
            if(display.isMinimized()){
                usableWidth -= 20.0;
            }
            else{
                allRations += display.getRatio();
            }
        }
        for(Display display: displays){
            if(!display.isMinimized()){
                display.setSize((usableWidth/allRations)*display.getRatio(), hbox.getHeight());
                display.resize();
            }
            else{
                display.setSize(20.0, hbox.getHeight());
            }
        }
    }

    public static Display addDisplay(DisplayType displayType, String name, int sizeRatio){
        if(DisplayManager.hbox == null){
            return null;
        }
        VBox vBox = new VBox();
        switch (displayType) {
            case Matrix -> {
                hbox.getChildren().add(vBox);
                displays.add(new MatrixDisplay(vBox, name, sizeRatio));
                resize();
                return displays.get(displays.size() - 1);
            }
            case Text -> {
                hbox.getChildren().add(vBox);
                displays.add(new TextDisplay(vBox, name, sizeRatio));
                resize();
                return displays.get(displays.size() - 1);
            }
            case DirectedGraph-> {
                hbox.getChildren().add(vBox);
                OrientedGraphDisplay graphDisplay = new OrientedGraphDisplay(vBox, name, sizeRatio);
                graphDisplay.setFactories(new DirectedVertexFactory(), new DirectedEdgeFactory());
                displays.add(graphDisplay);
                resize();
                return graphDisplay;
            }
            case UndirectedGraph -> {
                hbox.getChildren().add(vBox);
                GraphDisplay graphDisplay = new GraphDisplay(vBox, name, sizeRatio);
                graphDisplay.setFactories(new VertexFactory(), new EdgeFactory());
                displays.add(graphDisplay);
                resize();
                return graphDisplay;
            }
            case Code -> {
                hbox.getChildren().add(vBox);
                CodeDisplay codeDisplay= new CodeDisplay(vBox, name, sizeRatio);
                displays.add(codeDisplay);
                resize();
                return codeDisplay;
            }
            case Selector -> {
                hbox.getChildren().add(vBox);
                SelectorDisplay selectorDisplay = new SelectorDisplay(vBox, name, sizeRatio);
                displays.add(selectorDisplay);
                resize();
                return selectorDisplay;
            }
            case Tree -> {
                hbox.getChildren().add(vBox);
                TreeDisplay treeDisplay = new TreeDisplay(vBox, name, sizeRatio);
                displays.add(treeDisplay);
                resize();
                return treeDisplay;
            }
        }
        return null;
    }

    public static void setHBox(HBox hbox){
        if(DisplayManager.hbox == null) {
            DisplayManager.hbox = hbox;
        }
    }

    public static void clearDisplays(){
        displays.clear();
    }

    //todo - skontrolovat
    public static void disableDisplaysControls(){
        displays.forEach((Display::disableButtons));
    }

    public static void enableDisplaysControls(){
        displays.forEach((Display::enableButtons));
    }
}
