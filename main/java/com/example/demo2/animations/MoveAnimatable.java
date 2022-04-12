package com.example.demo2.animations;

import javafx.util.Pair;

public interface MoveAnimatable {
    void setPosition(double x, double y);
    Pair<Double, Double> getPosition();
}
