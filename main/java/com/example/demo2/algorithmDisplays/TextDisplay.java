package com.example.demo2.algorithmDisplays;

import com.example.demo2.multilingualism.LanguageListenerAdder;
import com.example.demo2.multilingualism.Languages;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

public class TextDisplay extends Display{

    private final Pane pane;
    private final TextFlow textFlow;

    private class TextPart{
        String key;
        Text text;
        String style;//asi nie je potrebne, aj tak je to stale ulozene v texte todo - skontrolovat
        boolean translatable;
        TextPart(String key, Text text, String style, boolean translatable){
            this.key = key;
            this.text = text;
            this.style = style;
            this.translatable = translatable;
        }
    }
    private final ArrayList<TextPart> textParts;

    public TextDisplay(VBox container, String name, int ratio) {
        super(container, name, ratio);

        this.pane = super.getPane();

        this.textFlow = new TextFlow();
        this.pane.getChildren().add(this.textFlow);
        this.textParts = new ArrayList<>();

        LanguageListenerAdder.addLanguageListener(this);
    }

    @Override
    public void centre(){

        int lineLength = 50;//todo - dynamicka zmena
        int currentLineLength = 0;

        //todo, aby to nerobilo novy riadok hocikde, ale iba na medzere
        for(TextPart textPart:this.textParts){
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0;i<textPart.text.getText().length();i++){
                if(textPart.text.getText().charAt(i) != '\n'){
                    stringBuilder.append(textPart.text.getText().charAt(i));
                    currentLineLength++;
                }
                if(currentLineLength == lineLength){
                    stringBuilder.append('\n');
                    currentLineLength = 0;
                }
            }
            textPart.text.setText(stringBuilder.toString());
        }
    }

    public void addString(String key, String style, boolean translatable){
        Text text = new Text();
        if(translatable) {
            text.setText(Languages.getString(key));
        }
        else{
            text.setText(key);
        }
        text.setStyle(style);
        this.textFlow.getChildren().add(text);
        this.textParts.add(new TextPart(key, text, style, translatable));
        centre();
    }

    public void clear(){
        this.textFlow.getChildren().clear();
        this.textParts.clear();
    }

    public void changeLanguage(){
        this.textFlow.getChildren().clear();
        for(int i = 0; i < this.textParts.size(); i++){
            Text text = new Text();
            if(this.textParts.get(i).translatable){
                text.setText(Languages.getString(this.textParts.get(i).key));
            }
            else{
                text.setText(this.textParts.get(i).key);
            }
            text.setStyle(this.textParts.get(i).style);
            this.textFlow.getChildren().add(text);
        }
        centre();//kvoli tomuto centre to musi byt takto
    }

    //todo - pridat veci tak, aby sa mohli niektore casti textu zmenit (farba, zvyraznenie, a pod)


}
