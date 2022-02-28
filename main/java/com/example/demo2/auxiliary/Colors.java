package com.example.demo2.auxiliary;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Colors {
    private final ArrayList<Color> colors;
    private final Random random;
    public Colors(int numberOfColors){
        this.colors = new ArrayList<>();
        this.random = new Random();
        for(int i = 0;i<numberOfColors;i++){
            addColor();
        }
    }
    public void addColor(){
        //todo - aby to davalo nejake logicke a pekne odlisitelne farby - najko to vymysliet
        colors.add(Color.rgb(this.random.nextInt() % 256,
                this.random.nextInt() % 256, this.random.nextInt() %256));
    }
    public Color getColor(int i){
        if(i < 0 || i >= this.colors.size()){
            return null;
        }
        return this.colors.get(i);
    }
}
