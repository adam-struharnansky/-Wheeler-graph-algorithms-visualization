package com.example.demo2.animations;

import javafx.util.Pair;

import java.util.HashMap;

public class Animation {

    //myslienky na vylepsenie: pridat mozno funkciu, ktorou sa bude dat pridat aj nieco do stredu, aby to urobilo

    //vec a finalny priestor kde to chceme dostat
    private final HashMap<MoveAnimatable, Pair<Double, Double>> moveAnimatables;

    private AnimationThread animationThread;

    //neviem ci je toto az taky dobry napad to davat, smrdi mi to
    public interface EndFunction{
        void executeEndFunction();
    }

    private EndFunction endFunction;

    public Animation(){
        this.moveAnimatables = new HashMap<>();
        this.animationThread = null;
    }

    public void addAnimatable(MoveAnimatable animatable, Pair<Double, Double> endPosition){
        this.moveAnimatables.put(animatable, endPosition);
    }

    public void addAnimatable(MoveAnimatable animatable, double endX, double endY){
        addAnimatable(animatable, new Pair<>(endX, endY));
    }


    public void animate(){
        animate(() -> {});
    }

    public void animate(int iterationNumber){
        animate(iterationNumber, () -> {});
    }

    public void animate(EndFunction endFunction){
        animate(50, endFunction);//todo - zistit, ktore cislo vyzera byt najlepsie
    }

    public void animate(int iterationNumber, EndFunction endFunction){
        iterationNumber = Math.max(1, iterationNumber);
        iterationNumber = Math.min(100, iterationNumber);//todo, upravit, aby to bolo este logicke
        this.endFunction = endFunction;
        this.animationThread = new AnimationThread(iterationNumber);
        this.animationThread.start();
    }

    class AnimationThread extends Thread{

        private final int iterationNumber;
        private boolean alive;

        AnimationThread(int iterationNumber){
            super();
            this.iterationNumber = iterationNumber;
            this.alive = true;
        }

        @Override
        public void run(){

            HashMap<MoveAnimatable, Pair<Double, Double>> moveAnimatablesStep = new HashMap<>();

            moveAnimatables.forEach((moveAnimatable, positionTo) -> moveAnimatablesStep.put(moveAnimatable, new Pair<>(
                    (positionTo.getKey() - moveAnimatable.getPosition().getKey()) / iterationNumber,
                    (positionTo.getValue() - moveAnimatable.getPosition().getValue()) / iterationNumber)));


            for(int i = 0; i<iterationNumber;i++){
                //todo - kontrola, ci to este funguje
                if(!this.alive){//todo, skontrolovat, ci to funguje pri tom, ze robim s vlaknami, a ci to nepadne
                    break;
                }

                //posun o prislusny krok
                moveAnimatablesStep.forEach((moveAnimatable, change) ->
                        moveAnimatable.setPosition(moveAnimatable.getPosition().getKey() + change.getKey(),
                        moveAnimatable.getPosition().getValue() + change.getValue()));
                try
                {
                    Thread.sleep(16);//todo nastavit na nieco intelignetne
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
            }
            endFunction.executeEndFunction();
        }
    }

    public void endAnimation(){
        //todo - ukoncit to pre kazdu vec
        this.moveAnimatables.forEach((animatable, position) ->
                animatable.setPosition(position.getKey(), position.getValue()));
        if(this.animationThread != null){
            this.animationThread.alive = false;
        }
        this.moveAnimatables.clear();
    }

}
