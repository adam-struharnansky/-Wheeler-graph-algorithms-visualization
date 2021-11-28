package com.example.demo2.geographicalGraph;

import javafx.util.Pair;

import java.util.ArrayList;

public class TopologicalVertex {
    private int value;
    private ArrayList<Pair<String,TopologicalVertex>> incoming;
    private ArrayList<Pair<String,TopologicalVertex>> outgoing;

    public TopologicalVertex(){
        this.value = -1;
        this.incoming = new ArrayList<>();
        this.outgoing = new ArrayList<>();
    }
    public TopologicalVertex(int value){
        this.value = value;
        this.incoming = new ArrayList<>();
        this.outgoing = new ArrayList<>();
    }

    public ArrayList<Pair<String, TopologicalVertex>> getIncoming() {
        return this.incoming;
    }

    public ArrayList<Pair<String, TopologicalVertex>> getOutgoing() {
        return this.outgoing;
    }

    public boolean addOutgoingEdge(String text, TopologicalVertex otv){
        return this.outgoing.add(new Pair<>(text, otv));
    }
    public boolean addOutgoingEdge(TopologicalVertex otv){
        //todo - podobne ako s textom
        return false;
    }
    public boolean addIncomingEdge(String text, TopologicalVertex itv){
        return this.incoming.add(new Pair<>(text, itv));
    }
    public boolean addIncomingEdge(TopologicalVertex itv){
        //todo
        return false;
    }
    public boolean removeOutgoingEdge(String text, TopologicalVertex otv){
        //todo - skontorlovat ci toto funguje, asi tu bude treba nieco prepisat
        return this.outgoing.remove(new Pair<>(text, otv));
    }
    public boolean removeIncomingEdge(String text, TopologicalVertex itv){
        return this.incoming.remove(new Pair<>(text, itv));
    }
    public int getValue(){
        return value;
    }
    public void setValue(int value){
        this.value = value;
    }
    public boolean hasIncomingEdge(String text, TopologicalVertex itv){
        return this.incoming.contains(new Pair<>(text, itv));
    }
    public boolean hasOutgoingEdge(String text, TopologicalVertex otv){
        return this.outgoing.contains(new Pair<>(text,otv));
    }

}
