package com.example.demo2.algorithmManager;

import com.example.demo2.algorithmDisplays.WindowManager;
import com.example.demo2.algorithmDisplays.animatableNodes.DirectedVertex;
import com.example.demo2.algorithms.Algorithm;
import com.example.demo2.algorithms.StartScreen;
import com.example.demo2.algorithms.TestAlgorithm;
import com.example.demo2.algorithms.bwt.*;
import com.example.demo2.algorithms.sa.SAGeneralConstructionAlgorithm;
import com.example.demo2.algorithms.wg.*;

import java.util.ArrayList;

public class AlgorithmManager {

    private Algorithm algorithm;

    public void changeAlgorithm(AlgorithmType algorithmType){
        WindowManager.clearWindow();
        switch (algorithmType){
            case Test -> this.algorithm = new TestAlgorithm(this);
            case BWT -> this.algorithm = new BWTDivisionAlgorithm(this);
            case BWTDecode -> this.algorithm = new BWTDecodeAlgorithm(this);
            case BWTEncode -> this.algorithm = new BWTGeneralAlgorithm(this);
            case Start -> this.algorithm = new StartScreen(this);
            case SAIntroduction -> this.algorithm = new SAGeneralConstructionAlgorithm(this);
            case BWTFromSA -> this.algorithm = new BWTFromSAAlgorithm(this);
            case WG -> this.algorithm = new WGDivision(this);
            case WGCreation -> this.algorithm = new WGCreation(this);
            case WGTunneling -> this.algorithm = new WGTunneling(this);
            case WGSearch -> this.algorithm = new WGSearch(this);
            case WGFromBWT -> this.algorithm = new WGFromBWT(this);
            case BWTSearch -> this.algorithm = new BWTSearch(this);
        }
    }

    public void changeAlgorithm(AlgorithmType algorithmType, String input){
        WindowManager.clearWindow();
        switch (algorithmType){
            case BWTDecode -> this.algorithm = new BWTDecodeAlgorithm(this, input);
            case BWTFromSA -> this.algorithm = new BWTFromSAAlgorithm(this, input);
            case BWTSearch -> this.algorithm = new BWTSearch(this, input);
        }
    }

    public void changeAlgorithm(AlgorithmType algorithmType, ArrayList<DirectedVertex> vertices){
        WindowManager.clearWindow();
        switch (algorithmType){
            case WGCreation -> this.algorithm = new WGCreation(this, vertices);
            case WGTunneling -> this.algorithm = new WGTunneling(this, vertices);
            case WGSearch -> this.algorithm = new WGSearch(this, vertices);
        }
    }
}
