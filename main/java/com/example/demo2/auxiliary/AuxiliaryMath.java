package com.example.demo2.auxiliary;

import javafx.util.Pair;

public final class AuxiliaryMath {

    public static double vectorSize(Pair<Double, Double> f, Pair<Double,Double> s){
        return Math.sqrt((f.getKey() - s.getKey())*(f.getKey() - s.getKey())
                + (f.getValue() - s.getValue())*(f.getValue() - s.getValue()));
    }

    public static double vectorSize(double fX, double fY, double sX, double sY){
        return Math.sqrt((fX - sX)*(fX - sX) + (fY - sY)*(fY - sY));
    }
}