package com.example.demo2.auxiliary;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Colors {

    public final static Color highlightingColor = Color.color(1.0,1.0,0.0,0.3);

    private static final ArrayList<Color> colors = new ArrayList<>();
    private static final Random random = new Random();

    static {
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        //todo - Add some more distinct colors
    }


    public static Color getColor(int colorNumber){
        if(colorNumber < 0 ){
            return null;
        }
        while(colors.size() <= colorNumber){
            colors.add(new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1.0));
        }
        return colors.get(colorNumber);
    }
}
