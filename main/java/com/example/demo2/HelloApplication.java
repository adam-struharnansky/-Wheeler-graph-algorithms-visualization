package com.example.demo2;

import com.example.demo2.algorithmDisplays.WindowManager;
import com.example.demo2.auxiliary.Algorithms;
import com.example.demo2.auxiliary.Auxiliary;
import com.example.demo2.multilingualism.LanguageListenerAdder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class HelloApplication extends Application {

    private static final double startingHeight = 750;
    private static final double startingWidth = 1000;

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
        Scene scene = new Scene(vBox, startingWidth, startingHeight);

        stage.setTitle("Wheeler graphs");//todo zmenit - podla toho, aky nazov to bude mat nakoniec
        stage.setScene(scene);
        stage.show();

        WindowManager.setHBox((HBox) vBox.getChildren().get(1));
        WindowManager.setControllerPane((Pane) vBox.getChildren().get(2));

        BackgroundFill backgroundFill =
                new BackgroundFill(
                        Color.LIGHTGRAY,
                        new CornerRadii(5),
                        new Insets(5)
                );

        Background background =
                new Background(backgroundFill);

        ((Pane) vBox.getChildren().get(2)).setBackground(background);

        scene.heightProperty().addListener((observableValue, number, t1) -> WindowManager.changeHeight(t1.doubleValue()));
        scene.widthProperty().addListener((observableValue, number, t1) -> WindowManager.changeWidth(t1.doubleValue()));

        WindowManager.changeHeight(startingHeight);
        WindowManager.changeWidth(startingWidth);
    }

    public static void main(String[] args) {
        //test();
        launch();
    }

    static void test(){
        System.out.println(Arrays.toString(Algorithms.suffixArray("banana")));
        System.out.println();
        System.out.println(Arrays.toString(Algorithms.suffixArray("abracadabra")));
        System.out.println();
        System.out.println(Arrays.toString(Algorithms.suffixArray("abrakadabra")));
        System.out.println();
        System.out.println(Arrays.toString(Algorithms.suffixArray("abcdefgh")));
        System.out.println();
        System.out.println(Arrays.toString(Algorithms.suffixArray("hgfedcba")));
        System.out.println();
    }
}
