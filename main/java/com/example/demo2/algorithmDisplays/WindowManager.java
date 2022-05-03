package com.example.demo2.algorithmDisplays;

import com.example.demo2.algorithmDisplays.animatableNodes.DisplayType;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class WindowManager {

    private static double width = 0.0;
    private static double height = 0.0;

    private static HBox displaysHBox = null;
    private static final ArrayList<Display> displays = new ArrayList<>();

    private static Pane controllerPane = null;
    private final static int maxColumn = 10;
    private final static int maxRow = 4;
    private final static double controllerPanePadding = 12.0;
    private final static double controllerHeight = 150.0;
    private final static HashMap<Node, Pair<Integer, Integer>> nodePositionMap = new HashMap<>();
    private final static Node[][] positionNodeMap = new Node[maxRow][maxColumn];

    public static void changeWidth(double width){
        WindowManager.width = width;
        redrawDisplays();
        redrawControllers();
    }

    public static void changeHeight(double height){
        displaysHBox.setMaxHeight(height - controllerHeight);
        controllerPane.setMinHeight(controllerHeight);
        controllerPane.setPrefHeight(controllerHeight);
        WindowManager.height = height;
        redrawDisplays();
        //controllery by nemali zavisiet od vysky, tie ju maju fixnu
    }

    public static void redrawDisplays(){
        double usableWidth = (WindowManager.width == 0.0)? WindowManager.displaysHBox.getWidth(): WindowManager.width;
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
                display.setSize((usableWidth/allRations)*display.getRatio(), displaysHBox.getHeight());
                display.resize();
            }
            else{
                display.setSize(20.0, displaysHBox.getHeight());
            }
        }
    }

    public static Display addDisplay(DisplayType displayType, String name, int sizeRatio){
        if(WindowManager.displaysHBox == null){
            return null;
        }
        VBox vBox = new VBox();
        switch (displayType) {
            case Matrix -> {
                displaysHBox.getChildren().add(vBox);
                displays.add(new MatrixDisplay(vBox, name, sizeRatio));
                redrawDisplays();
                return displays.get(displays.size() - 1);
            }
            case Text -> {
                displaysHBox.getChildren().add(vBox);
                displays.add(new TextDisplay(vBox, name, sizeRatio));
                redrawDisplays();
                return displays.get(displays.size() - 1);
            }
            case DirectedGraph-> {
                displaysHBox.getChildren().add(vBox);
                GraphDisplay graphDisplay = new GraphDisplay(vBox, name, sizeRatio, true);
                displays.add(graphDisplay);
                redrawDisplays();
                return graphDisplay;
            }
            case UndirectedGraph -> {
                displaysHBox.getChildren().add(vBox);
                GraphDisplay graphDisplay = new GraphDisplay(vBox, name, sizeRatio, false);
                displays.add(graphDisplay);
                redrawDisplays();
                return graphDisplay;
            }
            case Code -> {
                displaysHBox.getChildren().add(vBox);
                CodeDisplay codeDisplay= new CodeDisplay(vBox, name, sizeRatio);
                displays.add(codeDisplay);
                redrawDisplays();
                return codeDisplay;
            }
            case Selector -> {
                displaysHBox.getChildren().add(vBox);
                SelectorDisplay selectorDisplay = new SelectorDisplay(vBox, name, sizeRatio);
                displays.add(selectorDisplay);
                redrawDisplays();
                return selectorDisplay;
            }
            case Tree -> {
                displaysHBox.getChildren().add(vBox);
                TreeDisplay treeDisplay = new TreeDisplay(vBox, name, sizeRatio);
                displays.add(treeDisplay);
                redrawDisplays();
                return treeDisplay;
            }
        }
        return null;
    }

    public static void setHBox(HBox hbox){
        if(WindowManager.displaysHBox == null) {
            WindowManager.displaysHBox = hbox;
        }
    }

    public static void setControllerPane(Pane pane){
        if(WindowManager.controllerPane == null){
            WindowManager.controllerPane = pane;
        }
    }

    private static void redrawControllers(){
        double y = controllerPanePadding;//toto bude o hornom okraji
        for(int i = 0;i<maxRow;i++){
            ArrayList<Node> row = new ArrayList<>();
            double contentWidth = 0.0;
            double maxHeight = -1.0;
            for(int j = 0;j<maxColumn;j++){
                if(positionNodeMap[i][j] != null){
                    row.add(positionNodeMap[i][j]);
                    positionNodeMap[i][j].autosize();
                    contentWidth += positionNodeMap[i][j].getLayoutBounds().getWidth();
                    maxHeight = Math.max(maxHeight, positionNodeMap[i][j].getLayoutBounds().getHeight());
                }
            }
            contentWidth += (row.size() - 1)*controllerPanePadding;
            double x;
            if(width != 0.0){
                x = (width / 2) - (contentWidth / 2);
            }
            else {
                x = (controllerPane.getWidth() / 2) - (contentWidth / 2);
            }
            y += maxHeight/2;
            for(Node node:row){
                node.setLayoutY(y);
                node.setLayoutX(x);
                x += node.getLayoutBounds().getWidth() + controllerPanePadding;
            }
            y += maxHeight;
        }
    }

    public static void addController(Node node, int row, int column){
        if(0 <= column && column < maxColumn && 0 <= row  && row < maxRow){
            if(positionNodeMap[row][column] == null && !nodePositionMap.containsKey(node)){
                positionNodeMap[row][column] = node;
                nodePositionMap.put(node, new Pair<>(row, column));
                controllerPane.getChildren().add(node);
                redrawControllers();
                node.layoutBoundsProperty().addListener((observableValue, bounds, t1) -> redrawControllers());
            }
        }
    }

    public static void removeController(Node node){
        if(nodePositionMap.containsKey(node)){
            positionNodeMap[nodePositionMap.get(node).getKey()][nodePositionMap.get(node).getValue()] = null;
            nodePositionMap.remove(node);
            controllerPane.getChildren().remove(node);
            node.layoutBoundsProperty().removeListener((observableValue, bounds, t1) -> redrawControllers());
            //todo - to vyssie to asi nespravi, bude dobre si ich ulozit mimo, a potom to odstranit postupne
            /*
            node.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
                @Override
                public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds t1) {

                }
            });
             */
        }
    }

    public static void clearWindow(){
        displays.clear();
        displaysHBox.getChildren().clear();
        nodePositionMap.clear();
        for(int i = 0;i<maxRow;i++){
            for (int j = 0;j<maxColumn;j++){
                if(positionNodeMap[i][j] != null){
                    controllerPane.getChildren().remove(positionNodeMap[i][j]);
                    positionNodeMap[i][j] = null;
                }
            }
        }
    }



    //todo - skontrolovat
    public static void disableDisplaysControls(){
        displays.forEach((Display::disableButtons));
    }

    public static void enableDisplaysControls(){
        displays.forEach((Display::enableButtons));
    }
}
