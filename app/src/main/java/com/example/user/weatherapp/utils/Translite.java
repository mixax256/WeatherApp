package com.example.user.weatherapp.utils;

/**
 * Created by user on 05.11.2015.
 */
public class Translite {
    public String ruToTranslite(String city){
        char[] cityName = city.toCharArray();
        String result = "";
        for (int i = 0; i < cityName.length; i++)
            result += translite(cityName[i]);
        return result;
    }

    private String translite(char c) {
        String result = String.valueOf(c);
        switch (c) {
            case 'а':
                result = "a";
                break;
            case 'б':
                result = "b";
                break;
            case 'в':
                result = "v";
                break;
            case 'г':
                result = "g";
                break;
            case 'д':
                result = "d";
                break;
            case 'е':
                result = "e";
                break;
            case 'ё':
                result = "e";
                break;
            case 'ж':
                result = "zh";
                break;
            case 'з':
                result = "z";
                break;
            case 'и':
                result = "i";
                break;
            case 'й':
                result = "y";
                break;
            case 'к':
                result = "k";
                break;
            case 'л':
                result = "l";
                break;
            case 'м':
                result = "m";
                break;
            case 'н':
                result = "n";
                break;
            case 'о':
                result = "o";
                break;
            case 'п':
                result = "p";
                break;
            case 'р':
                result = "r";
                break;
            case 'с':
                result = "s";
                break;
            case 'т':
                result = "t";
                break;
            case 'у':
                result = "u";
                break;
            case 'ф':
                result = "f";
                break;
            case 'х':
                result = "kh";
                break;
            case 'ц':
                result = "ts";
                break;
            case 'ч':
                result = "ch";
                break;
            case 'ш':
                result = "sh";
                break;
            case 'щ':
                result = "shch";
                break;
            case 'ъ':
                result = "";
                break;
            case 'ы':
                result = "y";
                break;
            case 'ь':
                result = "";
                break;
            case 'э':
                result = "e";
                break;
            case 'ю':
                result = "yu";
                break;
            case 'я':
                result = "ya";
                break;
        }
        return result;
    }
}
