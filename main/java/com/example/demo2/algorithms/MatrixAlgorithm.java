package com.example.demo2.algorithms;

import java.util.ArrayList;

public interface MatrixAlgorithm extends AlgorithmInterface {
    //todo - porozmyslat, ci by to nemalo byt getFinalMatrix, kedze skor by to uzivatel nemal mat mozne pouzivat
    //podobne aj pri graphAlgorithm
    ArrayList<String> getMatrix();
}
