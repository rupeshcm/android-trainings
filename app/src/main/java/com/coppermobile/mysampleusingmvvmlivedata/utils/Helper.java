package com.coppermobile.mysampleusingmvvmlivedata.utils;

import android.text.format.DateFormat;

import java.util.Date;

public class Helper {

    public static String formatDate() {
        return (String) DateFormat.format("MMM d, yy ", new Date());
    }
}