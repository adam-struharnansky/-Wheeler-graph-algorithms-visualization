package com.example.demo2.algorithmDisplays;

import com.example.demo2.algorithmManager.AlgorithmManager;
import com.example.demo2.algorithmManager.AlgorithmType;
import com.example.demo2.multilingualism.Languages;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class SelectorDisplay extends Display{

    private final ArrayList<Text> texts;
    private final Button button;
    private String nameKey;
    private String descriptionKey;

    private final double padding = 10.0;


    public SelectorDisplay(VBox container, String name, int ratio) {
        super(container, name, ratio);
        this.texts = new ArrayList<>();
        this.button = new Button();
        super.getPane().getChildren().add(this.button);
    }

    public void setChoice(String name, String description, AlgorithmType algorithmType,
                          AlgorithmManager algorithmManager){
        this.descriptionKey = description;
        this.nameKey = name;
        this.button.setOnAction(actionEvent -> algorithmManager.changeAlgorithm(algorithmType));
        this.button.setText("->");//todo toto asi zmenit, mozno nejaky obrazok bude vhodnejsi
        centre();
    }

    @Override
    public void centre(){
        this.texts.forEach(text -> super.getPane().getChildren().remove(text));
        this.texts.clear();
        Text nameText = new Text();
        super.getPane().getChildren().add(nameText);
        nameText.setText(Languages.getString(this.nameKey));
        this.texts.add(nameText);
        String description = Languages.getString(this.descriptionKey);
         description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi" +
                " ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum " +
                "dolore eu fugiat nulla pariatur.";

        while(description.length() > 0){
            if(description.length() > 50){
                Text line = new Text();
                super.getPane().getChildren().add(line);
                //binarne vyhldavanie na najdenie mazimalneho textu, ktory sa da vlozit do jedneho riadku
                int str = 0, end = description.length();
                line.setText(description.substring(str, end));
                while(end - str > 1){
                    int mid = (str + end)/2;
                    line.setText(description.substring(0, mid));
                    line.autosize();
                    if(line.getLayoutBounds().getWidth() >= super.getWidth() - this.padding*2.0){
                        end = mid;
                    }
                    else{
                        str = mid;
                    }
                }
                //teraz najst medzeru
                boolean hasSpace = false;
                for(int i = str;i>0;i--){
                    if(description.charAt(i) == ' '){
                        line.setText(description.substring(0,i));
                        description = description.substring(i);
                        hasSpace = true;
                        break;
                    }
                }
                if(!hasSpace){
                    //pridany spojovnik na koniec riadku
                    line.setText(description.substring(0,str)+ "-");
                    description = description.substring(str);
                }
                this.texts.add(line);

            }
            else{
                Text line = new Text(description);
                super.getPane().getChildren().add(line);
                this.texts.add(line);
                break;
            }
        }

        double y = this.padding;
        for(Text text:this.texts){
            text.setLayoutY(y);
            y += this.padding + text.getLayoutBounds().getHeight();
        }
        this.button.autosize();
        this.button.setLayoutX(super.getWidth()/2 - this.button.getLayoutBounds().getCenterX());
        this.button.setLayoutY(y + this.padding*2);
    }

    public void changeLanguage(){
        centre();
    }

    @Override
    public void resize(){
        centre();
    }
}
