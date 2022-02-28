package com.example.demo2;

import com.example.demo2.algorithms.BWT;
import com.example.demo2.algorithms.CreateGraphFromString;
import com.example.demo2.algorithms.CreateGraphFromStringGraphical;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //VBox vb = (VBox) fxmlLoader.load();
        //HBox hb = (HBox) vb.getChildrenUnmodifiable().get(0);
        //Pane p = (Pane) hb.getChildrenUnmodifiable().get(1);
        //todo - toto odstranit, chceme aby kazdy algorimus bud dostal graf uz z obrazovky
        //ktory skontoluje, alebo ak bude chciet uzivat menit veci na grafe, tak bude musiet klikat
        //na nejkay button
        //po skonceni algoritmu vrati tento GeografickyGraf, s ktroym uz uzivatel bude moct posuvat veci
        //HelloController.geographicalGraph = new SimpleGeographicalGraph_1(p);

        //je potrebne tu dat minimalnu velkost okna, alebo nieco podobne
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Wheeler graphs");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //test();
        launch();
    }

    //for testing some simple classes/functions
    private static void test(){
        System.out.println("test");

        System.out.println("end of test");
    }
}
