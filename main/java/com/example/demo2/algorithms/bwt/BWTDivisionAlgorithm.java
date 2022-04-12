package com.example.demo2.algorithms.bwt;

import com.example.demo2.algorithmDisplays.DisplayManager;
import com.example.demo2.algorithmDisplays.SelectorDisplay;
import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.algorithmManager.AlgorithmType;
import com.example.demo2.algorithms.Algorithm;

public class BWTDivisionAlgorithm extends Algorithm {

    public BWTDivisionAlgorithm(AlgorithmManager algorithmManager) {
        super(algorithmManager);

        SelectorDisplay selectorDisplay1 = (SelectorDisplay)
                super.addDisplay(DisplayManager.DisplayType.Selector, "", 1);
        selectorDisplay1.setChoice("code", "choose", AlgorithmType.BWTEncode, algorithmManager );

        SelectorDisplay selectorDisplay2 = (SelectorDisplay)
                super.addDisplay(DisplayManager.DisplayType.Selector, "", 1);
        selectorDisplay2.setChoice("decode", "choose", AlgorithmType.BWTDecode, algorithmManager);

        SelectorDisplay selectorDisplay3 = (SelectorDisplay)
                super.addDisplay(DisplayManager.DisplayType.Selector, "", 1);
        selectorDisplay3.setChoice("sa encode", "choose", AlgorithmType.BWTFromSA, algorithmManager);

        //todo - dlasei moznosti co sa da robit s BWT
    }
}
