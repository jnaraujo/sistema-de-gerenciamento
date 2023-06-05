package com.uefs.sistemadegerenciamento.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Formatter {
    public static String date(Date date) {
        if(!(date instanceof Date)) {
            return "";
        }

        String newDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        return newDate;
    }

    public static String currency(Double value) {
        if(!(value instanceof Double)) {
            return "";
        }

        Locale locale = new Locale("pt", "BR");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        return formatter.format(value);
    }
}
