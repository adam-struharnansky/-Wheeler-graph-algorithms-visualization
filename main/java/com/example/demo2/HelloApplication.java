package com.example.demo2;

import com.example.demo2.algorithmDisplays.DisplayManager;
import com.example.demo2.multilingualism.LanguageListenerAdder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private void addingObjectWithTextToLanguageListener(VBox vBox){
        //todo - urobit to skor ako trochu zlozitejsie fifo, toto moze ist iba do hlbky 1
        MenuBar menuBar = (MenuBar) vBox.getChildren().get(0);
        for(Menu menu:menuBar.getMenus()){
            LanguageListenerAdder.addLanguageListener(menu.getText(), menu);
            for(MenuItem menuItem: menu.getItems()){
                LanguageListenerAdder.addLanguageListener(menuItem.getText(), menuItem);
            }
        }
        ((HBox) vBox.getChildren().get(1)).setFillHeight(true);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        VBox vBox = fxmlLoader.load();
        addingObjectWithTextToLanguageListener(vBox);
        Scene scene = new Scene(vBox, 1000, 500);

        DisplayManager.setHBox((HBox) vBox.getChildren().get(1));

        scene.heightProperty().addListener((observableValue, number, t1) -> DisplayManager.changeHeight(t1.doubleValue()));
        scene.widthProperty().addListener((observableValue, number, t1) -> DisplayManager.changeWidth(t1.doubleValue()));

        stage.setTitle("Wheeler graphs");//todo zmenit - podla toho, aky nazov to bude mat nakoniec
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
