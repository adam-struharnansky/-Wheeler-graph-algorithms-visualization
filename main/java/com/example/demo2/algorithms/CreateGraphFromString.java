package com.example.demo2.algorithms;

import com.example.demo2.geographicalGraph.TopologicalVertex;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class CreateGraphFromString implements MatrixAlgorithm, GraphAlgorithm{

    private final BWTMatrixGraphical bwtMatrixGraphical;
    private final CreateGraphFromStringGraphical createGraphFromStringGraphical;

    public CreateGraphFromString(Pane graph_pane, Pane matrix_pane, String text){
        graph_pane.getChildren().clear();
        matrix_pane.getChildren().clear();
        BWT bwt = new BWT(text);
        this.bwtMatrixGraphical = new BWTMatrixGraphical(matrix_pane, bwt, text.length());
        this.createGraphFromStringGraphical = new CreateGraphFromStringGraphical(graph_pane, bwt, text + '$');
    }

    @Override
    public boolean hasNext() {
        return this.bwtMatrixGraphical.hasNext();
    }

    @Override
    public String nextStep() {
        this.bwtMatrixGraphical.nextStep();
        //chceme nejako grafu povedat, ze on tiez potrebuje vediet, ktoru hranu ide menit
        //teda on si chce prejst vsetky hrany, a potom sa rozhodne, ze tato je ta, ktora je spravna
        //on si z textu ale tiez urobi bwt
        //a tiez si to usporiada, a iba bude robit v tom istom case to iste
        //je este moznost, ze  by vracali miesto, ktore je zasiahnute, aby sa vytvorila sipka medzi danymi vecami

        //this.createGraphFromStringGraphical.nextStep();
        return null;
    }

    @Override
    public ArrayList<TopologicalVertex> getGraph() {
        return this.createGraphFromStringGraphical.getGraph();
    }

    @Override
    public ArrayList<String> getMatrix() {
        return this.bwtMatrixGraphical.getMatrix();
    }
}
