package com.example.demo2.auxiliary;

import com.example.demo2.algorithmDisplays.animatableNodes.DirectedVertex;

import java.util.ArrayList;

public class WheelerGraphExamples {

    private static final ArrayList<ArrayList<DirectedVertex>> graphs = new ArrayList<>();
    static {
        //todo Add example graphs
    }

    public static ArrayList<DirectedVertex> vertices(int graphNumber){
        if(0 > graphNumber || graphNumber >= graphs.size()){
            return null;
        }
        return graphs.get(graphNumber);
    }
}
