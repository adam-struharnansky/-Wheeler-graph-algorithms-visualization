package com.example.demo2.geographicalGraph;

import java.util.ArrayList;

public interface GeographicalGraph {
    public boolean addVertex(TopologicalVertex topologicalVertex);
    public boolean addEdge(TopologicalVertex tv1, TopologicalVertex tv2, String text);
    public boolean removeVertex(TopologicalVertex topologicalVertex);
    public boolean removeEdge(TopologicalVertex topologicalVertex, String text);
    public boolean rewriteEdgeText(TopologicalVertex topologicalVertex, String text);
    public boolean rewriteVertexValue(TopologicalVertex topologicalVertex);
    public ArrayList<TopologicalVertex> getGraph();
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
