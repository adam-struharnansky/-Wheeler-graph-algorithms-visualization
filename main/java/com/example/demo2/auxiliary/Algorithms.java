package com.example.demo2.auxiliary;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;

public class Algorithms {

    public static String f(String input){
        input = (input.endsWith("$"))?input: input + "$";
        ArrayList<Character> resultAL = new ArrayList<>();
        for(Character character:input.toCharArray()){
            resultAL.add(character);
        }
        resultAL.sort(Comparator.naturalOrder());
        StringBuilder result = new StringBuilder();
        for(Character character:resultAL){
            result.append(character);
        }
        return result.toString();
    }

    public static String bwt(String input){
        input = (input.endsWith("$"))?input: input + "$";
        ArrayList<String> rotations = new ArrayList<>();
        StringBuilder str = new StringBuilder(input);
        StringBuilder end = new StringBuilder();
        for(int i = 0;i<input.length();i++){
            str.deleteCharAt(0);
            end.insert(0, input.charAt(i));
            rotations.add(str.toString() + end);
        }
        System.out.println(rotations);
        rotations.sort(Comparator.naturalOrder());
        StringBuilder result = new StringBuilder();
        for(String rotation:rotations){
            result.append(rotation.charAt(rotation.length() - 1));
        }
        return result.toString();
    }

    public static int[] lfMapping(String input){
        int[] output = new int[input.length()];

        ArrayList<Character> alphabet = new ArrayList<>();
        for(int i = 0;i<input.length();i++){
            boolean contains = false;
            for(Character character:alphabet){
                if(character == input.charAt(i)){
                    contains = true;
                    break;
                }
            }
            if(!contains){
                alphabet.add(input.charAt(i));
            }
        }
        alphabet.sort(Comparator.naturalOrder());
        int c[] = new int[alphabet.size()];
        for(int i = 0;i<input.length();i++){
            for(int j = 0;j<alphabet.size();j++){
                if(alphabet.get(j) == input.charAt(i)){
                    c[j]++;
                    break;
                }
            }
        }
        int sum = 0;
        for(int i = 0;i<alphabet.size();i++){
            int cnt = c[i];
            c[i] = sum;
            sum = sum + cnt;
        }
        for(int i = 0;i<input.length();i++){
            for(int j = 0;j<alphabet.size();j++){
                if(alphabet.get(j) == input.charAt(i)){
                    c[j]++;
                    output[i] = c[j] - 1;//todo toto je pre indexovanie od 0
                    break;
                }
            }
        }

        return output;
    }

    public static int[] suffixArray(String input){
        int [] output = new int[input.length()+1];
        input = input.endsWith("$")? input:input + "$";
        ArrayList<Pair<String, Integer>> suffixes = new ArrayList<>();

        for(int i = 0;i<input.length();i++){
            suffixes.add(new Pair<>(input.substring(i), i));
        }
        suffixes.sort(Comparator.comparing(Pair::getKey));

        for(int i = 0;i<input.length();i++){
            output[i] = suffixes.get(i).getValue();
        }

        return output;
    }
}
