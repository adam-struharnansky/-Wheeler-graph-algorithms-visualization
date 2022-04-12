package com.example.demo2.multilingualism;

import com.example.demo2.algorithmDisplays.SelectorDisplay;
import com.example.demo2.algorithmDisplays.TextDisplay;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class LanguageListenerAdder {

    //todo zapametat si niekde dane veci, a potom ich odstranovat

    //pre button, label,
    public static void addLanguageListener(String key, Labeled labeled){
        labeled.setText(Languages.getString(key));
        class LabeledLanguageListener implements LanguageListener{
            @Override
            public void changeOfLanguage() {
                labeled.setText(Languages.getString(key));
            }
        }
        Languages.addListener(new LabeledLanguageListener());
    }

    //todo nejako ich odstranit - treba cez ne vediet prejst, a zistit, co obsahuju, treba si tam nechat asi odkaz na to
    //pre menu, menu item,
    public static void addLanguageListener(String key, MenuItem menuItem){
        menuItem.setText(Languages.getString(key));
        class MenuItemLanguageListener implements LanguageListener{
            @Override
            public void changeOfLanguage(){
                menuItem.setText(Languages.getString(key));
            }
        }
        Languages.addListener(new MenuItemLanguageListener());
    }

    //todo, porozmyslat, ci je to to najlepsie
    public static void addLanguageListener(TextDisplay textDisplay){
        textDisplay.changeLanguage();
        class MenuItemLanguageListener implements LanguageListener{
            @Override
            public void changeOfLanguage(){textDisplay.changeLanguage();
            }
        }
        Languages.addListener(new MenuItemLanguageListener());
    }

    public void addLanguageListener(SelectorDisplay selectorDisplay){
        selectorDisplay.changeLanguage();
        class MenuItemLanguageListener implements LanguageListener{
            @Override
            public void changeOfLanguage(){selectorDisplay.changeLanguage();
            }
        }
        Languages.addListener(new MenuItemLanguageListener());
    }


}
