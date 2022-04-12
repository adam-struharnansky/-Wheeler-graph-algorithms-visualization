package com.example.demo2.multilingualism;

import java.util.*;

public class Languages {

    //pre pridanie jazyka staci pridat jeho kod (a napisat mu vlastny properties file)
    private static final ArrayList<String> supportedLanguagesCodes = new ArrayList<>(Arrays.asList("sk", "en"));
    private static String currentLanguage = "en";

    private static final HashMap<String, Locale> locales = new HashMap<>();
    private static final HashMap<String, ResourceBundle> messages = new HashMap<>();
    private static final ArrayList<LanguageListener> listeners = new ArrayList<>();

    static{
        for(String languageCode:supportedLanguagesCodes){
            locales.put(languageCode, new Locale(languageCode));
            messages.put(languageCode, ResourceBundle.getBundle("message", locales.get(languageCode)));
        }
    }

    public static void addListener(LanguageListener languageListener){
        listeners.add(languageListener);
    }

    public static void setLanguage(String string){
        if(!supportedLanguagesCodes.contains(string)){
            return;
        }
        currentLanguage = string;
        for(LanguageListener languageListener:listeners){
            //todo - spytat sa, ci je tento este funkcny, ak nie, tak ho zahodit. Iba ak bude, tak zmenit jazyk
            languageListener.changeOfLanguage();
        }
    }

    public static String getString(String string){
        if(string == null){
            return "null";
        }
        try{
            return messages.get(currentLanguage).getString(string);
        }
        catch (MissingResourceException e){
            System.err.println(e.getMessage() + " " + string);
            return string;//ak sa nieco zabudne, tak toto by malo byt aspon podobne
            //tiez to vyriesi problem aj pri tych veciach, ktore tam nebudu mat nieco velmi zmysluplne (zmensi zvacsi)
        }
    }
}
