package com.example.demo2.algorithmDisplays;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Arrays;

public class CodeDisplay extends Display{

    private final TextFlow textFlow;
    private final ArrayList<CodeLine> codeLines;
    private final ArrayList<Variable> variables;

    private final ArrayList<String> specialWords = new ArrayList<>(Arrays.asList("int", "if", "for", "while", "def"));

    class CodeLine{
        TextFlow lineText;
        String lineString;

        CodeLine(String line){
            this.lineText = new TextFlow();
            //todo - oznacovat dolezite slova, napisat, ako to vyriesit
            //treba zisit, ci su tu dane slova
            //chceme to prejst, ale nesmie to byt pod ziadnym
            //treba nejako rozmyslat nad odsekmi
            //potom to treba cele presjt, a zistit, ci je tu nieco z toho
            Text text = new Text(line+'\n');
            this.lineText.getChildren().add(text);
        }

        void setColorToLinePart(int start, int end, Color color){
            //alebo to urobit, ze vsetko bude vlastny text? kazde ejdno pismeno
        }

        void setColorToLinePart(String part, Color color){
            if(this.lineString.contains(part)){
                setColorToLinePart(this.lineString.indexOf(part), this.lineString.indexOf(part) + part.length(), color);
            }
        }
    }

    class Variable{
        private String name;
        private String value;
        private Text text;
    }

    public CodeDisplay(VBox container, String name, int ratio) {
        super(container, name, ratio);
        this.textFlow = new TextFlow();
        super.getPane().getChildren().add(this.textFlow);
        this.codeLines = new ArrayList<>();
        this.variables = new ArrayList<>();
    }

    @Override
    public void centre(){
        //todo
    }

    public void addLine(String line){
        CodeLine codeLine = new CodeLine(line);
        this.codeLines.add(codeLine);
        this.textFlow.getChildren().add(codeLine.lineText);
    }

    public void setColorToCodeLinePart(int lineNumber, int start, int end, Color color){
        if(isLineIn(lineNumber)){
            this.codeLines.get(lineNumber).setColorToLinePart(start, end, color);
        }
    }

    public void setColorToCodeLinePart(int lineNumber, String part, Color color){
        if(isLineIn(lineNumber)){
            this.codeLines.get(lineNumber).setColorToLinePart(part, color);
        }
    }

    public boolean isLineIn(int lineNumber){
        return this.codeLines.size() > lineNumber && lineNumber >= 0;
    }

}


