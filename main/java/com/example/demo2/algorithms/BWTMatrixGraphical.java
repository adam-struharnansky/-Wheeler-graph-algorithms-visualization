package com.example.demo2.algorithms;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class BWTMatrixGraphical implements MatrixAlgorithm{

    private int it;
    private int textSize;
    private BWT bwt;
    private Pane pane;

    private final int drewMatrixSize = 20;

    //treba si niekde uchovavat veci, kde je to vypisane
    private ArrayList<Label> labels;
    public BWTMatrixGraphical(Pane pane, BWT bwt, int textSize){
        this.it = -1;
        this.textSize = textSize;
        this.labels = new ArrayList<>();
        this.pane = pane;
        this.bwt = bwt;

        for(int i = 0;i<Math.min(this.textSize + 1, drewMatrixSize);i++){
            Label l = new Label("");
            labels.add(l);
            //todo - zmneit to tak, aby bolo namiesto 15 velkost skutocneho textu + nejaka konstanta
            l.setLayoutY(i*15);
            pane.getChildren().add(l);
        }
        drawMatrix(pane,this.bwt.getRotations(),-1);
    }

    private void drawMatrix(Pane p, ArrayList<String> matrix, int focussedLine){
        //zobrazuje sa cela matica
        if(matrix.size() <= drewMatrixSize){
            for(int i = 0; i<matrix.size();i++){
                this.labels.get(i).setText(i+" "+matrix.get(i));
                this.labels.get(i).setStyle("-fx-font-weight: regular");
            }
            if(focussedLine >= 0){
                this.labels.get(focussedLine).setStyle("-fx-font-weight: bold");
            }
        }
        else{
            //todo - iba ak by sme chceli povolit velmi velke velkosti
            //zobrazuje sa iba cast matice
            //chceme prvy, posledny, a drewMatrixSize - 2 okolo daneho
            //to si asi urobime este predtym
            //vytvorme si boolean maticu, kde nastavime prve posledne ako true
            //potom pojdeme a zoberieme okolie fL, a nastavime ho ako true
            //alebo toto mozno aj priamo s novou
            //aebo priamto lables na prvoma poslednommieste dame na to, co to ma byt
            //znamena to ale, ze niekde musia byt kladne aj zaporne mieste
        }
    }

    @Override
    public boolean hasNext() {
        return it < textSize + 1;
    }

    @Override
    public String nextStep() {
        drawMatrix(this.pane,this.bwt.getSortedRotations(),this.it);
        this.it++;
        return null;
    }

    @Override
    public ArrayList<String> getMatrix() {
        return bwt.getSortedRotations();
    }

    //todo - dostat odtialto jeden riadok, aby sa vedelo urcit, ktora hrana je prisudena tomuto riadku
}


//vytvorit si stvorec, vo ktorom budu napisane slova
//potom treba urobit tak, aby pri velmi dlhom slove
//sa graf vopchal do viacerych riadkov
//vytvorit si niekde na kraji maticu so vsetkymi rotaciami
//tato matica ma problem, ze ak bude velmi velka, tak ju nedokazeme celu zapisat
//vytvorit si funckiu, ktora zabezpeci, ze bude vypisana prave dolezita cast matice
//zvysok budu ...
//a tiez aby bola tvaru takeho, aby sme vedeli napisat aj nejaky stredny riadok
//v ktorom bude, ze stredna cast sa bude tiez vediet zobrazit, medzi podkami
        /*
        0 text
        1
        2
        3
        5
        ...
        8
        9
        10
        11
        12

        potom sa vymaze prvy riadok, nahradi sa druhym, atd

        1 text
        2
        atd
        kym nebude matica posdnych par veci
        dlzka textu zlava-doprava, tam bude 40 znakov, ak bude slovo dlhsie, tak
        to zmeni na slovo 20 znakov ... 20 poslednych znakov
        tiez mozno podla toho, aka je velkost obrazovky
        urcite to nebude na mobily

         */