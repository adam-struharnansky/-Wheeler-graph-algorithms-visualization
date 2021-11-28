package com.example.demo2.algorithms;

import com.example.demo2.geographicalGraph.TopologicalVertex;

import java.util.ArrayList;

public interface GraphAlgorithm extends Algorithm{
    ArrayList<TopologicalVertex> getGraph();
}


