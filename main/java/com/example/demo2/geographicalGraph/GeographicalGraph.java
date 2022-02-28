package com.example.demo2.geographicalGraph;

import java.util.ArrayList;
import java.util.HashSet;

public interface GeographicalGraph {
    boolean addVertex(TopologicalVertex topologicalVertex);
    boolean addEdge(TopologicalVertex tvFrom, TopologicalVertex tvTo, String text);
    boolean removeVertex(TopologicalVertex topologicalVertex);
    boolean removeEdge(TopologicalVertex tvFrom, TopologicalVertex tvTo, String text);
    boolean rewriteEdgeText(TopologicalVertex tvFrom, TopologicalVertex tvTo, String originalText, String newText);
    boolean rewriteVertexValue(TopologicalVertex topologicalVertex, int value);
    boolean uniteVertices(TopologicalVertex tvFrom, TopologicalVertex tvTo);
    boolean highlightVertex(TopologicalVertex topologicalVertex);
    boolean unhighlightVertex(TopologicalVertex topologicalVertex);
    boolean highlightEdge(TopologicalVertex tvFrom, TopologicalVertex tvTo, String text);
    boolean unhighlightEdge(TopologicalVertex tvFrom, TopologicalVertex tvTo, String text);
    void beautify();
    //todo - premenovat toto, asi na get vertices, je to lepsie, aby v tom nebol zmatok
    HashSet<TopologicalVertex> getGraph();
    //toto si vyberie
    //kazdy algoritmus, skontroluje ci su tam sprvne veci, a potom
    //pojde robit podla toho

    //mozno nie je potrebne si vytvarat vlastny graf, budeme mat dve veci - chcem si naklikat valstny graf
    //chcem zmenit tento graf ktory nam vytvoril dany algoritmus - potom vieme vsetko indexovat
    //podla hodnot a nazvov

    //ako zabezpecit, aby dokazal algoritmus pridat nejku hranu
    //alebo aby vedel robit aj ine veci
    //treba, aby sme vedeli vrchol indexovat aj inak .
    //dostane algoritmus gg
    //vie, ze moze pridat vrchol
    //potom mame jeden algoritmus, ktory bude robit s vrcholmi
    //bez cisel, kde toto vopred skontrolujeme, a vsetky dalsie
    //budu vediet indexovat pomocou hodnot
}
