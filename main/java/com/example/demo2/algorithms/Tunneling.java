package com.example.demo2.algorithms;

import com.example.demo2.geographicalGraph.GeographicalGraph;

import java.util.ArrayList;

public class Tunneling implements MatrixAlgorithm, GraphAlgorithm{

    private boolean first;
    public Tunneling(GeographicalGraph geographicalGraph){
        //na tomto grafe treba najst dane veci
        this.first = true;
    }

    @Override
    public boolean hasNext() {
        return this.first;
    }

    @Override
    public String nextStep() {
        //treba najst nieco co sa da stunelovat
        //staci kontrolovat iba tie, ktore su za sebou
        //treba ich ale prechadzat tak
        //zatial iba jednoduche tunelovanie, bez stromov
        //berieme iba dvojice
        //do tychto dvojic idu iba hrany s rovnakymi znakmi
        //pocet vstupnych hran do vrcholov ktore chceme zobrat je 1 (kazdy dany vrchol ma 1 vstupnu hranu v t)
        //pre kazdy vrchol bloku plati, ze hrany smerujuce z neho maju kazde ine pismeno, ak smeruju
        //do bloku, ak smeruju inde potom nas to nazujima


        return null;
    }

    @Override
    public GeographicalGraph getGeographicalGraph() {
        return null;
    }

    @Override
    public ArrayList<String> getMatrix() {
        return null;
    }
}
