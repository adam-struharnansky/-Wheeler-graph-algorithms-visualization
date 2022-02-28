package com.example.demo2.algorithms;

import com.example.demo2.algorithmController.AlgorithmController;

public class Algorithm {

    class InvokerNextStep implements AlgorithmController.Invoker{
        @Override
        public void invoke() {
            if(hasNext()) {
                nextStep();
            }
        }
    }

    //mozno na to, aby zadal nejaky text
    //neviem ci toto robim spravne, ale vyzera to byt logicke

    public Algorithm(){

    }

    public boolean hasNext(){
        System.out.println("in alg");
        return false;
    }

    public void nextStep(){

    }
}
