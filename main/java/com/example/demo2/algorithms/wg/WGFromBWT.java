package com.example.demo2.algorithms.wg;

import com.example.demo2.algorithmDisplays.*;
import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.algorithms.Algorithm;

import java.util.ArrayList;

public class WGFromBWT extends Algorithm {

    private OrientedGraphDisplay graphDisplay;
    private MatrixDisplay matrixDisplay;
    private TextDisplay textDisplay;

    private String input;
    private ArrayList<String> rotations;
    private ArrayList<DirectedVertex> vertices;
    private ArrayList<DirectedEdge> edges;

    public WGFromBWT(AlgorithmManager algorithmManager) {
        super(algorithmManager);
        //todo pridat vlozenie textu
        start("abrakadabra");
    }

    public WGFromBWT(AlgorithmManager algorithmManager, String input){
        super(algorithmManager);
        start(input);
    }

    private void start(String input){
        this.graphDisplay = (OrientedGraphDisplay) super.addDisplay(DisplayManager.DisplayType.DirectedGraph, "", 1);
        this.matrixDisplay = (MatrixDisplay) super.addDisplay(DisplayManager.DisplayType.Matrix, "", 1);
        this.textDisplay = (TextDisplay) super.addDisplay(DisplayManager.DisplayType.Text, "", 1);

        this.input = input;
        this.rotations = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();


        StringBuilder start = new StringBuilder(this.input);
        StringBuilder end = new StringBuilder();
        for(int i = 0; i<this.input.length(); i++){
            this.rotations.add(String.valueOf(start) + end);
            for(int j = 0;j<this.input.length();j++){
                this.matrixDisplay.setSquareText(i + 1, j, this.rotations.get(i).charAt(j));
            }
            end.append(start.charAt(0));
            start.deleteCharAt(0);
        }

        this.matrixDisplay.setMatrixSize(this.input.length() +1, this.input.length());

        /*
        zobrazit tu maticu?
        alebo iba vypisat vsety veci, ktore o tom vieme?
         */
        for(int i = 0; i<this.input.length();i++){
            DirectedVertex vertex = this.graphDisplay.addVertex();
            vertex.setRelativePosition(0.4*Math.cos((2*Math.PI/this.input.length())*i) + 0.5,
                    0.4*Math.sin((2*Math.PI/this.input.length())*i) + 0.5);
            vertex.setValue(i);
            this.vertices.add(vertex);
        }

        for(int i = 0; i<this.input.length();i++){
            DirectedEdge edge = this.graphDisplay.addEdge(i==0?(this.vertices.get(this.input.length() - 1)):
                            this.vertices.get(i-1), this.vertices.get(i));
            edge.setText("a");
        }

        /*
        0 - 0
        il - 2pi
         */
        /*
        chceme urobit to, ze sa urobi podla poctu vrcholy
        ako tieto budu usporiadane?

        bud ideme ich davat relativne, alebo absolutne


         */

        /*
        chceme niekde dat vrcholy, asi najlepsie to bude dvoma sposobmi

        asi predsta bude len ten relativny sposob lepsi
        vkladat veci od 0-1, a potom to iba skalovat, to sa iba nasobi

         */
    }
}
