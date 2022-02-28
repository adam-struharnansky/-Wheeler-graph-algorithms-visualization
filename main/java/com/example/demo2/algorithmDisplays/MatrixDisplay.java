package com.example.demo2.algorithmDisplays;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MatrixDisplay extends Display {

    private final Pane pane;
    private final ArrayList<ArrayList<String>> matrix;
    private final ArrayList<ArrayList<Label>> labelMatrix;
    private int numberOfColumns = 0, numberOfRows = 0;

    private class Arrow {
        private Arrow(int sr, int sc, int er, int ec){
            //todo
            //ulozit si to niekde
            //pridat realne v pane
        }

        private void cover(){
            //todo pre minimize
        }

        private void uncover(){
            //todo pre maximize
        }
    }


    public MatrixDisplay(VBox container, String name, int ratio, AlgorithmDisplay.Resizer resizer) {
        super(container, name, ratio, resizer);
        this.pane = super.getPane();

        this.matrix = new ArrayList<>();
        this.labelMatrix = new ArrayList<>();
    }

    @Override
    public void centre(){
        //todo
        //to znamena ze chceme najst centrum, a nan to vycetrovat
        System.out.println("md centre");
    }

    public void setMatrixSize(int numberOfColumns, int numberOfRows){
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        //todo
        //vymazat to co tam bolo doteraz na obrazovke
        //vymazat obsah struktur
        //vytvorit nove
        //tieto pridat
    }

    public void test(){
    }

    public boolean highlightSquare(int columnNumber, int rowNumber){
        if(columnNumber < 0 || columnNumber >= this.numberOfColumns || rowNumber < 0 || rowNumber >= this.numberOfRows){
            return false;
        }
        //todo
        //zvyraznit dany stvorcek
        return true;
    }

    public boolean highlightRow(int rowNumber){
        return true;//todo
    }

    public boolean highlightColumn(int columnNumber){
        return true;//todo
    }

    public boolean setSquareColor(int rowNumber, int columnNumber){
        return true;//todo
    }

    public boolean setSquareText(int rowNumber, int columnNumber, String text){
        return true;
    }

    //niektore funkcie tu by mali byt také, že ich zmena je ina. Teda, vytvorenie niecoho
    //by mohlo byt animovatelne. Treba pozriet, ako sa to robi este pred tym
    //nez sa cele toto naprogramuje

    //nebude najednoduchsie iba odstranit pane z hboxu? potom by tam nemal byt zobrazeny,
    //ale vsetky veci by sa snim mali robit nadalej jednoducho


}
