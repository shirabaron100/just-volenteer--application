package com.example.myapplication.models;

public class TextUtils {
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isValidName(String str) {

        return str.matches("[a-zA-Z]*");
    }

}