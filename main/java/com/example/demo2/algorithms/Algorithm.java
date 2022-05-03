package com.example.demo2.algorithms;

import com.example.demo2.algorithmDisplays.WindowManager;
import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.multilingualism.LanguageListenerAdder;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class Algorithm {

    protected final Button nextStepButton = new Button();
    protected final Button backStepButton = new Button();
    protected final CheckBox animateCheckBox = new CheckBox();

    public Algorithm(AlgorithmManager algorithmManager){
        this.animateCheckBox.setSelected(true);
        LanguageListenerAdder.addLanguageListener("animate", this.animateCheckBox);

        this.nextStepButton.setOnAction(actionEvent -> nextStep(this.animateCheckBox.isSelected()));
        LanguageListenerAdder.addLanguageListener("nextStep", this.nextStepButton);

        this.backStepButton.setOnAction(actionEvent -> backStep(this.animateCheckBox.isSelected()));
        LanguageListenerAdder.addLanguageListener("backStep", this.backStepButton);
    }

    public void addNextBackAnimateControls(int rowNext, int columnNext, int rowBack, int columnBack, int rowAnimate, int columnAnimate){
        WindowManager.removeController(this.nextStepButton);
        WindowManager.removeController(this.backStepButton);
        WindowManager.removeController(this.animateCheckBox);
        WindowManager.addController(this.nextStepButton, rowNext, columnNext);
        WindowManager.addController(this.backStepButton, rowBack, columnBack);
        WindowManager.addController(this.animateCheckBox, rowAnimate, columnAnimate);

    }

    protected void nextStep(boolean animate){}

    protected void backStep(boolean animate){}

    //todo - pridat tu aj veci na zmenu algoritmu, to co je v konkretnych algorimoch v end
}
