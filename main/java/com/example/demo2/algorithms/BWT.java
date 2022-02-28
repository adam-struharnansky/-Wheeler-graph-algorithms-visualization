package com.example.demo2.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.lang.StringBuilder;

public class BWT {

    private final String content;
    private final ArrayList<String> rotations;
    private final ArrayList<String> sortedRotations;

    public BWT(String content){
        this.content = content + '$';
        rotations = new ArrayList<>();
        sortedRotations = new ArrayList<>();
        fillRotations();
        sortRotations();
    }

    private void fillRotations(){
        StringBuilder start = new StringBuilder(this.content);
        StringBuilder end = new StringBuilder();
        for(int i = 0; i<this.content.length(); i++){
            StringBuilder rotation = new StringBuilder();
            rotation.append(start).append(end);
            end.append(start.charAt(0));
            start.deleteCharAt(0);
            this.rotations.add(rotation.toString());
            this.sortedRotations.add(rotation.toString());
        }
    }

    public ArrayList<String> getRotations(){
        return this.rotations;
    }

    private void sortRotations(){
        this.sortedRotations.sort(Comparator.naturalOrder());
    }

    public ArrayList<String> getSortedRotations(){
        return this.sortedRotations;
    }

    public String getLastColumn(){
        StringBuilder result = new StringBuilder();
        for (int i = 0;i<this.content.length();i++){
            result.append(this.sortedRotations.get(i).charAt(this.content.length() - 1));
        }
        return result.toString();
    }

}
