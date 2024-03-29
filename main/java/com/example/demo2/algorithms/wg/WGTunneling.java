package com.example.demo2.algorithms.wg;

import com.example.demo2.Step.StepManager;
import com.example.demo2.algorithmDisplays.*;
import com.example.demo2.algorithmDisplays.animatableNodes.*;
import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.algorithmManager.AlgorithmType;
import com.example.demo2.algorithms.Algorithm;
import com.example.demo2.animations.Animation;
import com.example.demo2.animations.AnimationManager;
import com.example.demo2.animations.AnimationType;
import com.example.demo2.auxiliary.Algorithms;
import com.example.demo2.auxiliary.Colors;
import com.example.demo2.auxiliary.WheelerGraphExamples;
import com.example.demo2.multilingualism.LanguageListenerAdder;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.*;

public class WGTunneling extends Algorithm {

    private final AlgorithmManager algorithmManager;
    private final AnimationManager animationManager = new AnimationManager();
    private final StepManager stepManager = new StepManager();

    private final GraphDisplay graphDisplay = (GraphDisplay) WindowManager.addDisplay(DisplayType.DirectedGraph, "", 2);
    private final MatrixDisplay matrixDisplay = (MatrixDisplay) WindowManager.addDisplay(DisplayType.Matrix, "", 1);
    private final CodeDisplay codeDisplay = (CodeDisplay) WindowManager.addDisplay(DisplayType.Code, "", 1);
    private final TextDisplay textDisplay = (TextDisplay) WindowManager.addDisplay(DisplayType.Text, "", 1);

    //todo - na zaciatku oznacit, ktore veci v matici sa idu spajat, a preco
    //todo - pridat to, aby sa vrcholy, ktore sa maju spajat zvyraznili,
    //todo - pridat texty
    //todo - pridat prikladne grafy

    private final static int tunnelingRow = 0;
    private final static int exampleRow = 1;
    private final static int changeRow = 2;

    private final static int iColumn = 0;
    private final static int lColumn = 1;
    private final static int outColumn = 2;
    private final static int inColumn = 3;
    private final static int fColumn = 4;

    private final Button startButton = new Button();
    private final CheckBox automaticBeautifyCheckBox = new CheckBox();
    private final ArrayList<RadioButton> exampleRadioButtons = new ArrayList<>();
    private final Button setExampleGraph = new Button();
    private final Button inverseGraphButton = new Button();
    private final Button transformGraphButton = new Button();

    private final ArrayList<DirectedVertex> vertices = new ArrayList<>();
    private final ArrayList<DirectedEdge> edges = new ArrayList<>();

    private String input;

    public WGTunneling(AlgorithmManager algorithmManager) {
        super(algorithmManager);
        this.algorithmManager = algorithmManager;
        addButtons();
    }

    public WGTunneling(AlgorithmManager algorithmManager, ArrayList<DirectedVertex> vertices){
        super(algorithmManager);
        this.algorithmManager = algorithmManager;
        addButtons();
        setGraph(vertices);
        setInputFromVertices();
        setCode();
    }

