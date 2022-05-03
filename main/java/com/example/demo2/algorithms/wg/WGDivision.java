package com.example.demo2.algorithms.wg;

import com.example.demo2.algorithmDisplays.WindowManager;
import com.example.demo2.algorithmDisplays.SelectorDisplay;
import com.example.demo2.algorithmDisplays.animatableNodes.DisplayType;
import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.algorithmManager.AlgorithmType;
import com.example.demo2.algorithms.Algorithm;

public class WGDivision extends Algorithm {
    public WGDivision(AlgorithmManager algorithmManager) {
        super(algorithmManager);

        SelectorDisplay selectorDisplay1 = (SelectorDisplay)
                WindowManager.addDisplay(DisplayType.Selector, "", 1);
        selectorDisplay1.setChoice("WGFromBWT", "WGFromBWTDescription", AlgorithmType.WGFromBWT, algorithmManager );

        SelectorDisplay selectorDisplay2 = (SelectorDisplay)
                WindowManager.addDisplay(DisplayType.Selector, "", 1);
        selectorDisplay2.setChoice("createGraph", "createWGGraphDescription", AlgorithmType.WGCreation, algorithmManager);

        SelectorDisplay selectorDisplay3 = (SelectorDisplay)
                WindowManager.addDisplay(DisplayType.Selector, "", 1);
        selectorDisplay3.setChoice("tunneling", "tunnelingDescription", AlgorithmType.WGTunneling, algorithmManager);

        SelectorDisplay selectorDisplay4 = (SelectorDisplay)
                WindowManager.addDisplay(DisplayType.Selector, "", 1);
        selectorDisplay4.setChoice("WGSearch", "WGSearchDescription", AlgorithmType.WGSearch, algorithmManager);
    }
}
