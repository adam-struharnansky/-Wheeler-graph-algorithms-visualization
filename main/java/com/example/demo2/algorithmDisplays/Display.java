package com.example.demo2.algorithmDisplays;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Display{

    private final VBox container;
    private final ToolBar toolBar;
    private boolean isMinimized;
    private final String name;
    private final Pane pane;
    private final int ratio;
    private final AlgorithmDisplay.Resizer resizer;

    public Display(VBox container, String name, int ratio, AlgorithmDisplay.Resizer resizer){
        this.container = container;
        this.name = name;
        this.ratio = ratio;
        this.resizer = resizer;
        this.isMinimized = false;
        this.pane = new Pane();

        this.toolBar = new ToolBar();
        this.toolBar.setStyle("-fx-border-color: black");
        Label nameLabel = new Label(name);//todo, zmenit to tak, aby to bralo veci podla slovnika
        this.toolBar.getItems().add(nameLabel);
        //todo - pridat tam asi hbox tak, aby boli buttony zarovnane doprava
        Button centreButton = new Button("+");
        centreButton.setOnAction(actionEvent -> centre());
        this.toolBar.getItems().add(centreButton);
        Button changeSizeButton = new Button("*");
        changeSizeButton.setOnAction(actionEvent -> {
            if (isMinimized) {
                maximize();
            } else {
                minimize();
            }
        });
        this.toolBar.getItems().add(changeSizeButton);
        this.container.getChildren().add(this.toolBar);
        this.container.getChildren().add(this.pane);

    }

    //toto by mala kazda funkcia overridnut, kedze tu sa nechceme chytat obsahu, iba formy
    public void centre(){    }

    private void minimize(){
        if(this.isMinimized){
            return;
        }
        this.container.getChildren().remove(this.pane);
        setPreferredWidth(20);//todo - toto sa mozno zmeni
        this.isMinimized = true;
        this.resizer.resize();
    }

    private void maximize(){
        if(!this.isMinimized){
            return;
        }
        this.container.getChildren().add(this.pane);
        this.isMinimized = false;
        this.resizer.resize();
    }

    public boolean isMinimized(){
        return this.isMinimized;
    }

    public double getWidth(){
        return this.container.getWidth();
    }

    protected Pane getPane(){
        return this.pane;
    }

    protected int getRatio(){
        return this.ratio;
    }

    protected void setPreferredWidth(double width){
        this.toolBar.setPrefWidth(width); //su tieto dve potrebne? Vyskusat treba, resp najst na nete.
        this.pane.setPrefWidth(width);
        this.container.setPrefWidth(width);
    }

}