    private void setInputFromVertices(){
        String vertexId = "0";
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0;i<this.vertices.size();i++){
            for(DirectedEdge edge:this.edges){
                if(edge.getVertexTo().getValue().compareTo(vertexId) == 0){
                    stringBuilder.append(edge.getText());
                    vertexId = edge.getVertexFrom().getValue();
                    break;
                }
            }
        }
        this.input = stringBuilder.toString();
        this.input = this.input.replaceAll("\\$", "");
        this.input = this.input + "$";
        System.out.println(input);
    }

    private void setGraph(ArrayList<DirectedVertex> vertices){
        //todo - odstranit vsetko, co je tu teraz
        this.vertices.forEach(graphDisplay::removeVertex);
        this.vertices.clear();
        this.edges.clear();

        for(DirectedVertex oldVertex:vertices){
            DirectedVertex vertex = (DirectedVertex) this.graphDisplay.addVertex();
            vertex.setValue(oldVertex.getValue());
            vertex.setRelativePosition(oldVertex.getRelativePosition());
            this.vertices.add(vertex);
        }

        for(DirectedVertex oldVertex:vertices){
            for(Edge edge:oldVertex.getOutgoing()){
                DirectedVertex from = null, to = null;
                for(DirectedVertex vertex:this.vertices){
                    if(vertex.getValue().compareTo(edge.getVertexFrom().getValue()) == 0){
                        from = vertex;
                    }
                    if(vertex.getValue().compareTo(edge.getVertexTo().getValue()) == 0){
                        to = vertex;
                    }
                }
                if(from != null && to != null){//this should be always true
                    DirectedEdge edgeNew = (DirectedEdge) graphDisplay.addEdge(from, to);
                    edgeNew.setText(edge.getText());
                    this.edges.add(edgeNew);
                }
            }
        }
    }

    private void setCode(){
        this.codeDisplay.addLine("h := end - beg + 1");
        this.codeDisplay.addLine("x := beg");
        this.codeDisplay.addLine("for(k := 0; k<w-1;k++)");
        this.codeDisplay.addLine("    in[x + 1] = 0");
        this.codeDisplay.addLine("    x = lf[x]");
        this.codeDisplay.addLine("    out[x + 1] = 0");
        this.codeDisplay.addLine("i := 0");
        this.codeDisplay.addLine("j := 0");
        this.codeDisplay.addLine("for(k := 0; k<n;k++)");
        this.codeDisplay.addLine("    if(in[k] = 1)");
        this.codeDisplay.addLine("        out[i] = out[k]");
        this.codeDisplay.addLine("        l[i] = l[k]");
        this.codeDisplay.addLine("        i = i + 1");
        this.codeDisplay.addLine("    if(out[k] = 1)");
        this.codeDisplay.addLine("        in[i] = in[k]");
        this.codeDisplay.addLine("        j = j + 1");
        this.codeDisplay.addLine("l = l[0, i - 1]");
        this.codeDisplay.addLine("out = out[0, i]");
        this.codeDisplay.addLine("out[i] = 1");
        this.codeDisplay.addLine("in = in[0, i]");
        this.codeDisplay.addLine("in[i] = 1");
    }

    private void addButtons(){
        this.startButton.setOnAction(actionEvent -> start());
        LanguageListenerAdder.addLanguageListener("tunnelGraph", this.startButton);
        WindowManager.addController(this.startButton, tunnelingRow,0);

        this.automaticBeautifyCheckBox.setSelected(true);
        LanguageListenerAdder.addLanguageListener("automaticBeautify", this.automaticBeautifyCheckBox);
        WindowManager.addController(this.automaticBeautifyCheckBox, tunnelingRow, 1);

        ToggleGroup examples = new ToggleGroup();

        for(int i = 0;i<5;i++) {
            RadioButton radioButton = new RadioButton();
            radioButton.setToggleGroup(examples);
            LanguageListenerAdder.addLanguageListener("example"+i, radioButton);
            WindowManager.addController(radioButton, exampleRow, i);
            this.exampleRadioButtons.add(radioButton);
        }

        this.setExampleGraph.setOnAction(actionEvent -> {
            for(int i = 0;i<this.exampleRadioButtons.size();i++){
                if(examples.getSelectedToggle() == this.exampleRadioButtons.get(i)){
                    setGraph(Objects.requireNonNull(WheelerGraphExamples.vertices(i)));
                }
            }
        });
        LanguageListenerAdder.addLanguageListener("setExampleGraph", this.setExampleGraph);
        WindowManager.addController(this.setExampleGraph, exampleRow, 5);
    }

    private void start(){
        this.input = (input.endsWith("$"))? input:input+"$";//?
        WindowManager.removeController(this.startButton);
        WindowManager.removeController(this.automaticBeautifyCheckBox);
        if(this.automaticBeautifyCheckBox.isSelected()){
            this.graphDisplay.setBeautifyDisable(true);
            this.graphDisplay.setCenterDisable(true);
        }
        this.exampleRadioButtons.forEach(WindowManager::removeController);
        WindowManager.removeController(this.setExampleGraph);

        this.matrixDisplay.setMatrixSize(input.length() + 2, 5);
        this.matrixDisplay.setRowText(0, "i", "L[i]", "out[i]", "in[i]", "F[i]");
        this.matrixDisplay.setSquareTextColor(0, fColumn, Color.GRAY);
        //Set variables for given graph
        this.bwt = Algorithms.bwt(input);
        this.l = this.bwt.toCharArray();
        lf = Algorithms.lfMapping(bwt);
        inV = new int[input.length() + 1];
        outV = new int[input.length() + 1];
        for(int i = 0;i<inV.length;i++){
            inV[i] = 1;
            outV[i] =  1;
        }
        f = input.toCharArray();
        Arrays.sort(f);
        for(int i = 0;i<this.input.length();i++){
            if(!this.alphabet.contains(this.input.charAt(i))){
                this.alphabet.add(this.input.charAt(i));
            }
        }
        this.alphabet.sort(Comparator.naturalOrder());
        recolorEdges();

        for(int i = 0;i<this.input.length() + 1;i++){
            this.matrixDisplay.setSquareText(i + 1, inColumn, 1);
            this.matrixDisplay.setSquareText(i + 1, outColumn, 1);
            this.matrixDisplay.setSquareText(i + 1, iColumn, i);
            if(i < bwt.length()){
                this.matrixDisplay.setSquareText(i + 1, lColumn, bwt.charAt(i));
            }
            if(i < f.length){
                this.matrixDisplay.setSquareTextColor(i + 1, fColumn, Color.GRAY);
                this.matrixDisplay.setSquareText(i + 1, fColumn, f[i]);
            }
        }

        //3.1 Baier - Finding all prefix intervals, and storing them in "intervals"
        int [] lfInverse = Algorithms.inverseLFMapping(bwt);
        int [] lcs = new int[input.length()];
        lcs[0] = 0;
        int j = lfInverse[0];
        int l = 0;
        for(int i = 1; i<input.length(); i++){
            if(j > 0 && bwt.charAt(j) == bwt.charAt(j - 1)){
                l++;
            }
            else{
                l = 0;
            }
            lcs[j] = l;
            j = lfInverse[j];
        }
        LinkedList<Pair<Integer, Pair<Integer, Integer>>> intervals = new LinkedList<>();
        LinkedList<Pair<Integer, Integer>> s = new LinkedList<>();
        s.push(new Pair<>(1, 0));
        for(int i = 1;i<input.length();i++){
            Pair<Integer, Integer> tmp = s.getFirst();
            while(tmp.getValue() > lcs[i]){
                s.removeFirst();
                intervals.push(new Pair<>(tmp.getValue() + 1, new Pair<>(tmp.getKey(), i - 1)));
                tmp = s.getFirst();
            }
            if(tmp.getValue() < lcs[i]){
                s.push(new Pair<>(i - 1, lcs[i]));
            }
        }
        while(s.size() > 1){
            Pair<Integer, Integer> tmp = s.getFirst();
            intervals.push(new Pair<>(tmp.getValue() + 1, new Pair<>(tmp.getKey(), input.length() - 1)));
            s.removeFirst();
        }
        Pair<Integer, Pair<Integer, Integer>> best = new Pair<>(-1, null);
        for (Pair<Integer, Pair<Integer, Integer>> interval : intervals) {
            if (interval.getKey() > best.getKey() && interval.getKey()*2 < input.length()) {
                best = interval;
            }
        }
        System.out.println(best+" "+intervals);
        System.out.println(input);

        if(best.getKey() == -1){
            //todo - napisat do textu, ze sa neda tunelovat
            System.out.println("neda sa tunelovat");
            negativeEnd();
        }
        else{
            super.addBasicControls(tunnelingRow, 0);
            super.backStepButton.setDisable(true);

            startV = best.getValue().getKey();
            endV = best.getValue().getValue();
            wV = best.getKey();
            for(int i = 0;i<25;i++){
                firstTime.add(true);
            }

            for (DirectedVertex directedVertex : this.vertices) {
                if (directedVertex.getOutgoing().get(0).getVertexTo().getValue().compareTo(startV + "") == 0) {
                    this.tunel.add(directedVertex);
                }
                if (directedVertex.getOutgoing().get(0).getVertexTo().getValue().compareTo(endV + "") == 0) {
                    this.toDisappear.add(directedVertex);
                }
            }
            for(int i = 0;i<=wV;i++) {
                for (DirectedVertex vertex : vertices) {
                    if (vertex.getIncoming().get(0).getVertexFrom().getValue().compareTo(tunel.get(tunel.size() - 1).getValue()) == 0) {
                        this.tunel.add(vertex);
                        break;
                    }
                }
            }
            for(int i = 0;i<=wV;i++) {
                for (DirectedVertex vertex : this.vertices) {
                    if (vertex.getIncoming().get(0).getVertexFrom().getValue().compareTo(toDisappear.get(toDisappear.size() - 1).getValue()) == 0) {
                        this.toDisappear.add(vertex);
                        break;
                    }
                }
            }
            for(DirectedVertex vertex:this.tunel){
                System.out.print(" " +vertex.getValue());
            }
            System.out.println();
            for(DirectedVertex vertex:this.toDisappear){
                System.out.print(" " +vertex.getValue());
            }
            System.out.println();
        }
    }

    private final ArrayList<Character> alphabet = new ArrayList<>();
    private int step = 0;
    private int highestStep = 0;
    private int endInt = -1;
    private final ArrayList<Boolean> firstTime = new ArrayList<>();

    private final ArrayList<DirectedVertex> tunel = new ArrayList<>();
    private final ArrayList<DirectedVertex> toDisappear = new ArrayList<>();
    private int tunelDisappearPointer = 1;

    int currentLineNumber = 1;
    int hV, startV, endV, xV, kV, wV, iV, jV;
    private int [] inV, outV;
    private String bwt;
    private char [] f;
    private char [] l;
    private int [] lf;

    private void recolorEdges(){
        for(Edge edge:this.edges){
            edge.setColor(Colors.getColor(this.alphabet.indexOf(edge.getText().charAt(0))));
        }
    }

    private void rewriteGraph(Animation animation, int end){
        ArrayList<Pair<Pair<Integer, Integer>, Character>> succinct = new ArrayList<>();
        ArrayList<Pair<Pair<Integer, Integer>, Character>> real = new ArrayList<>();
        int [] occ = new int[this.alphabet.size()];
        for(int i = 0;i<end;i++){
            int from = -1;
            for(int j = 0;j<=i;j++){
                from += outV[j];
            }
            char label = l[i];
            int to = -1;
            int o = 0;
            for(int j = 0;j<end;j++){
                to += inV[j];
                if(f[j] == label && o == occ[alphabet.indexOf(label)]){
                    break;
                }
                if(f[j] == label){
                    o++;
                }
            }
            succinct.add(new Pair<>(new Pair<>(from, to), label));
            occ[alphabet.indexOf(label)]++;
        }
        succinct.sort(Comparator.comparing(Pair::getValue));
        for(Edge edge:this.edges) {
            Pair<Integer, Integer> pos = new Pair<>(
                    Integer.parseInt(edge.getVertexFrom().getValue()),
                    Integer.parseInt(edge.getVertexTo().getValue())
            );
            real.add(new Pair<>(pos, edge.getText().charAt(0)));
        }
        real.sort(Comparator.comparing(Pair::getValue));
        int i = 0;
        while(i <succinct.size()){
            int j = 0;
            boolean removed = false;
            while(j < real.size()){
                if((succinct.get(i).getValue() == real.get(j).getValue())
                &&(Objects.equals(succinct.get(i).getKey().getKey(), real.get(j).getKey().getKey()))
                &&(Objects.equals(succinct.get(i).getKey().getValue(), real.get(j).getKey().getValue()))){
                    succinct.remove(i);
                    real.remove(j);
                    removed = true;
                    break;
                }
                j++;
            }
            if(!removed){
                i++;
            }
        }
        ArrayList<Edge> edgesToBeRemoved = new ArrayList<>();
        for (Pair<Pair<Integer, Integer>, Character> pairCharacterPair : real) {
            for (Edge edge : this.edges) {
                if ((Integer.parseInt(edge.getVertexFrom().getValue()) == pairCharacterPair.getKey().getKey())
                        && (Integer.parseInt(edge.getVertexTo().getValue()) == pairCharacterPair.getKey().getValue())
                        && edge.getText().charAt(0) == pairCharacterPair.getValue()) {
                    animation.addAnimatable(AnimationType.DisappearAnimation, edge);
                    edgesToBeRemoved.add(edge);
                    //Should be done via iterator, but the cycle breaks after this, so it creates no problem
                    this.edges.remove(edge);
                    break;
                }
            }
        }
        for (Pair<Pair<Integer, Integer>, Character> pairCharacterPair : succinct) {
            Vertex from = null, to = null;
            for (Vertex vertex : this.vertices) {
                if (Integer.parseInt(vertex.getValue()) == pairCharacterPair.getKey().getKey()) {
                    from = vertex;
                }
                if (Integer.parseInt(vertex.getValue()) == pairCharacterPair.getKey().getValue()) {
                    to = vertex;
                }
            }
            DirectedEdge edge = (DirectedEdge) this.graphDisplay.addEdge(from, to);
            edge.setText(pairCharacterPair.getValue());
            animation.addAnimatable(AnimationType.AppearAnimation, edge);
            this.edges.add(edge);
        }
        recolorEdges();
        if(this.automaticBeautifyCheckBox.isSelected()) {
            edgesToBeRemoved.forEach(Edge::disappear);
            this.graphDisplay.beautify(animation, 1000);
            edgesToBeRemoved.forEach(Edge::appear);//todo - skontrolovat, ci nie je chyba v tomto
        }
    }

    int firstOut, lastIn;
    @Override
    protected void nextStep(boolean animate){
        Animation animation = this.animationManager.getAnimation(step);

        if(step == highestStep){
            this.codeDisplay.unhighlightEverything(animation);
            this.matrixDisplay.unhighlightBackgrounds(animation);

            this.codeDisplay.highlightLine(animation, this.currentLineNumber);
            switch (this.currentLineNumber){
                case 1 -> {
                    hV = endV - startV + 1;
                    this.codeDisplay.addVariable(animation, "h", hV);
                    this.currentLineNumber = 2;
                }
                case 2 -> {
                    xV = startV;
                    this.codeDisplay.addVariable(animation, "x", xV);
                    this.currentLineNumber = 3;
                }
                case 3 -> {
                    if(this.firstTime.get(3)){
                        kV = -1;
                        this.codeDisplay.addVariable(animation, "k", 0);
                        this.firstTime.set(3, false);
                        this.stepManager.addSetVariableValue(step, this.codeDisplay, "k", "", (kV+1)+"");
                    }
                    else {
                        this.stepManager.addSetVariableValue(step, this.codeDisplay, "k", kV + "", (kV + 1) + "");
                    }
                    kV++;
                    if(kV == wV - 1){
                        this.currentLineNumber = 7;
                    }
                    else{
                        this.currentLineNumber = 4;
                    }
                }
                case 4 -> {
                    inV[xV + 1] = 0;
                    this.stepManager.addSetSquareText(step, this.matrixDisplay, xV + 2, inColumn, "1", "0");
                    if(this.firstTime.get(4)){
                        this.firstTime.set(4, false);
                        this.firstOut = xV + 1;
                        this.outV[xV + 1] = 0;
                        this.stepManager.addSetSquareText(step, this.matrixDisplay, xV + 2, outColumn, "1", "0");
                        this.stepManager.addSetHighlightSquare(step, this.matrixDisplay, xV + 2, outColumn, true);
                    }
                    rewriteGraph(animation, bwt.length());
                    this.currentLineNumber = 5;
                }
                case 5 -> {
                    this.stepManager.addSetVariableValue(step, this.codeDisplay, "x", xV, lf[xV]);
                    xV = lf[xV];
                    tunelDisappearPointer++;
                    this.currentLineNumber = 6;
                }
                case 6 -> {
                    outV[xV + 1] = 0;
                    lastIn = xV + 1;
                    this.stepManager.addSetSquareText(step, this.matrixDisplay, xV + 2, outColumn, "1", "0");
                    this.currentLineNumber = 3;
                }
                case 7 -> {
                    inV[lastIn] = 0;
                    stepManager.addSetSquareText(step, this.matrixDisplay, lastIn + 1, inColumn, "1", "0");
                    this.stepManager.addSetHighlightSquare(step, this.matrixDisplay, lastIn + 1, inColumn, true);
                    rewriteGraph(animation, bwt.length());
                    this.codeDisplay.addVariable(animation, "i", "0");
                    iV = 0;
                    this.currentLineNumber = 8;
                }
                case 8 -> {
                    this.codeDisplay.addVariable(animation, "j", "0");
                    jV = 0;
                    this.currentLineNumber = 9;
                }
                case 9 -> {
                    if(this.firstTime.get(9)){
                        kV = -1;
                        this.stepManager.addSetHighlightSquare(step, this.matrixDisplay, lastIn + 1, inColumn, false);
                        this.stepManager.addSetHighlightSquare(step, this.matrixDisplay, firstOut + 1, outColumn, false);
                        this.stepManager.addSetSquareText(step, this.matrixDisplay, lastIn + 1, inColumn, "0", "1");
                        this.stepManager.addSetSquareText(step, this.matrixDisplay, firstOut + 1, outColumn, "0", "1");
                        this.stepManager.addSetSquareText(step, this.matrixDisplay, 0, fColumn, "F[i]", "");
                        for(int i = 0;i<f.length;i++){
                            this.stepManager.addSetSquareText(step, this.matrixDisplay, i + 1, fColumn, f[i]+"", "");
                        }
                        inV[lastIn] = 1;
                        outV[firstOut] = 1;
                        this.codeDisplay.addVariable(animation, "k", 0);
                        this.firstTime.set(9, false);
                        this.stepManager.addSetVariableValue(step, this.codeDisplay, "k", "", (kV+1)+"");
                    }
                    else {
                        this.stepManager.addSetVariableValue(step, this.codeDisplay, "k", kV + "", (kV + 1) + "");
                    }
                    kV++;
                    if(kV == (input.length())){
                        this.currentLineNumber = 17;
                    }
                    else{
                        this.currentLineNumber = 10;
                    }
                }
                case 10 -> {
                    if(inV[kV] == 1){
                        this.currentLineNumber = 11;
                    }
                    else{
                        this.currentLineNumber = 14;
                    }
                }
                case 11 -> {
                    this.stepManager.addSetSquareText(step, this.matrixDisplay, iV + 1, outColumn, outV[iV]+"", outV[kV]+"");
                    outV[iV] = outV[kV];
                    this.currentLineNumber = 12;
                }
                case 12 -> {
                    this.stepManager.addSetSquareText(step, this.matrixDisplay, iV + 1, lColumn, bwt.charAt(iV)+"", bwt.charAt(kV)+"");
                    l[iV] = l[kV];
                    this.currentLineNumber = 13;
                }
                case 13 -> {
                    this.stepManager.addSetVariableValue(step, this.codeDisplay, "i", iV+"", (iV+1)+"");
                    iV++;
                    this.currentLineNumber = 14;
                }
                case 14 -> {
                    if(outV[kV] == 1){
                        this.currentLineNumber = 15;
                    }
                    else{
                        this.currentLineNumber = 9;
                    }
                }
                case 15 -> {
                    this.stepManager.addSetSquareText(step, this.matrixDisplay, jV + 1, inColumn, inV[jV]+"", inV[kV]+"");
                    inV[jV] = inV[kV];
                    this.currentLineNumber = 16;
                }
                case 16 -> {
                    this.stepManager.addSetVariableValue(step, this.codeDisplay, "j", jV+"", (jV+1)+"");
                    jV++;
                    this.currentLineNumber = 9;
                }
                case 17 -> {
                    for(int i = iV;i<bwt.length();i++){
                        this.stepManager.addSetSquareText(step, this.matrixDisplay, i + 1, lColumn, bwt.charAt(i)+"", "");
                    }
                    char [] tmp = new char[l.length];
                    if (iV >= 0) System.arraycopy(l, 0, tmp, 0, iV);
                    l = new char[iV];
                    System.arraycopy(tmp, 0, l, 0, iV);
                    f = new char[iV];
                    System.arraycopy(tmp, 0, f, 0, iV);
                    Arrays.sort(f);
                    System.out.println(Arrays.toString(l));
                    System.out.println(Arrays.toString(f));
                    this.currentLineNumber = 18;
                }
                case 18 -> {
                    for(int i = iV + 1; i< outV.length;i++){
                        this.stepManager.addSetSquareText(step, this.matrixDisplay, i + 1, outColumn, outV[i]+"", "");
                    }
                    int [] tmp = new int[outV.length];
                    if (iV + 1 >= 0) System.arraycopy(outV, 0, tmp, 0, iV + 1);
                    outV = new int[iV + 1];
                    System.arraycopy(tmp, 0, outV, 0, iV + 1);
                    this.currentLineNumber = 19;
                }
                case 19 -> {
                    this.stepManager.addSetSquareText(step, this.matrixDisplay, iV + 1, outColumn, outV[iV]+"", "1");
                    outV[iV] = 1;
                    this.currentLineNumber = 20;
                }
                case 20 -> {
                    for(int i = jV + 1; i< inV.length;i++){
                        this.stepManager.addSetSquareText(step, this.matrixDisplay, i + 1, inColumn, inV[i]+"", "");
                    }
                    int [] tmp = new int[inV.length];
                    if (iV + 1 >= 0) System.arraycopy(inV, 0, tmp, 0, iV + 1);
                    inV = new int[iV + 1];
                    System.arraycopy(tmp, 0, inV, 0, iV + 1);
                    this.currentLineNumber = 21;
                }
                case 21 ->{
                    this.stepManager.addSetSquareText(step, this.matrixDisplay, iV + 1, inColumn, inV[iV]+"", "1");
                    inV[iV] = 1;
                    this.currentLineNumber = 22;
                }
                case 22 -> {
                    this.endInt = step;
                    rewriteGraph(animation, l.length);
                    this.stepManager.addSetSquareText(step, this.matrixDisplay, 0, fColumn, "", "F[i]");
                    for(int i = 0;i<f.length;i++){
                        this.stepManager.addSetSquareText(step, this.matrixDisplay, i + 1, fColumn,  "", f[i]+"");
                    }
                    int realVertices = -2;
                    for (int j : inV) {
                        realVertices += j;
                    }
                    for(Vertex vertex:this.vertices){
                        if(Integer.parseInt(vertex.getValue()) > realVertices){
                            animation.addAnimatable(AnimationType.DisappearAnimation, vertex);
                        }
                    }
                }
            }
        }

        if(animate){
            animationManager.executeAnimation(step, true);
        }
        else{
            animation.setForward(true);
            animation.endAnimation();
        }
        this.stepManager.forwardStep(step);
        if(step == endInt){
            end();
        }
        step++;
        highestStep = Math.max(highestStep, step);

        super.backStepButton.setDisable(false);
    }

    @Override
    protected void backStep(boolean animate){
        if(step > 0) {
            step--;
            this.stepManager.backStep(step);
            if (animate) {
                animationManager.executeAnimation(step, false);
            } else {
                animationManager.endAnimation(step, false);
            }
        }
        if(step == 0){
            super.backStepButton.setDisable(true);
        }
        super.nextStepButton.setDisable(false);
    }

    private void end(){
        super.nextStepButton.setDisable(true);

        ArrayList<Character> l = new ArrayList<>();
        for (char c : this.l) {
            l.add(c);
        }
        ArrayList<Integer> in = new ArrayList<>();
        for (int j : this.inV) {
            in.add(j);
        }
        ArrayList<Integer> out = new ArrayList<>();
        for (int j : this.outV) {
            out.add(j);
        }

        this.inverseGraphButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.WGInverse,
                l, in, out));
        LanguageListenerAdder.addLanguageListener("inverse???", this.inverseGraphButton);
        WindowManager.addController(this.inverseGraphButton, 1, 0);

        this.transformGraphButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.WGCreation,
                this.vertices));
        LanguageListenerAdder.addLanguageListener("transformGraph??", this.transformGraphButton);
        WindowManager.addController(this.inverseGraphButton, 1, 1);
    }

    private void negativeEnd(){
        this.transformGraphButton.setOnAction(actionEvent -> this.algorithmManager.changeAlgorithm(AlgorithmType.WGCreation,
                this.vertices));
        LanguageListenerAdder.addLanguageListener("transformGraph??", this.transformGraphButton);
        WindowManager.addController(this.inverseGraphButton, 1, 0);
    }
}
